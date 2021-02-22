package com.tesco.crypt.kmip.ttlv.model.values;

import com.tesco.crypt.kmip.operation.model.attributes.AttributeValue;
import com.tesco.crypt.kmip.operation.model.attributes.BooleanAttributeValue;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.ItemType;
import lombok.Data;

import java.io.OutputStream;
import java.util.function.Function;

import static com.tesco.crypt.kmip.ttlv.Hex.hexToBytes;

@Data
public class BooleanValue extends Value<Boolean> {

    public BooleanValue() {
        super(ItemType.BOOLEAN);
        setLength(8);
    }

    Boolean value;

    @Override
    public void encode(OutputStream outputStream, Function<TTLV, byte[]> encode) {
        if (getValue()) {
            writeBytes(outputStream, hexToBytes("0000000000000001"));
        } else {
            writeBytes(outputStream, hexToBytes("0000000000000000"));
        }
    }

    @Override
    public AttributeValue<?> decode(String name) {
        return new BooleanAttributeValue().setName(name).setValue(getValue());
    }
}
