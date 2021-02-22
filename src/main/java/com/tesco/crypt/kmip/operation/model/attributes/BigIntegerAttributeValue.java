package com.tesco.crypt.kmip.operation.model.attributes;

import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.values.BigIntegerValue;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;

import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.ATTRIBUTE_VALUE;

@Data
public class BigIntegerAttributeValue extends AttributeValue<BigIntegerAttributeValue> {

    BigInteger value;

    @Override
    public List<TTLV> encode() {
        List<TTLV> attributeTtlvs = super.encode();
        attributeTtlvs.add(
            new BigIntegerValue()
                .setValue(getValue())
                .setTag(ATTRIBUTE_VALUE)
        );
        return attributeTtlvs;
    }

}
