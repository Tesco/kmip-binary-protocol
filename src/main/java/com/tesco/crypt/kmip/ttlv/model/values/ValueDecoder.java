package com.tesco.crypt.kmip.ttlv.model.values;

import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum;
import com.tesco.crypt.kmip.ttlv.model.enums.ItemType;
import com.tesco.crypt.kmip.ttlv.model.enums.MaskEnum;
import com.tesco.crypt.kmip.ttlv.model.enums.MessageTag;
import com.tesco.crypt.kmip.ttlv.model.enums.bitmasks.CryptographicUsageMask;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.CryptographicAlgorithm;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.tesco.crypt.kmip.ttlv.BytesToTTLVDecoder.readBytes;
import static com.tesco.crypt.kmip.ttlv.Hex.bytesToHex;
import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.ATTRIBUTE_NAME;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Data
public class ValueDecoder {

    private static final ClassLoader CLASS_LOADER = ByteEnum.class.getClassLoader();
    private static final String ENUMERATION_PACKAGE = StringUtils.substringBeforeLast(CryptographicAlgorithm.class.getName(), ".");
    private static final String MASK_PACKAGE = StringUtils.substringBeforeLast(CryptographicUsageMask.class.getName(), ".");

    // state required to support Mask and Enumeration values
    private TextStringValue attributeName;

    public TTLV decode(InputStream inputStream, BigInteger length, MessageTag tag, ItemType itemType) {
        if (inputStream != null) {
            Value<?> value;

            byte[] itemBytes = readBytes(inputStream, length, itemType.name().toLowerCase() + " data");
            value = decodeValue(tag, new byte[0], itemType, itemBytes);
            consumePadding(inputStream, itemType, length);
            return value;
        } else {
            throw new IllegalArgumentException("empty bytes");
        }
    }

    @SuppressWarnings("unchecked")
    public Value<?> decodeValue(MessageTag tag, byte[] tagBytes, ItemType itemType, byte[] itemBytes) {
        Value<?> value;
        // copy previous immediate sibling attribute name (to handle mask or enumeration) and clear as should only apply to immediate sibling
        String previousAttributeName = attributeName != null ? attributeName.getValue() : "";
        attributeName = null;
        switch (itemType) {
            case INTEGER:
                BigInteger integerValue = new BigInteger(itemBytes);

                Class<? extends MaskEnum<?>> maskClass = null;
                if (isNotBlank(previousAttributeName) && previousAttributeName.endsWith("Mask")) {
                    try {
                        maskClass = (Class<? extends MaskEnum<?>>) CLASS_LOADER.loadClass(MASK_PACKAGE + "." + previousAttributeName.replaceAll(" ", ""));
                    } catch (ClassNotFoundException e) {
                        // ignore exception as often won't match
                    }
                }
                if (maskClass != null) {
                    value = new MaskValue().setValue(MaskEnum.fromBytes(integerValue, maskClass));
                } else {
                    value = new IntegerValue().setValue(integerValue.intValue());
                }
                break;
            case LONG_INTEGER:
                value = new LongIntegerValue().setValue(new BigInteger(itemBytes).longValue());
                break;
            case BIG_INTEGER:
                value = new BigIntegerValue().setValue(new BigInteger(itemBytes));
                break;
            case ENUMERATION:
                Class<? extends ByteEnum<?>> enumerationClass = tag.getEnumeration();
                if (isNotBlank(previousAttributeName) && enumerationClass == null) {
                    try {
                        enumerationClass = (Class<? extends ByteEnum<?>>) CLASS_LOADER.loadClass(ENUMERATION_PACKAGE + "." + previousAttributeName.replaceAll(" ", ""));
                    } catch (ClassNotFoundException e) {
                        // ignore exception as often won't match
                    }
                }
                ByteEnum<?> enumeration = ByteEnum.fromBytes(itemBytes, enumerationClass);
                value = new EnumerationValue().setValue(enumeration).setInteger(new BigInteger(itemBytes).intValue());
                break;
            case BOOLEAN:
                value = new BooleanValue().setValue(new BigInteger(itemBytes).intValue() == 1);
                break;
            case TEXT_STRING:
                String stringValue = new String(itemBytes, StandardCharsets.UTF_8);
                if (tag == ATTRIBUTE_NAME) {
                    value = attributeName = new TextStringValue().setValue(stringValue);
                } else {
                    value = new TextStringValue().setValue(stringValue);
                }
                break;
            case BYTE_STRING:
                value = new ByteStringValue().setValue(bytesToHex(itemBytes));
                break;
            case DATE_TIME:
                value = new DateTimeValue().setValue(new Date(new BigInteger(itemBytes).longValue() * 1000));
                break;
            case INTERVAL:
                value = new IntervalValue().setValue(new BigInteger(itemBytes).intValue());
                break;
            default:
                throw new IllegalArgumentException("invalid item type " + itemType);
        }
        return (Value<?>) value.setTag(tag).setTagBytes(tagBytes);
    }

    private void consumePadding(InputStream inputStream, ItemType itemType, BigInteger length) {
        switch (itemType) {
            case INTEGER:
            case ENUMERATION:
            case INTERVAL:
                // consume padding of fixed 4 bytes
                readBytes(inputStream, 4, itemType.name().toLowerCase() + " padding");
                break;
            case TEXT_STRING:
            case BYTE_STRING:
                // consume padding to obtain a multiple of eight bytes
                BigInteger mod = length.mod(BigInteger.valueOf(8));
                if (mod.compareTo(BigInteger.ZERO) != 0) {
                    readBytes(inputStream, BigInteger.valueOf(8).subtract(mod), itemType.name().toLowerCase() + " padding");
                }
                break;
        }
    }

}
