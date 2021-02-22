package com.tesco.crypt.kmip.operation.model.messages.activate;

import com.tesco.crypt.kmip.operation.model.messages.RequestBatchItem;
import com.tesco.crypt.kmip.ttlv.model.Structure;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.MessageTag;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.Operation;
import com.tesco.crypt.kmip.ttlv.model.values.EnumerationValue;
import com.tesco.crypt.kmip.ttlv.model.values.TextStringValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.checkAndCast;
import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.invalidMessageTag;
import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.UNIQUE_IDENTIFIER;

@Data
public class ActivateRequest extends RequestBatchItem {

    private String objectId;

    @Override
    public Operation getOperation() {
        return Operation.ACTIVATE;
    }

    @Override
    public void decode(List<TTLV> payloadItems) {
        for (TTLV payloadItem : payloadItems) {
            if (payloadItem.getTag() == UNIQUE_IDENTIFIER) {
                objectId = checkAndCast(payloadItem, TextStringValue.class).getValue();
            } else {
                invalidMessageTag(payloadItem, Collections.singletonList(UNIQUE_IDENTIFIER));
            }
        }
    }

    @Override
    public List<TTLV> encode() {
        List<TTLV> payload = new ArrayList<>();
        if (getObjectId() != null) {
            payload.add(
                new TextStringValue()
                    .setValue(getObjectId())
                    .setTag(UNIQUE_IDENTIFIER)
            );
        }
        return getOperationRequest(payload, getOperation());
    }

}
