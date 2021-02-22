package com.tesco.crypt.kmip.ttlv.model.enums.bitmasks;

import com.tesco.crypt.kmip.ttlv.model.enums.MaskEnum;

import java.math.BigInteger;

public enum StorageStatusMask implements MaskEnum<StorageStatusMask> {
    ON_LINE_STORAGE("1"),
    ARCHIVAL_STORAGE("2"),
    EXTENSIONS("XXXXXXX0");

    BigInteger value;

    StorageStatusMask(String hexValue) {
        value = new BigInteger(hexValue.replaceAll("X", "F"), 16);
    }

    @Override
    public Class<StorageStatusMask> getType() {
        return this.getDeclaringClass();
    }

    public BigInteger getValue() {
        return value;
    }
}
