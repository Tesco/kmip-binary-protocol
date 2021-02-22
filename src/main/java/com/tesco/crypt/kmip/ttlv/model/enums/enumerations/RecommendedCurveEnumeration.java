package com.tesco.crypt.kmip.ttlv.model.enums.enumerations;

import com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum;

import java.math.BigInteger;

public enum RecommendedCurveEnumeration implements ByteEnum<RecommendedCurveEnumeration> {
    P_192("1"),
    K_163("2"),
    B_163("3"),
    P_224("4"),
    K_233("5"),
    B_233("6"),
    P_256("7"),
    K_283("8"),
    B_283("9"),
    P_384("0000000A"),
    K_409("0000000B"),
    B_409("0000000C"),
    P_521("0000000D"),
    K_571("0000000E"),
    B_571("0000000F"),
    EXTENSIONS("8XXXXXXX");

    private final BigInteger start;
    private final BigInteger end;

    RecommendedCurveEnumeration(String hexValue) {
        if (hexValue.contains("X")) {
            start = new BigInteger(hexValue.replaceAll("X", "0"), 16);
            end = new BigInteger(hexValue.replaceAll("X", "F"), 16);
        } else {
            start = new BigInteger(hexValue, 16);
            end = start;
        }
    }

    @Override
    public Class<RecommendedCurveEnumeration> getType() {
        return this.getDeclaringClass();
    }

    public BigInteger getStart() {
        return start;
    }

    public BigInteger getEnd() {
        return end;
    }
}
