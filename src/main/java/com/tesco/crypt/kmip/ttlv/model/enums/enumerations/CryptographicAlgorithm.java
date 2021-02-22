package com.tesco.crypt.kmip.ttlv.model.enums.enumerations;

import com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum;

import java.math.BigInteger;

public enum CryptographicAlgorithm implements ByteEnum<CryptographicAlgorithm> {
    DES("1"),
    TRIPLE_DES("2"),
    AES("3"),
    RSA("4"),
    DSA("5"),
    ECDSA("6"),
    HMAC_SHA1("7"),
    HMAC_SHA224("8"),
    HMAC_SHA256("9"),
    HMAC_SHA384("0000000A"),
    HMAC_SHA512("0000000B"),
    HMAC_MD5("0000000C"),
    DH("0000000D"),
    ECDH("0000000E"),
    ECMQV("0000000F"),
    BLOWFISH("10"),
    CAMELLIA("11"),
    CAST5("12"),
    IDEA("13"),
    MARS("14"),
    RC2("15"),
    RC4("16"),
    RC5("17"),
    SKIPJACK("18"),
    TWOFISH("19"),
    EXTENSIONS("8XXXXXXX");

    private final BigInteger start;
    private final BigInteger end;

    CryptographicAlgorithm(String hexValue) {
        if (hexValue.contains("X")) {
            start = new BigInteger(hexValue.replaceAll("X", "0"), 16);
            end = new BigInteger(hexValue.replaceAll("X", "F"), 16);
        } else {
            start = new BigInteger(hexValue, 16);
            end = start;
        }
    }

    @Override
    public Class<CryptographicAlgorithm> getType() {
        return this.getDeclaringClass();
    }

    public BigInteger getStart() {
        return start;
    }

    public BigInteger getEnd() {
        return end;
    }
}
