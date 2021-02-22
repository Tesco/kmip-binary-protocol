package com.tesco.crypt.kmip.operation.model.messages.create;

import com.tesco.crypt.kmip.operation.model.attributes.TemplateAttributes;
import com.tesco.crypt.kmip.operation.model.messages.ResponseBatchItem;
import com.tesco.crypt.kmip.ttlv.model.Structure;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.ObjectType;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.Operation;
import com.tesco.crypt.kmip.ttlv.model.values.EnumerationValue;
import com.tesco.crypt.kmip.ttlv.model.values.TextStringValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.checkAndCast;
import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.invalidMessageTag;
import static com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum.parseEnumeration;
import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.*;

@Data
public class CreateResponse extends ResponseBatchItem {

    private ObjectType objectType;
    private String objectId;
    private TemplateAttributes templateAttributes;

    @Override
    public Operation getOperation() {
        return Operation.CREATE;
    }

    @Override
    public void decode(List<TTLV> payloadItems) {
        for (TTLV payloadItem : payloadItems) {
            switch (payloadItem.getTag()) {
                case OBJECT_TYPE:
                    objectType = parseEnumeration(payloadItem, ObjectType.class);
                    break;
                case UNIQUE_IDENTIFIER:
                    objectId = checkAndCast(payloadItem, TextStringValue.class).getValue();
                    break;
                case TEMPLATE_ATTRIBUTE:
                    templateAttributes = new TemplateAttributes().decode(checkAndCast(payloadItem, Structure.class).getTtlvs());
                    break;
                default:
                    invalidMessageTag(payloadItem, Arrays.asList(OBJECT_TYPE, UNIQUE_IDENTIFIER, TEMPLATE_ATTRIBUTE));
            }
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
        if (objectId != null) {
            payload.add(
                new TextStringValue()
                    .setValue(objectId)
                    .setTag(UNIQUE_IDENTIFIER)
            );
        }
        return getOperationResponse(payload, getOperation());
    }

}
