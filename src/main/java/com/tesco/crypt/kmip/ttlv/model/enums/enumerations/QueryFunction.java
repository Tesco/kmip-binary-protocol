package com.tesco.crypt.kmip.ttlv.model.enums.enumerations;

import com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum;

import java.math.BigInteger;

public enum QueryFunction implements ByteEnum<QueryFunction> {
    QUERY_OPERATIONS("1"),
    QUERY_OBJECTS("2"),
    QUERY_SERVER_INFORMATION("3"),
    QUERY_APPLICATION_NAMESPACES("4"),
    QUERY_EXTENSION_LIST("5"),
    QUERY_EXTENSION_MAP("6"),
    EXTENSIONS("8XXXXXXX");

    private final BigInteger start;
    private final BigInteger end;

    QueryFunction(String hexValue) {
        if (hexValue.contains("X")) {
            start = new BigInteger(hexValue.replaceAll("X", "0"), 16);
            end = new BigInteger(hexValue.replaceAll("X", "F"), 16);
        } else {
            start = new BigInteger(hexValue, 16);
            end = start;
        }
    }

    @Override
    public Class<QueryFunction> getType() {
        return this.getDeclaringClass();
    }

    public BigInteger getStart() {
        return start;
    }

    public BigInteger getEnd() {
        return end;
    }
}
