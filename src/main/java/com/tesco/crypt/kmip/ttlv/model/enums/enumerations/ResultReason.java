package com.tesco.crypt.kmip.ttlv.model.enums.enumerations;

import com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum;

import java.math.BigInteger;

public enum ResultReason implements ByteEnum<ResultReason> {
    ITEM_NOT_FOUND("1"),
    RESPONSE_TOO_LARGE("2"),
    AUTHENTICATION_NOT_SUCCESSFUL("3"),
    INVALID_MESSAGE("4"),
    OPERATION_NOT_SUPPORTED("5"),
    MISSING_DATA("6"),
    INVALID_FIELD("7"),
    FEATURE_NOT_SUPPORTED("8"),
    OPERATION_CANCELED_BY_REQUESTER("9"),
    CRYPTOGRAPHIC_FAILURE("0000000A"),
    ILLEGAL_OPERATION("0000000B"),
    PERMISSION_DENIED("0000000C"),
    OBJECT_ARCHIVED("0000000D"),
    INDEX_OUT_OF_BOUNDS("0000000E"),
    APPLICATION_NAMESPACE_NOT_SUPPORTED("0000000F"),
    KEY_FORMAT_TYPE_NOT_SUPPORTED("10"),
    KEY_COMPRESSION_TYPE_NOT_SUPPORTED("11"),
    ENCODING_OPTION_ERROR("12"),
    GENERAL_FAILURE("100"),
    EXTENSIONS("8XXXXXXX");

    private final BigInteger start;
    private final BigInteger end;

    ResultReason(String hexValue) {
        if (hexValue.contains("X")) {
            start = new BigInteger(hexValue.replaceAll("X", "0"), 16);
            end = new BigInteger(hexValue.replaceAll("X", "F"), 16);
        } else {
            start = new BigInteger(hexValue, 16);
            end = start;
        }
    }

    @Override
    public Class<ResultReason> getType() {
        return this.getDeclaringClass();
    }

    public BigInteger getStart() {
        return start;
    }

    public BigInteger getEnd() {
        return end;
    }
}
