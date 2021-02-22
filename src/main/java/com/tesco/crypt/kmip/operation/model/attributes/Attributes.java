package com.tesco.crypt.kmip.operation.model.attributes;

import com.tesco.crypt.kmip.ttlv.model.Structure;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.MessageTag;
import com.tesco.crypt.kmip.ttlv.model.values.IntegerValue;
import com.tesco.crypt.kmip.ttlv.model.values.TextStringValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.checkAndCast;
import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.invalidMessageTag;
import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.*;

@Data
public class Attributes {

    List<AttributeValue<?>> attributes;

    public Attributes decode(List<TTLV> attributes) {
        String attributeName = null;
        Integer attributeIndex = null;
        AttributeValue<?> attributeValue = null;
        for (TTLV attributeItems : attributes) {
            switch (attributeItems.getTag()) {
                case ATTRIBUTE_NAME:
                    attributeName = checkAndCast(attributeItems, TextStringValue.class).getValue();
                    break;
                case ATTRIBUTE_INDEX:
                    attributeIndex = checkAndCast(attributeItems, IntegerValue.class).getValue();
                    break;
                case ATTRIBUTE_VALUE:
                    attributeValue = attributeItems.decode(attributeName);
                    break;
                default:
                    invalidMessageTag(attributeItems, Arrays.asList(ATTRIBUTE_NAME, ATTRIBUTE_INDEX, ATTRIBUTE_VALUE));
            }
        }
        if (attributeValue == null) {
            attributeValue = new EmptyAttributeValue();
        }
        attributeValue.setIndex(attributeIndex);
        attributeValue.setName(attributeName);
        if (this.attributes == null) {
            this.attributes = new ArrayList<>();
        }
        this.attributes.add(attributeValue);
        return this;
    }

    public List<TTLV> encode() {
        List<TTLV> attributeItems = new ArrayList<>();
        if (attributes != null) {
            for (AttributeValue<?> attribute : attributes) {
                attributeItems.add(
                    new Structure()
                        .addTtlvs(attribute.encode())
                        .setTag(MessageTag.ATTRIBUTE)
                );
            }
        }
        return attributeItems;
    }


}
