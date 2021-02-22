package com.tesco.crypt.kmip.ttlv.model.values;

import com.tesco.crypt.kmip.operation.model.attributes.AttributeValue;
import com.tesco.crypt.kmip.operation.model.attributes.BigIntegerAttributeValue;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.ItemType;
import lombok.Data;

import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.function.Function;

@Data
public class BigIntegerValue extends Value<BigInteger> {

    public BigIntegerValue() {
        super(ItemType.BIG_INTEGER);
    }

    BigInteger value;

    public BigIntegerValue setValue(BigInteger value) {
        this.value = value;
        setLength(value);
        return this;
    }

    private void setLength(BigInteger value) {
        if (value != null) {
            setLength(nextMultipleOfEight(getValue().toByteArray().length));
        }
    }

    @Override
    public void encode(OutputStream outputStream, Function<TTLV, byte[]> encode) {
        byte[] bytes = getValue().toByteArray();
        int paddedLength = nextMultipleOfEight(bytes.length);
        if (bytes.length != paddedLength) {
            int paddingRequired = paddedLength - bytes.length;
            byte[] paddedBytes = new byte[paddedLength];
            if (getValue().signum() < 0) Arrays.fill(paddedBytes, 0, paddingRequired, (byte) -1);
            System.arraycopy(bytes, 0, paddedBytes, paddingRequired, bytes.length);
            bytes = paddedBytes;
        }
        writeBytes(outputStream, bytes);
    }

    @Override
    public AttributeValue<?> decode(String name) {
        return new BigIntegerAttributeValue().setName(name).setValue(getValue());
    }
}
