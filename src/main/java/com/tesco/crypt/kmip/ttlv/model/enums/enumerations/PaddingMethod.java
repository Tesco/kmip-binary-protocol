package com.tesco.crypt.kmip.ttlv.model.enums.enumerations;

import com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum;

import java.math.BigInteger;

public enum PaddingMethod implements ByteEnum<PaddingMethod> {
    NONE("1"),
    OAEP("2"),
    PKCS5("3"),
    SSL3("4"),
    ZEROS("5"),
    ANSI_X9_23("6"),
    ISO_10126("7"),
    PKCS1_V1_5("8"),
    X9_31("9"),
    PSS("0000000A"),
    EXTENSIONS("8XXXXXXX");

    private final BigInteger start;
    private final BigInteger end;

    PaddingMethod(String hexValue) {
        if (hexValue.contains("X")) {
            start = new BigInteger(hexValue.replaceAll("X", "0"), 16);
            end = new BigInteger(hexValue.replaceAll("X", "F"), 16);
        } else {
            start = new BigInteger(hexValue, 16);
            end = start;
        }
    }

    @Override
    public Class<PaddingMethod> getType() {
        return this.getDeclaringClass();
    }

    public BigInteger getStart() {
        return start;
    }

    public BigInteger getEnd() {
        return end;
    }
}
