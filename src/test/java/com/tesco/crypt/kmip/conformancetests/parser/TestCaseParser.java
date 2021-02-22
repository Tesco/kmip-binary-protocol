package com.tesco.crypt.kmip.conformancetests.parser;

import com.tesco.crypt.kmip.ttlv.Hex;
import com.tesco.crypt.kmip.ttlv.model.Structure;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum;
import com.tesco.crypt.kmip.ttlv.model.enums.ItemType;
import com.tesco.crypt.kmip.ttlv.model.enums.MessageTag;
import com.tesco.crypt.kmip.ttlv.model.values.BooleanValue;
import com.tesco.crypt.kmip.ttlv.model.values.ValueDecoder;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.logging.log4j.util.Strings.isNotBlank;

public class TestCaseParser {

    private static final Pattern TEST_CASE_SECTION = Pattern.compile("^(\\d+\\.\\d+(\\.\\d+)?)\\s*(?:Test Case: )(.*)$");

    public static Map<String, TestCase> loadConformanceTest(String location) {
        Map<String, TestCase> result = new LinkedHashMap<>();
        String fileContents = loadFileFromLocation(location);
        String testGroupName = null;
        boolean requestResponseSection = false;
        String testName = null;
        boolean requestTTLV = false;
        boolean requestHex = false;
        List<String> structure = new ArrayList<>();
        for (String line : fileContents.split("\n")) {
            if (isNotBlank(line.trim())) {
                Matcher titleMatcher = TEST_CASE_SECTION.matcher(line);
                if (titleMatcher.matches()) {
                    testGroupName = titleMatcher.group(1);
                    continue;
                }
                if (testGroupName != null) {
                    if (line.matches("^\\d{1,2}$")) {
                        requestResponseSection = true;
                        continue;
                    }
                    if (requestResponseSection && line.matches("^[A-z\\s()]+$")) {
                        testName = testGroupName + " " + line;
                        requestResponseSection = false;
                        requestHex = true;
                        requestTTLV = true;
                        continue;
                    }
                    if (testName != null) {
                        if (line.matches("^\\s*(Tag:)+.*")) {
                            structure.add(line);
                        }
                        if (line.matches("^\\s*4200.*")) {
                            if (requestTTLV) {
                                result.put(testName + " request", new TestCase().setName(testName + " request").setTtlv(convertToTTLV(new ArrayList<>(structure))));
                                requestTTLV = false;
                            } else {
                                result.put(testName + " response", new TestCase().setName(testName + " response").setTtlv(convertToTTLV(new ArrayList<>(structure))));
                            }

                            if (requestHex) {
                                result.get(testName + " request").setHex(line);
                                requestHex = false;
                            } else {
                                result.get(testName + " response").setHex(line);
                            }
                            structure.clear();
                        }
                    }
                }
            }
        }
        return result;
    }

    private static final Pattern BYTES_DATA = Pattern.compile("^.*(?:Data: 0x)([\\dA-F]+).*$");
    private static final Pattern TEXT_STRING_DATA = Pattern.compile("^.*(?:Data: )(.+)$");
    private static final Pattern BYTE_STRING_DATA = Pattern.compile("^.*(?:Data: )([\\dA-F]+).*$");
    private static final Pattern INDENT = Pattern.compile("^(\\s*)T{1}.*+$");
    private static final Pattern TAG = Pattern.compile("^.*(?:0x)([\\dA-F]+)\\), Type:.*$");
    private static final Pattern TYPE = Pattern.compile("^.*(?:0x)(0[\\dA-Z]{1})\\).*$");

    @SuppressWarnings("ConstantConditions")
    private static TTLV convertToTTLV(ArrayList<String> lines) {
        ValueDecoder valueDecoder = new ValueDecoder();
        Map<Integer, Structure> structures = new HashMap<>();
        for (String line : lines) {
            if (line.contains("XXX")) {
                System.out.println("line = " + line);
            }
            Matcher tagMatcher = TAG.matcher(line);
            Matcher typeMatcher = TYPE.matcher(line);
            Matcher indentMatcher = INDENT.matcher(line);
            Matcher bytesDataMatcher = BYTES_DATA.matcher(line);
            Matcher textStringDataMatcher = TEXT_STRING_DATA.matcher(line);
            Matcher byteStringDataMatcher = BYTE_STRING_DATA.matcher(line);

            MessageTag messageTag = null;
            Integer indent = null;
            if (indentMatcher.matches()) {
                indent = indentMatcher.group(1).length();
            }
            byte[] tagBytes = null;
            if (tagMatcher.matches()) {
                tagBytes = new BigInteger(tagMatcher.group(1), 16).toByteArray();
                messageTag = MessageTag.fromBytes(tagBytes);
            }
            if (typeMatcher.matches()) {
                ItemType itemType = ByteEnum.fromBytes(new BigInteger(typeMatcher.group(1), 16).toByteArray(), ItemType.class);
                Structure parentStructure = null;
                if (indent >= 2) {
                    parentStructure = structures.get(indent - 2);
                }
                switch (itemType) {
                    case STRUCTURE:
                        Structure structure = (Structure) new Structure().setTag(messageTag).setTagBytes(tagBytes);
                        structures.put(indent, structure);
                        if (parentStructure != null) {
                            parentStructure.addTtlvs(structure);
                        }
                        break;
                    case INTEGER:
                    case LONG_INTEGER:
                    case ENUMERATION:
                    case DATE_TIME:
                    case INTERVAL:
                        if (parentStructure != null) {
                            if (bytesDataMatcher.matches()) {
                                parentStructure.addTtlvs(valueDecoder.decodeValue(messageTag, tagBytes, itemType, new BigInteger(bytesDataMatcher.group(1), 16).toByteArray()));
                            }
                        }
                        break;
                    case BOOLEAN:
                        if (parentStructure != null) {
                            if (textStringDataMatcher.matches()) {
                                parentStructure.addTtlvs(new BooleanValue().setValue(textStringDataMatcher.group(1).trim().equalsIgnoreCase("TRUE")).setTag(messageTag));
                            }
                        }
                        break;
                    case TEXT_STRING:
                        if (parentStructure != null) {
                            if (textStringDataMatcher.matches()) {
                                parentStructure.addTtlvs(valueDecoder.decodeValue(messageTag, tagBytes, itemType, textStringDataMatcher.group(1).getBytes(StandardCharsets.UTF_8)));
                            }
                        }
                        break;
                    case BIG_INTEGER:
                    case BYTE_STRING:
                        if (parentStructure != null) {
                            if (byteStringDataMatcher.matches()) {
                                parentStructure.addTtlvs(valueDecoder.decodeValue(messageTag, tagBytes, itemType, Hex.hexToBytes(byteStringDataMatcher.group(1))));
                            }
                        }
                        break;
                    default:
                        throw new RuntimeException("Unsupported item type " + itemType);
                }
            }
        }
        return structures.get(0);
    }

    @SneakyThrows
    public static String loadFileFromLocation(String location) {
        location = location.trim().replaceAll("\\\\", "/");

        Path path;
        if (location.toLowerCase().startsWith("file:")) {
            path = Paths.get(URI.create(location));
        } else {
            path = Paths.get(location);
        }

        if (Files.exists(path)) {
            return FileUtils.readFileToString(path.toFile(), "UTF-8");
        } else {
            return loadFileFromClasspath(location);
        }
    }

    private static String loadFileFromClasspath(String location) {
        InputStream inputStream = TestCaseParser.class.getResourceAsStream(location);

        if (inputStream == null) {
            inputStream = TestCaseParser.class.getClassLoader().getResourceAsStream(location);
        }

        if (inputStream == null) {
            inputStream = ClassLoader.getSystemResourceAsStream(location);
        }

        if (inputStream != null) {
            try {
                return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException("Could not read " + location + " from the classpath", e);
            }
        }

        throw new RuntimeException("Could not find " + location + " on the classpath");
    }

}
