package com.tesco.crypt.kmip.operation.model.attributes;

import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.values.ByteStringValue;
import lombok.Data;

import java.util.List;

import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.ATTRIBUTE_VALUE;

@Data
public class ByteStringAttributeValue extends AttributeValue<ByteStringAttributeValue> {

    String value;

    @Override
    public List<TTLV> encode() {
        List<TTLV> attributeTtlvs = super.encode();
        attributeTtlvs.add(
            new ByteStringValue()
                .setValue(getValue())
                .setTag(ATTRIBUTE_VALUE)
        );
        return attributeTtlvs;
    }

}
