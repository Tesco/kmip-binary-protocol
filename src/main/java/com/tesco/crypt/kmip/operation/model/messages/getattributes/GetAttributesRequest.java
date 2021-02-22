package com.tesco.crypt.kmip.operation.model.messages.getattributes;

import com.tesco.crypt.kmip.operation.model.messages.RequestBatchItem;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.Operation;
import com.tesco.crypt.kmip.ttlv.model.values.TextStringValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.checkAndCast;
import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.invalidMessageTag;
import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.ATTRIBUTE_NAME;
import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.UNIQUE_IDENTIFIER;

@Data
public class GetAttributesRequest extends RequestBatchItem {

    private String objectId;
    private List<String> attributeNames = new ArrayList<>();

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
                case ATTRIBUTE_NAME:
                    attributeNames.add(checkAndCast(payloadItem, TextStringValue.class).getValue());
                    break;
                default:
                    invalidMessageTag(payloadItem, Arrays.asList(UNIQUE_IDENTIFIER, ATTRIBUTE_NAME));
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
        for (String attributeName : attributeNames) {
            payload.add(new TextStringValue()
                .setValue(attributeName)
                .setTag(ATTRIBUTE_NAME)
            );
        }
        return getOperationRequest(payload, getOperation());
    }

}
