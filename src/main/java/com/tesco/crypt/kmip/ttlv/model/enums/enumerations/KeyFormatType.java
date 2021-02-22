package com.tesco.crypt.kmip.ttlv.model.enums.enumerations;

import com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum;

import java.math.BigInteger;

public enum KeyFormatType implements ByteEnum<KeyFormatType> {
    RAW("1"),
    OPAQUE("2"),
    PKCS_1("3"),
    PKCS_8("4"),
    X_509("5"),
    ECPRIVATEKEY("6"),
    TRANSPARENT_SYMMETRIC_KEY("7"),
    TRANSPARENT_DSA_PRIVATE_KEY("8"),
    TRANSPARENT_DSA_PUBLIC_KEY("9"),
    TRANSPARENT_RSA_PRIVATE_KEY("0000000A"),
    TRANSPARENT_RSA_PUBLIC_KEY("0000000B"),
    TRANSPARENT_DH_PRIVATE_KEY("0000000C"),
    TRANSPARENT_DH_PUBLIC_KEY("0000000D"),
    TRANSPARENT_ECDSA_PRIVATE_KEY("0000000E"),
    TRANSPARENT_ECDSA_PUBLIC_KEY("0000000F"),
    TRANSPARENT_ECDH_PRIVATE_KEY("10"),
    TRANSPARENT_ECDH_PUBLIC_KEY("11"),
    TRANSPARENT_ECMQV_PRIVATE_KEY("12"),
    TRANSPARENT_ECMQV_PUBLIC_KEY("13"),
    EXTENSIONS("8XXXXXXX");

    private final BigInteger start;
    private final BigInteger end;

    KeyFormatType(String hexValue) {
        if (hexValue.contains("X")) {
            start = new BigInteger(hexValue.replaceAll("X", "0"), 16);
            end = new BigInteger(hexValue.replaceAll("X", "F"), 16);
        } else {
            start = new BigInteger(hexValue, 16);
            end = start;
        }
    }

    @Override
    public Class<KeyFormatType> getType() {
        return this.getDeclaringClass();
    }

    public BigInteger getStart() {
        return start;
    }

    public BigInteger getEnd() {
        return end;
    }
}
