package com.tesco.crypt.kmip.operation.model.attributes;

import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum;
import com.tesco.crypt.kmip.ttlv.model.values.EnumerationValue;
import lombok.Data;

import java.util.List;

import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.ATTRIBUTE_VALUE;

@Data
public class EnumerationAttributeValue extends AttributeValue<EnumerationAttributeValue> {

    ByteEnum<?> value;

    @Override
    public List<TTLV> encode() {
        List<TTLV> attributeTtlvs = super.encode();
        attributeTtlvs.add(
            new EnumerationValue()
                .setValue(getValue())
                .setTag(ATTRIBUTE_VALUE)
        );
        return attributeTtlvs;
    }

}
