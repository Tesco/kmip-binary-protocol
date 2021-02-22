package com.tesco.crypt.kmip.ttlv;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;

@Slf4j
public class Hex {

    public static byte[] hexToBytes(String hex) {
        try {
            return org.apache.commons.codec.binary.Hex.decodeHex(hex);
        } catch (DecoderException e) {
            log.error("Exception decoding hex value " + hex, e);
            return new byte[0];
        }
    }

    public static byte hexToByte(String hex) {
        return hexToBytes(hex)[0];
    }

    public static String bytesToHex(byte[] bytes) {
        return new String(org.apache.commons.codec.binary.Hex.encodeHex(bytes));
    }

}
