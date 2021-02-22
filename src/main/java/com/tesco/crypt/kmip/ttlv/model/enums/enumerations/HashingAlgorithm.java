package com.tesco.crypt.kmip.ttlv.model.enums.enumerations;

import com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum;

import java.math.BigInteger;

public enum HashingAlgorithm implements ByteEnum<HashingAlgorithm> {
    MD2("1"),
    MD4("2"),
    MD5("3"),
    SHA_1("4"),
    SHA_224("5"),
    SHA_256("6"),
    SHA_384("7"),
    SHA_512("8"),
    RIPEMD_160("9"),
    TIGER("0000000A"),
    WHIRLPOOL("0000000B"),
    EXTENSIONS("8XXXXXXX");

    private final BigInteger start;
    private final BigInteger end;

    HashingAlgorithm(String hexValue) {
        if (hexValue.contains("X")) {
            start = new BigInteger(hexValue.replaceAll("X", "0"), 16);
            end = new BigInteger(hexValue.replaceAll("X", "F"), 16);
        } else {
            start = new BigInteger(hexValue, 16);
            end = start;
        }
    }

    @Override
    public Class<HashingAlgorithm> getType() {
        return this.getDeclaringClass();
    }

    public BigInteger getStart() {
        return start;
    }

    public BigInteger getEnd() {
        return end;
    }
}
