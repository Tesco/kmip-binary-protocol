package com.tesco.crypt.kmip.ttlv.model.enums.enumerations;

import com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum;

import java.math.BigInteger;

public enum LinkType implements ByteEnum<LinkType> {
    CERTIFICATE_LINK("101"),
    PUBLIC_KEY_LINK("102"),
    PRIVATE_KEY_LINK("103"),
    DERIVATION_BASE_OBJECT_LINK("104"),
    DERIVED_KEY_LINK("105"),
    REPLACEMENT_OBJECT_LINK("106"),
    REPLACED_OBJECT_LINK("107"),
    EXTENSIONS("8XXXXXXX");

    private final BigInteger start;
    private final BigInteger end;

    LinkType(String hexValue) {
        if (hexValue.contains("X")) {
            start = new BigInteger(hexValue.replaceAll("X", "0"), 16);
            end = new BigInteger(hexValue.replaceAll("X", "F"), 16);
        } else {
            start = new BigInteger(hexValue, 16);
            end = start;
        }
    }

    @Override
    public Class<LinkType> getType() {
        return this.getDeclaringClass();
    }

    public BigInteger getStart() {
        return start;
    }

    public BigInteger getEnd() {
        return end;
    }
}
