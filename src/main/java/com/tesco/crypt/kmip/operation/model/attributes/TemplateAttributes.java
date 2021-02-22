package com.tesco.crypt.kmip.operation.model.attributes;

import com.tesco.crypt.kmip.ttlv.model.Structure;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.MessageTag;
import com.tesco.crypt.kmip.ttlv.model.values.TextStringValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.checkAndCast;
import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.invalidMessageTag;
import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.*;

@Data
public class TemplateAttributes {

    List<String> attributeNames;
    Attributes attributes;

    public TemplateAttributes decode(List<TTLV> templateAttributes) {
        for (TTLV templateAttributeItem : templateAttributes) {
            switch (templateAttributeItem.getTag()) {
                case ATTRIBUTE_NAME:
                    attributeNames.add(checkAndCast(templateAttributeItem, TextStringValue.class).getValue());
                    break;
                case ATTRIBUTE:
                    if (attributes == null) {
                        attributes = new Attributes();
                    }
                    attributes.decode(checkAndCast(templateAttributeItem, Structure.class).getTtlvs());
                    break;
                default:
                    invalidMessageTag(templateAttributeItem, Arrays.asList(ATTRIBUTE_NAME, ATTRIBUTE_INDEX, ATTRIBUTE));
                    break;
            }
        }
        return this;
    }

    public List<TTLV> encode() {
        List<TTLV> templateAttributeItems = new ArrayList<>();
        List<TTLV> attributeItems = new ArrayList<>();
        if (attributes != null) {
            attributeItems.addAll(attributes.encode());
        }
        if (attributeNames != null) {
            for (String attributeName : attributeNames) {
                attributeItems.add(
                    new TextStringValue()
                        .setValue(attributeName)
                        .setTag(ATTRIBUTE_NAME)
                );
            }
        }
        templateAttributeItems.add(
            new Structure()
                .addTtlvs(attributeItems)
                .setTag(MessageTag.TEMPLATE_ATTRIBUTE)
        );
        return templateAttributeItems;
    }
}
