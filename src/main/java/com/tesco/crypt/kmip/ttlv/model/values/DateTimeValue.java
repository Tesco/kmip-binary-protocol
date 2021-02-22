package com.tesco.crypt.kmip.ttlv.model.values;

import com.tesco.crypt.kmip.operation.model.attributes.AttributeValue;
import com.tesco.crypt.kmip.operation.model.attributes.DateTimeAttributeValue;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.ItemType;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Date;
import java.util.function.Function;

import static com.tesco.crypt.kmip.ttlv.Hex.hexToBytes;

@Data
public class DateTimeValue extends Value<Date> {

    public DateTimeValue() {
        super(ItemType.DATE_TIME);
        setLength(8);
    }

    Date value;

    @Override
    public void encode(OutputStream outputStream, Function<TTLV, byte[]> encode) {
        writeBytes(outputStream, hexToBytes(StringUtils.leftPad(BigInteger.valueOf(getValue().getTime() / 1000).toString(16), 16, "0")));
    }

    @Override
    public AttributeValue<?> decode(String name) {
        return new DateTimeAttributeValue().setName(name).setValue(getValue());
    }
}
