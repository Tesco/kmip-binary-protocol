package com.tesco.crypt.kmip.ttlv.model.values;

import com.tesco.crypt.kmip.operation.model.attributes.AttributeValue;
import com.tesco.crypt.kmip.operation.model.attributes.LongIntegerAttributeValue;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.ItemType;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.math.BigInteger;
import java.util.function.Function;

import static com.tesco.crypt.kmip.ttlv.Hex.hexToBytes;

@Data
public class LongIntegerValue extends Value<Long> {

    Long value;

    public LongIntegerValue() {
        super(ItemType.LONG_INTEGER);
        setLength(8);
    }

    @Override
    public void encode(OutputStream outputStream, Function<TTLV, byte[]> encode) {
        writeBytes(outputStream, hexToBytes(StringUtils.leftPad(BigInteger.valueOf(getValue()).toString(16), 16, "0")));
    }

    @Override
    public AttributeValue<LongIntegerAttributeValue> decode(String name) {
        return new LongIntegerAttributeValue().setName(name).setValue(getValue());
    }
}
