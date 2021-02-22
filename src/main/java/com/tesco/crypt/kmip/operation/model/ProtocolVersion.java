package com.tesco.crypt.kmip.operation.model;

import lombok.Data;

@Data
public class ProtocolVersion {

    public static ProtocolVersion VERSION_ONE_ZERO = new ProtocolVersion().setMajor(1).setMinor(0);
    public static ProtocolVersion VERSION_ONE_ONE = new ProtocolVersion().setMajor(1).setMinor(1);
    public static ProtocolVersion VERSION_ONE_FOUR = new ProtocolVersion().setMajor(1).setMinor(4);

    Integer major;
    Integer minor;

}
