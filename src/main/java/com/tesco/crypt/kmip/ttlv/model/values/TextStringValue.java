package com.tesco.crypt.kmip.ttlv.model.values;

import com.tesco.crypt.kmip.operation.model.attributes.AttributeValue;
import com.tesco.crypt.kmip.operation.model.attributes.TextStringAttributeValue;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.ItemType;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Function;

import static com.tesco.crypt.kmip.ttlv.Hex.bytesToHex;
import static com.tesco.crypt.kmip.ttlv.Hex.hexToBytes;

@Data
public class TextStringValue extends Value<String> {

    public TextStringValue() {
        super(ItemType.TEXT_STRING);
    }

    String value;

    public TextStringValue setValue(String value) {
        this.value = value;
        setLength(value);
        return this;
    }

    private void setLength(String value) {
        if (value != null) {
            setLength(value.getBytes(StandardCharsets.UTF_8).length);
            setPadding(nextMultipleOfEight(getLength()) - getLength());
        }
    }

    @Override
    public void encode(OutputStream outputStream, Function<TTLV, byte[]> encode) {
        String hexString = StringUtils.rightPad(bytesToHex(getValue().getBytes(StandardCharsets.UTF_8)), getLength() * 2, "0");
        if (getPadding() > 0) {
            hexString += StringUtils.repeat("0", getPadding() * 2);
        }
        writeBytes(outputStream, hexToBytes(hexString));
    }

    @Override
    public AttributeValue<?> decode(String name) {
        return new TextStringAttributeValue().setName(name).setValue(getValue());
    }
}
