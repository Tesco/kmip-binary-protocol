package com.tesco.crypt.kmip.ttlv.model.enums.enumerations;

import com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum;

import java.math.BigInteger;

public enum KeyRoleType implements ByteEnum<KeyRoleType> {
    BDK("1"),
    CVK("2"),
    DEK("3"),
    MKAC("4"),
    MKSMC("5"),
    MKSMI("6"),
    MKDAC("7"),
    MKDN("8"),
    MKCP("9"),
    MKOTH("0000000A"),
    KEK("0000000B"),
    MAC16609("0000000C"),
    MAC97971("0000000D"),
    MAC97972("0000000E"),
    MAC97973("0000000F"),
    MAC97974("10"),
    MAC97975("11"),
    ZPK("12"),
    PVKIBM("13"),
    PVKPVV("14"),
    PVKOTH("15"),
    EXTENSIONS("8XXXXXXX");

    private final BigInteger start;
    private final BigInteger end;

    KeyRoleType(String hexValue) {
        if (hexValue.contains("X")) {
            start = new BigInteger(hexValue.replaceAll("X", "0"), 16);
            end = new BigInteger(hexValue.replaceAll("X", "F"), 16);
        } else {
            start = new BigInteger(hexValue, 16);
            end = start;
        }
    }

    @Override
    public Class<KeyRoleType> getType() {
        return this.getDeclaringClass();
    }

    public BigInteger getStart() {
        return start;
    }

    public BigInteger getEnd() {
        return end;
    }
}
