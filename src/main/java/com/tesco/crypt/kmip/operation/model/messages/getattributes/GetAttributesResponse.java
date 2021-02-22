package com.tesco.crypt.kmip.operation.model.messages.getattributes;

import com.tesco.crypt.kmip.operation.model.attributes.Attributes;
import com.tesco.crypt.kmip.operation.model.messages.ResponseBatchItem;
import com.tesco.crypt.kmip.ttlv.model.Structure;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.Operation;
import com.tesco.crypt.kmip.ttlv.model.values.TextStringValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.checkAndCast;
import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.invalidMessageTag;
import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.ATTRIBUTE;
import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.UNIQUE_IDENTIFIER;

@Data
public class GetAttributesResponse extends ResponseBatchItem {

    private String objectId;
    private Attributes attributes;

    @Override
    public Operation getOperation() {
        return Operation.GET_ATTRIBUTES;
    }

    @Override
    public void decode(List<TTLV> payloadItems) {
        for (TTLV payloadItem : payloadItems) {
            switch (payloadItem.getTag()) {
                case UNIQUE_IDENTIFIER:
                    objectId = checkAndCast(payloadItem, TextStringValue.class).getValue();
                    break;
                case ATTRIBUTE:
                    if (attributes == null) {
                        attributes = new Attributes();
                    }
                    attributes.decode(checkAndCast(payloadItem, Structure.class).getTtlvs());
                    break;
                default:
                    invalidMessageTag(payloadItem, Arrays.asList(UNIQUE_IDENTIFIER, ATTRIBUTE));
            }
        }
    }

    @Override
    public List<TTLV> encode() {
        List<TTLV> payload = new ArrayList<>();
        if (objectId != null) {
            payload.add(
                new TextStringValue()
                    .setValue(objectId)
                    .setTag(UNIQUE_IDENTIFIER)
            );
        }
        if (attributes != null) {
            payload.addAll(attributes.encode());
        }
        return getOperationResponse(payload, getOperation());
    }
}
