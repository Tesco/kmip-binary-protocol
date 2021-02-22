package com.tesco.crypt.kmip.ttlv;

import com.tesco.crypt.kmip.Encoder;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;

import static com.tesco.crypt.kmip.ttlv.Hex.hexToBytes;

@Slf4j
public class TTLVToBytesEncoder implements Encoder<TTLV, byte[]> {

    public static byte[] writeBytes(OutputStream outputStream, byte[] bytes, String description) {
        try {
            IOUtils.write(bytes, outputStream);
            if (log.isTraceEnabled()) {
                log.trace("writing " + description + " hex: " + Hex.bytesToHex(bytes));
            }
            return bytes;
        } catch (Throwable throwable) {
            throw new RuntimeException("Exception writing " + description, throwable);
        }
    }

    public byte[] encode(TTLV message) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        writeBytes(outputStream, message.getTag() != null ? message.getTag().getStart().toByteArray() : message.getTagBytes(), "tag");
        writeBytes(outputStream, message.getItemType().getStart().toByteArray(), "itemType");
        writeBytes(outputStream, hexToBytes(StringUtils.leftPad(BigInteger.valueOf(message.getLength()).toString(16), 8, "0")), "length");
        message.encode(outputStream, this::encode);
        return outputStream.toByteArray();
    }
}
