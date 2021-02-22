package com.tesco.crypt.kmip.ttlv.model.enums.enumerations;

import com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum;

import java.math.BigInteger;

public enum BlockCipherMode implements ByteEnum<BlockCipherMode> {
    CBC("1"),
    ECB("2"),
    PCBC("3"),
    CFB("4"),
    OFB("5"),
    CTR("6"),
    CMAC("7"),
    CCM("8"),
    GCM("9"),
    CBC_MAC("0000000A"),
    XTS("0000000B"),
    AESKEYWRAPPADDING("0000000C"),
    NISTKEYWRAP("0000000D"),
    X9_102_AESKW("0000000E"),
    X9_102_TDKW("0000000F"),
    X9_102_AKW1("10"),
    X9_102_AKW2("11"),
    EXTENSIONS("8XXXXXXX");

    private final BigInteger start;
    private final BigInteger end;

    BlockCipherMode(String hexValue) {
        if (hexValue.contains("X")) {
            start = new BigInteger(hexValue.replaceAll("X", "0"), 16);
            end = new BigInteger(hexValue.replaceAll("X", "F"), 16);
        } else {
            start = new BigInteger(hexValue, 16);
            end = start;
        }
    }

    @Override
    public Class<BlockCipherMode> getType() {
        return this.getDeclaringClass();
    }

    public BigInteger getStart() {
        return start;
    }

    public BigInteger getEnd() {
        return end;
    }

}
