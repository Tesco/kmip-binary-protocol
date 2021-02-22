package com.tesco.crypt.kmip.ttlv.model.enums.bitmasks;

import com.tesco.crypt.kmip.ttlv.model.enums.MaskEnum;

import java.math.BigInteger;

public enum CryptographicUsageMask implements MaskEnum<CryptographicUsageMask> {
    SIGN("1"),
    VERIFY("2"),
    ENCRYPT("4"),
    DECRYPT("8"),
    WRAP_KEY("10"),
    UNWRAP_KEY("20"),
    EXPORT("40"),
    MAC_GENERATE("80"),
    MAC_VERIFY("100"),
    DERIVE_KEY("200"),
    CONTENT_COMMITMENT_NON_REPUDIATION("400"),
    KEY_AGREEMENT("800"),
    CERTIFICATE_SIGN("1000"),
    CRL_SIGN("2000"),
    GENERATE_CRYPTOGRAM("4000"),
    VALIDATE_CRYPTOGRAM("8000"),
    TRANSLATE_ENCRYPT("10000"),
    TRANSLATE_DECRYPT("20000"),
    TRANSLATE_WRAP("40000"),
    TRANSLATE_UNWRAP("80000"),
    EXTENSIONS("XXX00000");

    BigInteger value;

    CryptographicUsageMask(String hexValue) {
        value = new BigInteger(hexValue.replaceAll("X", "F"), 16);
    }

    @Override
    public Class<CryptographicUsageMask> getType() {
        return this.getDeclaringClass();
    }

    public BigInteger getValue() {
        return value;
    }

}
