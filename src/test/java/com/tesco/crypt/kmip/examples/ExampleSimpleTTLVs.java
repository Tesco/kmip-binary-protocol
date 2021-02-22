package com.tesco.crypt.kmip.examples;

import com.tesco.crypt.kmip.ttlv.model.Structure;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.MessageTag;
import com.tesco.crypt.kmip.ttlv.model.enums.bitmasks.CryptographicUsageMask;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.CryptographicAlgorithm;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.ResultStatus;
import com.tesco.crypt.kmip.ttlv.model.values.*;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class ExampleSimpleTTLVs {

    // An Integer containing the decimal value 8
    public static String integerValueHex = "42002002000000040000000800000000";
    public static TTLV integerValue = new IntegerValue()
        .setValue(8)
        .setTag(MessageTag.COMPROMISE_DATE);

    // A Long Integer containing the decimal value 123456789000000000
    public static String longIntegerValueHex = "420020030000000801B69B4BA5749200";
    public static TTLV longIntegerValue = new LongIntegerValue()
        .setValue(123456789000000000L)
        .setTag(MessageTag.COMPROMISE_DATE);

    // A Big Integer containing the decimal value 1234567890000000000000000000
    public static String bigIntegerValueHex = "42002004000000100000000003FD35EB6BC2DF4618080000";
    public static TTLV bigIntegerValue = new BigIntegerValue()
        .setValue(new BigInteger("1234567890000000000000000000"))
        .setTag(MessageTag.COMPROMISE_DATE);

    // A Boolean with the value True
    public static String booleanValueHex = "42002006000000080000000000000001";
    public static TTLV booleanValue = new BooleanValue()
        .setValue(true)
        .setTag(MessageTag.COMPROMISE_DATE);

    // A Text String with the value "Hello World"
    public static String stringValueHex = "420020070000000B48656C6C6F20576F726C640000000000";
    public static TTLV stringValue = new TextStringValue()
        .setValue("Hello World")
        .setTag(MessageTag.COMPROMISE_DATE);

    // A Text String without padding
    public static String stringValueWithoutPaddingHex = "420023070000001030313233343536373839414243444546";
    public static TTLV stringValueWithoutPadding = new TextStringValue()
        .setValue("0123456789ABCDEF")
        .setTag(MessageTag.CREDENTIAL);

    // A Byte String with the value { 0x01, 0x02, 0x03 }
    public static String byteStringValueHex = "42002008000000030102030000000000";
    public static TTLV byteStringValue = new ByteStringValue()
        .setValue("010203")
        .setTag(MessageTag.COMPROMISE_DATE);

    // A Byte String without padding
    public static String byteStringValueWithoutPaddingHex = "4200230800000010000102030405060708090a0b0c0d0e0f";
    public static TTLV byteStringValueWithoutPadding = new ByteStringValue()
        .setValue("000102030405060708090A0B0C0D0E0F".toLowerCase())
        .setTag(MessageTag.CREDENTIAL);

    // A Date-Time, containing the value for Friday, March 14, 2008, 11:56:40 GMT
    public static String dataTimeValueHex = "42002009000000080000000047DA67F8";
    public static TTLV dataTimeValue;
    // A Structure containing an Enumeration, value 254, followed by an Integer, value 255, having tags 420004 (APPLICATION_SPECIFIC_INFORMATION) and 420005 (ARCHIVE_DATE) respectively
    public static String structureEnumerationAndIntegerHex = "42002001000000204200040500000004000000FE000000004200050200000004000000FF00000000";
    // An Interval, containing the value for 10 days (864,000)
    public static String intervalValueHex = "4200200A00000004000D2F0000000000";
    public static TTLV structureEnumerationAndInteger = new Structure()
        .addTtlvs(
            new EnumerationValue()
                .setInteger(254)
                .setTag(MessageTag.APPLICATION_SPECIFIC_INFORMATION),
            new IntegerValue()
                .setValue(255)
                .setTag(MessageTag.ARCHIVE_DATE)
        )
        .setTag(MessageTag.COMPROMISE_DATE);
    // given - An Enumeration with value 255
    public static String enumerationValueHex = "4200200500000004000000FF00000000";
    public static TTLV intervalValue = new IntervalValue()
        .setValue(864000)
        .setTag(MessageTag.COMPROMISE_DATE);
    // given - An Enumeration for Result Status of value SUCCESS
    public static String enumerationValueMatchingTagHex = "42007F05000000040000000000000000";
    public static TTLV enumerationValue = new EnumerationValue()
        .setInteger(255)
        .setTag(MessageTag.COMPROMISE_DATE);
    // A Mask for Cryptographic Usage with values SIGN, VERIFY, ENCRYPT & DECRYPT
    public static String maskStructureHex = "420008010000003042000A070000001843727970746F67726170686963205573616765204D61736B42000B02000000040000000F00000000";
    public static TTLV enumerationValueMatchingTag = new EnumerationValue()
        .setValue(ResultStatus.SUCCESS)
        .setTag(MessageTag.RESULT_STATUS);
    // An enumeration for Cryptographic Algorithm
    public static String attributeValueEnumerationStructureHex = "420008010000003042000A070000001743727970746F6772617068696320416C676F726974686D0042000B05000000040000000300000000";
    public static TTLV maskStructure = new Structure()
        .addTtlvs(
            new TextStringValue()
                .setValue("Cryptographic Usage Mask")
                .setTag(MessageTag.ATTRIBUTE_NAME),
            new MaskValue()
                .setValue(Arrays.asList(
                    CryptographicUsageMask.SIGN,
                    CryptographicUsageMask.VERIFY,
                    CryptographicUsageMask.ENCRYPT,
                    CryptographicUsageMask.DECRYPT
                ))
                .setTag(MessageTag.ATTRIBUTE_VALUE)
        )
        .setTag(MessageTag.ATTRIBUTE);

    static {
        try {
            dataTimeValue = new DateTimeValue()
                .setValue(new SimpleDateFormat("EEE, MMM d, yyyy, HH:mm:ss Z").parse("Friday, March 14, 2008, 11:56:40 GMT"))
                .setTag(MessageTag.COMPROMISE_DATE);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static TTLV attributeValueEnumerationStructure = new Structure()
        .addTtlvs(
            new TextStringValue()
                .setValue("Cryptographic Algorithm")
                .setTag(MessageTag.ATTRIBUTE_NAME),
            new EnumerationValue()
                .setValue(CryptographicAlgorithm.AES)
                .setTag(MessageTag.ATTRIBUTE_VALUE)
        )
        .setTag(MessageTag.ATTRIBUTE);

}
