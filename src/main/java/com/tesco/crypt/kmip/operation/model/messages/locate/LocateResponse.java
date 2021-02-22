package com.tesco.crypt.kmip.operation.model.messages.locate;

import com.tesco.crypt.kmip.operation.model.messages.ResponseBatchItem;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.Operation;
import com.tesco.crypt.kmip.ttlv.model.values.TextStringValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.checkAndCast;
import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.invalidMessageTag;
import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.UNIQUE_IDENTIFIER;

@Data
public class LocateResponse extends ResponseBatchItem {

    private String objectId;

    @Override
    public Operation getOperation() {
        return Operation.LOCATE;
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
        return getOperationResponse(payload, getOperation());
    }

}
