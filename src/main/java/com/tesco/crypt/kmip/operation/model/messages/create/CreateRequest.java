package com.tesco.crypt.kmip.operation.model.messages.create;

import com.tesco.crypt.kmip.operation.model.attributes.AttributeValue;
import com.tesco.crypt.kmip.operation.model.attributes.TemplateAttributes;
import com.tesco.crypt.kmip.operation.model.messages.RequestBatchItem;
import com.tesco.crypt.kmip.operation.model.templates.KMIPTemplate;
import com.tesco.crypt.kmip.ttlv.model.Structure;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.ObjectType;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.Operation;
import com.tesco.crypt.kmip.ttlv.model.values.EnumerationValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.checkAndCast;
import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.invalidMessageTag;
import static com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum.parseEnumeration;
import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.OBJECT_TYPE;
import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.TEMPLATE_ATTRIBUTE;

@Data
public class CreateRequest extends RequestBatchItem {

    private ObjectType objectType;
    private TemplateAttributes templateAttributes;
    private KMIPTemplate kmipTemplate;

    @Override
    public Operation getOperation() {
        return Operation.CREATE;
    }

    public List<AttributeValue<?>> getAttributeValues() {
        return getTemplateAttributes().getAttributes().getAttributes();
    }

    @Override
    public void decode(List<TTLV> payloadItems) {
        List<TTLV> templateItems = Collections.emptyList();
        for (TTLV payloadItem : payloadItems) {
            switch (payloadItem.getTag()) {
                case OBJECT_TYPE:
                    objectType = parseEnumeration(payloadItem, ObjectType.class);
                    break;
                case TEMPLATE_ATTRIBUTE:
                    templateItems = checkAndCast(payloadItem, Structure.class).getTtlvs();
                    templateAttributes = new TemplateAttributes().decode(templateItems);
                    break;
                default:
                    invalidMessageTag(payloadItem, Arrays.asList(OBJECT_TYPE, TEMPLATE_ATTRIBUTE));
            }
        }
        try {
            kmipTemplate = objectType.getTemplateClass().getDeclaredConstructor().newInstance();
            kmipTemplate.decode(templateItems);
        } catch (Exception ex) {
            throw new RuntimeException("Exception constructing and initialising " + objectType.getObjectClass(), ex);
        }
    }

    @Override
    public List<TTLV> encode() {
        List<TTLV> payload = new ArrayList<>();
        if (objectType != null) {
            payload.add(
                new EnumerationValue()
                    .setValue(objectType)
                    .setTag(OBJECT_TYPE)
            );
        }
        if (templateAttributes != null) {
            payload.addAll(templateAttributes.encode());
        }
        return getOperationRequest(payload, getOperation());
    }

}
