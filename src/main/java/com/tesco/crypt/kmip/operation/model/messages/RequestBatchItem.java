package com.tesco.crypt.kmip.operation.model.messages;

import com.tesco.crypt.kmip.operation.model.ProtocolVersion;
import com.tesco.crypt.kmip.ttlv.model.Structure;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.MessageTag;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.Operation;
import com.tesco.crypt.kmip.ttlv.model.values.ByteStringValue;
import com.tesco.crypt.kmip.ttlv.model.values.EnumerationValue;
import com.tesco.crypt.kmip.ttlv.model.values.TextStringValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class RequestBatchItem extends BatchItem {

    public RequestBatchItem setVersion(ProtocolVersion version) {
        setHeader(new RequestHeader().setVersion(version));
        return this;
    }

    protected List<TTLV> getOperationRequest(List<TTLV> payload, Operation operation) {
        List<TTLV> ttlvs = new ArrayList<>();
        if (operation != null) {
            ttlvs.add(
                new EnumerationValue()
                    .setValue(operation)
                    .setTag(MessageTag.OPERATION)
            );
        }
        if (id != null) {
            ttlvs.add(
                new ByteStringValue()
                    .setValue(id)
                    .setTag(MessageTag.UNIQUE_BATCH_ITEM_ID)
            );
        }
        if (payload != null) {
            ttlvs.add(
                new Structure()
                    .addTtlvs(payload)
                    .setTag(MessageTag.REQUEST_PAYLOAD)
            );
        }
        return ttlvs;
    }

}
