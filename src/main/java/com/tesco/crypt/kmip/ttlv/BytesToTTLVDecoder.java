package com.tesco.crypt.kmip.ttlv;

import com.tesco.crypt.kmip.Decoder;
import com.tesco.crypt.kmip.ttlv.model.IncompleteMessageException;
import com.tesco.crypt.kmip.ttlv.model.Structure;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum;
import com.tesco.crypt.kmip.ttlv.model.enums.ItemType;
import com.tesco.crypt.kmip.ttlv.model.enums.MessageTag;
import com.tesco.crypt.kmip.ttlv.model.values.ValueDecoder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.function.Consumer;

@Slf4j
public class BytesToTTLVDecoder implements Decoder<InputStream, TTLV> {

    public static byte[] readBytes(InputStream inputStream, BigInteger length, String description) {
        return readBytes(inputStream, length.intValue(), description);
    }

    public static byte[] readBytes(InputStream inputStream, int length, String description) {
        try {
            if (log.isTraceEnabled()) {
                log.trace("trying to read " + description + " as " + length + " bytes");
            }
            byte[] bytes = inputStream.readNBytes(length);
            if (log.isTraceEnabled()) {
                log.trace("reading " + description + " hex: \"" + Hex.bytesToHex(bytes) + "\"");
            }
            if (bytes.length < length) {
                throw new IncompleteMessageException("Expected: " + length + " bytes, found: " + bytes.length + " while reading " + description);
            }
            return bytes;
        } catch (IOException ioe) {
            throw new RuntimeException("Exception reading " + description, ioe);
        }
    }

    public void decode(InputStream inputStream, Consumer<TTLV> ttlvConsumer) {
        decode(inputStream, new ValueDecoder(), ttlvConsumer);
    }

    /**
     * Supports state between values within a structure, i.e. for Mask and Enumeration values
     */
    @SneakyThrows
    private void decode(InputStream inputStream, ValueDecoder valueDecoder, Consumer<TTLV> ttlvConsumer) {
        if (inputStream != null) {
            byte[] messageTagBytes = readBytes(inputStream, 3, "tag");
            MessageTag tag = MessageTag.fromBytes(messageTagBytes);
            ItemType itemType = ByteEnum.fromBytes(readBytes(inputStream, 1, "itemType"), ItemType.class);
            BigInteger length = new BigInteger(readBytes(inputStream, 4, "length"));
            switch (itemType) {
                case STRUCTURE: {
                    Structure structure = new Structure();
                    if (length.intValue() > 0) {
                        decode(new ByteArrayInputStream(readBytes(inputStream, length, "structure")), structure::addTtlvs);
                    }
                    ttlvConsumer.accept(structure.setTag(tag).setTagBytes(messageTagBytes));
                    break;
                }
                case INTEGER:
                case LONG_INTEGER:
                case BIG_INTEGER:
                case ENUMERATION:
                case BOOLEAN:
                case TEXT_STRING:
                case BYTE_STRING:
                case DATE_TIME:
                case INTERVAL: {
                    ttlvConsumer.accept(valueDecoder.decode(inputStream, length, tag, itemType).setTag(tag));
                    break;
                }
                default:
                    throw new IllegalArgumentException("invalid item type " + itemType);
            }
            if (inputStream.available() > 0) {
                decode(inputStream, valueDecoder, ttlvConsumer);
            }
        }
    }
}
