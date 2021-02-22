package com.tesco.crypt.kmip.operation.model.attributes;

import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.values.IntegerValue;
import lombok.Data;

import java.util.List;

import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.ATTRIBUTE_VALUE;

@Data
public class IntegerAttributeValue extends AttributeValue<IntegerAttributeValue> {

    Integer value;

    @Override
    public List<TTLV> encode() {
        List<TTLV> attributeTtlvs = super.encode();
        attributeTtlvs.add(
            new IntegerValue()
                .setValue(getValue())
                .setTag(ATTRIBUTE_VALUE)
        );
        return attributeTtlvs;
    }
}
