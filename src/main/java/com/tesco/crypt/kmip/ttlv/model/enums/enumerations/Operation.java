package com.tesco.crypt.kmip.ttlv.model.enums.enumerations;

import com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum;

import java.math.BigInteger;

public enum Operation implements ByteEnum<Operation> {
    CREATE("1"),
    CREATE_KEY_PAIR("2"),
    REGISTER("3"),
    RE_KEY("4"),
    DERIVE_KEY("5"),
    CERTIFY("6"),
    RE_CERTIFY("7"),
    LOCATE("8"),
    CHECK("9"),
    GET("0000000A"),
    GET_ATTRIBUTES("0000000B"),
    GET_ATTRIBUTE_LIST("0000000C"),
    ADD_ATTRIBUTE("0000000D"),
    MODIFY_ATTRIBUTE("0000000E"),
    DELETE_ATTRIBUTE("0000000F"),
    OBTAIN_LEASE("10"),
    GET_USAGE_ALLOCATION("11"),
    ACTIVATE("12"),
    REVOKE("13"),
    DESTROY("14"),
    ARCHIVE("15"),
    RECOVER("16"),
    VALIDATE("17"),
    QUERY("18"),
    CANCEL("19"),
    POLL("0000001A"),
    NOTIFY("0000001B"),
    PUT("0000001C"),
    RE_KEY_KEY_PAIR("0000001D"),
    DISCOVER_VERSIONS("0000001E"),
    EXTENSIONS("8XXXXXXX");

    private final BigInteger start;
    private final BigInteger end;

    Operation(String hexValue) {
        if (hexValue.contains("X")) {
            start = new BigInteger(hexValue.replaceAll("X", "0"), 16);
            end = new BigInteger(hexValue.replaceAll("X", "F"), 16);
        } else {
            start = new BigInteger(hexValue, 16);
            end = start;
        }
    }

    @Override
    public Class<Operation> getType() {
        return this.getDeclaringClass();
    }

    public BigInteger getStart() {
        return start;
    }

    public BigInteger getEnd() {
        return end;
    }
}
