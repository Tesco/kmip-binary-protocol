package com.tesco.crypt.kmip.ttlv.model.values;

import com.tesco.crypt.kmip.operation.model.attributes.AttributeValue;
import com.tesco.crypt.kmip.operation.model.attributes.EnumerationAttributeValue;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum;
import com.tesco.crypt.kmip.ttlv.model.enums.ItemType;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.math.BigInteger;
import java.util.function.Function;

import static com.tesco.crypt.kmip.ttlv.Hex.hexToBytes;

@Data
public class EnumerationValue extends Value<ByteEnum<?>> {

    public EnumerationValue() {
        super(ItemType.ENUMERATION);
        setLength(4);
        setPadding(4);
    }

    ByteEnum<?> value;
    Integer integer;

    public EnumerationValue setValue(ByteEnum<?> value) {
        this.value = value;
        if (value != null) {
            this.integer = value.getStart().intValue();
        } else {
            this.integer = null;
        }
        return this;
    }

    @Override
    public void encode(OutputStream outputStream, Function<TTLV, byte[]> encode) {
        String enumerationHexString = "";
        if (getValue() != null) {
            enumerationHexString = StringUtils.leftPad(getValue().getStart().toString(16), 8, "0") + StringUtils.repeat("0", 8);
        } else {
            enumerationHexString = StringUtils.leftPad(BigInteger.valueOf(getInteger()).toString(16), 8, "0") + StringUtils.repeat("0", 8);
        }
        writeBytes(outputStream, hexToBytes(enumerationHexString));
    }

    @Override
    public AttributeValue<?> decode(String name) {
        return new EnumerationAttributeValue().setName(name).setValue(getValue());
    }
}
