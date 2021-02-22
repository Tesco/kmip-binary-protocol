package com.tesco.crypt.kmip.ttlv.model.enums.enumerations;

import com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum;

import java.math.BigInteger;

public enum DigitalSignatureAlgorithm implements ByteEnum<DigitalSignatureAlgorithm> {
    MD2_WITH_RSA_ENCRYPTION_PKCS_1_V1_5("1"),
    MD5_WITH_RSA_ENCRYPTION_PKCS_1_V1_5("2"),
    SHA_1_WITH_RSA_ENCRYPTION_PKCS_1_V1_5("3"),
    SHA_224_WITH_RSA_ENCRYPTION_PKCS_1_V1_5("4"),
    SHA_256_WITH_RSA_ENCRYPTION_PKCS_1_V1_5("5"),
    SHA_384_WITH_RSA_ENCRYPTION_PKCS_1_V1_5("6"),
    SHA_512_WITH_RSA_ENCRYPTION_PKCS_1_V1_5("7"),
    RSASSA_PSS_PKCS_1_V2_1("8"),
    DSA_WITH_SHA_1("9"),
    DSA_WITH_SHA224("0000000A"),
    DSA_WITH_SHA256("0000000B"),
    ECDSA_WITH_SHA_1("0000000C"),
    ECDSA_WITH_SHA224("0000000D"),
    ECDSA_WITH_SHA256("0000000E"),
    ECDSA_WITH_SHA384("0000000F"),
    ECDSA_WITH_SHA512("10"),
    EXTENSIONS("8XXXXXXX");

    private final BigInteger start;
    private final BigInteger end;

    DigitalSignatureAlgorithm(String hexValue) {
        if (hexValue.contains("X")) {
            start = new BigInteger(hexValue.replaceAll("X", "0"), 16);
            end = new BigInteger(hexValue.replaceAll("X", "F"), 16);
        } else {
            start = new BigInteger(hexValue, 16);
            end = start;
        }
    }

    @Override
    public Class<DigitalSignatureAlgorithm> getType() {
        return this.getDeclaringClass();
    }

    public BigInteger getStart() {
        return start;
    }

    public BigInteger getEnd() {
        return end;
    }
}
