package com.tesco.crypt.kmip.operation.model.messages.locate;

import com.tesco.crypt.kmip.operation.model.attributes.Attributes;
import com.tesco.crypt.kmip.operation.model.messages.RequestBatchItem;
import com.tesco.crypt.kmip.ttlv.model.Structure;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.Operation;
import com.tesco.crypt.kmip.ttlv.model.values.TextStringValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.checkAndCast;
import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.invalidMessageTag;
import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.ATTRIBUTE;
import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.UNIQUE_IDENTIFIER;

@Data
public class LocateRequest extends RequestBatchItem {

    private Attributes attributes;

    @Override
    public Operation getOperation() {
        return Operation.LOCATE;
    }

    @Override
    public void decode(List<TTLV> payloadItems) {
        for (TTLV payloadItem : payloadItems) {
            if (payloadItem.getTag() == ATTRIBUTE) {
                if (attributes == null) {
                    attributes = new Attributes();
                }
                attributes.decode(checkAndCast(payloadItem, Structure.class).getTtlvs());
            } else {
                invalidMessageTag(payloadItem, Collections.singletonList(ATTRIBUTE));
            }
        }
    }

    @Override
    public List<TTLV> encode() {
        List<TTLV> payload = new ArrayList<>();
        if (attributes != null) {
            payload.addAll(attributes.encode());
        }
        return getOperationRequest(payload, getOperation());
    }

}
