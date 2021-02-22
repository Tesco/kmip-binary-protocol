package com.tesco.crypt.kmip.ttlv.model.values;

import com.tesco.crypt.kmip.operation.model.attributes.AttributeValue;
import com.tesco.crypt.kmip.operation.model.attributes.IntervalAttributeValue;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.ItemType;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.math.BigInteger;
import java.util.function.Function;

import static com.tesco.crypt.kmip.ttlv.Hex.hexToBytes;

@Data
public class IntervalValue extends Value<Integer> {

    public IntervalValue() {
        super(ItemType.INTERVAL);
        setLength(4);
        setPadding(4);
    }

    Integer value;

    @Override
    public void encode(OutputStream outputStream, Function<TTLV, byte[]> encode) {
        writeBytes(outputStream, hexToBytes(StringUtils.leftPad(BigInteger.valueOf(getValue()).toString(16), 8, "0") + StringUtils.repeat("0", 8)));
    }

    @Override
    public AttributeValue<?> decode(String name) {
        return new IntervalAttributeValue().setName(name).setValue(getValue());
    }
}
