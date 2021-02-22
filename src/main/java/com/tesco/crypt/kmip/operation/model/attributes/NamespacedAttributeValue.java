package com.tesco.crypt.kmip.operation.model.attributes;

import com.tesco.crypt.kmip.ttlv.model.Structure;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.MessageTag;
import com.tesco.crypt.kmip.ttlv.model.values.TextStringValue;
import lombok.Data;

import java.util.List;

import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.APPLICATION_DATA;
import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.APPLICATION_NAMESPACE;

@Data
public class NamespacedAttributeValue extends AttributeValue<NamespacedAttributeValue> {

    String namespace;
    String value;

    @Override
    public List<TTLV> encode() {
        List<TTLV> attributeTtlvs = super.encode();
        Structure structure = new Structure();
        if (namespace != null) {
            structure.addTtlvs(
                new TextStringValue()
                    .setValue(namespace)
                    .setTag(APPLICATION_NAMESPACE)
            );
        }
        if (value != null) {
            structure.addTtlvs(
                new TextStringValue()
                    .setValue(value)
                    .setTag(APPLICATION_DATA)
            );
        }
        attributeTtlvs.add(structure.setTag(MessageTag.ATTRIBUTE_VALUE));
        return attributeTtlvs;
    }

}
