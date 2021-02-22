package com.tesco.crypt.kmip.operation.model.attributes;

import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.values.DateTimeValue;
import lombok.Data;

import java.util.Date;
import java.util.List;

import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.ATTRIBUTE_VALUE;

@Data
public class DateTimeAttributeValue extends AttributeValue<DateTimeAttributeValue> {

    Date value;

    @Override
    public List<TTLV> encode() {
        List<TTLV> attributeTtlvs = super.encode();
        attributeTtlvs.add(
            new DateTimeValue()
                .setValue(getValue())
                .setTag(ATTRIBUTE_VALUE)
        );
        return attributeTtlvs;
    }

}
