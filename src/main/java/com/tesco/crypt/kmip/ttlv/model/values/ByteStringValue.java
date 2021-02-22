package com.tesco.crypt.kmip.ttlv.model.values;

import com.tesco.crypt.kmip.operation.model.attributes.AttributeValue;
import com.tesco.crypt.kmip.operation.model.attributes.ByteStringAttributeValue;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.ItemType;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.util.function.Function;

import static com.tesco.crypt.kmip.ttlv.Hex.bytesToHex;
import static com.tesco.crypt.kmip.ttlv.Hex.hexToBytes;

@Data
public class ByteStringValue extends Value<String> {

    public ByteStringValue() {
        super(ItemType.BYTE_STRING);
    }

    String value;

    public ByteStringValue setValue(String value) {
        this.value = value;
        setLength(value);
        return this;
    }

    private void setLength(String value) {
        if (value != null) {
            setLength(value.length() / 2);
            setPadding(nextMultipleOfEight(getLength()) - getLength());
        }
    }

    public byte[] getBytes() {
        return hexToBytes(value);
    }

    public ByteStringValue setBytes(byte[] bytes) {
        setValue(bytesToHex(bytes));
        return this;
    }

    @Override
    public void encode(OutputStream outputStream, Function<TTLV, byte[]> encode) {
        String hexString = StringUtils.rightPad(bytesToHex(hexToBytes(getValue())), getLength() * 2, "0");
        if (getPadding() > 0) {
            hexString += StringUtils.repeat("0", getPadding() * 2);
        }
        writeBytes(outputStream, hexToBytes(hexString));
    }

    @Override
    public AttributeValue<?> decode(String name) {
        return new ByteStringAttributeValue().setName(name).setValue(getValue());
    }
}
