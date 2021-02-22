package com.tesco.crypt.kmip.ttlv.model.enums.enumerations;

import com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum;

import java.math.BigInteger;

public enum KeyCompressionType implements ByteEnum<KeyCompressionType> {
    EC_PUBLIC_KEY_TYPE_UNCOMPRESSED("1"),
    EC_PUBLIC_KEY_TYPE_X9_62_COMPRESSED_PRIME("2"),
    EC_PUBLIC_KEY_TYPE_X9_62_COMPRESSED_CHAR2("3"),
    EC_PUBLIC_KEY_TYPE_X9_62_HYBRID("4"),
    EXTENSIONS("8XXXXXXX");

    private final BigInteger start;
    private final BigInteger end;

    KeyCompressionType(String hexValue) {
        if (hexValue.contains("X")) {
            start = new BigInteger(hexValue.replaceAll("X", "0"), 16);
            end = new BigInteger(hexValue.replaceAll("X", "F"), 16);
        } else {
            start = new BigInteger(hexValue, 16);
            end = start;
        }
    }

    @Override
    public Class<KeyCompressionType> getType() {
        return this.getDeclaringClass();
    }

    public BigInteger getStart() {
        return start;
    }

    public BigInteger getEnd() {
        return end;
    }
}
