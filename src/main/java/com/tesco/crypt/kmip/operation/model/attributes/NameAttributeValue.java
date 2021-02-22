package com.tesco.crypt.kmip.operation.model.attributes;

import com.tesco.crypt.kmip.ttlv.model.Structure;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.MessageTag;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.NameType;
import com.tesco.crypt.kmip.ttlv.model.values.EnumerationValue;
import com.tesco.crypt.kmip.ttlv.model.values.TextStringValue;
import lombok.Data;

import java.util.List;

import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.NAME_TYPE;
import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.NAME_VALUE;

@Data
public class NameAttributeValue extends AttributeValue<NameAttributeValue> {

    NameType type;
    String value;

    @Override
    public List<TTLV> encode() {
        List<TTLV> attributeTtlvs = super.encode();
        Structure structure = new Structure();
        if (value != null) {
            structure.addTtlvs(
                new TextStringValue()
                    .setValue(value)
                    .setTag(NAME_VALUE)
            );
        }
        if (type != null) {
            structure.addTtlvs(
                new EnumerationValue()
                    .setValue(type)
                    .setTag(NAME_TYPE)
            );
        }
        attributeTtlvs.add(structure.setTag(MessageTag.ATTRIBUTE_VALUE));
        return attributeTtlvs;
    }

}
