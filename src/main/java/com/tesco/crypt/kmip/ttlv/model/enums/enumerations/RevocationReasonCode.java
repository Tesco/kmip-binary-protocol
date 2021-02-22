package com.tesco.crypt.kmip.ttlv.model.enums.enumerations;

import com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum;

import java.math.BigInteger;

public enum RevocationReasonCode implements ByteEnum<RevocationReasonCode> {
    UNSPECIFIED("1"),
    KEY_COMPROMISE("2"),
    CA_COMPROMISE("3"),
    AFFILIATION_CHANGED("4"),
    SUPERSEDED("5"),
    CESSATION_OF_OPERATION("6"),
    PRIVILEGE_WITHDRAWN("7"),
    EXTENSIONS("8XXXXXXX");

    private final BigInteger start;
    private final BigInteger end;

    RevocationReasonCode(String hexValue) {
        if (hexValue.contains("X")) {
            start = new BigInteger(hexValue.replaceAll("X", "0"), 16);
            end = new BigInteger(hexValue.replaceAll("X", "F"), 16);
        } else {
            start = new BigInteger(hexValue, 16);
            end = start;
        }
    }

    @Override
    public Class<RevocationReasonCode> getType() {
        return this.getDeclaringClass();
    }

    public BigInteger getStart() {
        return start;
    }

    public BigInteger getEnd() {
        return end;
    }
}
