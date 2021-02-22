package com.tesco.crypt.kmip.ttlv.model.enums;

import java.math.BigInteger;

public enum ItemType implements ByteEnum<ItemType> {
    STRUCTURE("1"),
    INTEGER("2"),
    LONG_INTEGER("3"),
    BIG_INTEGER("4"),
    ENUMERATION("5"),
    BOOLEAN("6"),
    TEXT_STRING("7"),
    BYTE_STRING("8"),
    DATE_TIME("9"),
    INTERVAL("0A");

    private final BigInteger start;
    private final BigInteger end;

    ItemType(String hexValue) {
        if (hexValue.contains("X")) {
            start = new BigInteger(hexValue.replaceAll("X", "0"), 16);
            end = new BigInteger(hexValue.replaceAll("X", "F"), 16);
        } else {
            start = new BigInteger(hexValue, 16);
            end = start;
        }
    }

    @Override
    public Class<ItemType> getType() {
        return this.getDeclaringClass();
    }

    public BigInteger getStart() {
        return start;
    }

    public BigInteger getEnd() {
        return end;
    }

}
