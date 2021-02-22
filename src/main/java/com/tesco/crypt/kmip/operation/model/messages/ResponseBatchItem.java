package com.tesco.crypt.kmip.operation.model.messages;

import com.tesco.crypt.kmip.operation.model.ProtocolVersion;
import com.tesco.crypt.kmip.ttlv.model.Structure;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.MessageTag;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.Operation;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.ResultReason;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.ResultStatus;
import com.tesco.crypt.kmip.ttlv.model.values.ByteStringValue;
import com.tesco.crypt.kmip.ttlv.model.values.EnumerationValue;
import com.tesco.crypt.kmip.ttlv.model.values.TextStringValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public abstract class ResponseBatchItem extends BatchItem {

    ResultStatus resultStatus;
    ResultReason resultReason;
    String resultMessage;
    String asynchronousCorrelationValue;

    protected List<TTLV> getOperationResponse(List<TTLV> payload, Operation operation) {
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
        if (resultStatus != null) {
            ttlvs.add(
                new EnumerationValue()
                    .setValue(resultStatus)
                    .setTag(MessageTag.RESULT_STATUS)
            );
        }
        if (resultReason != null) {
            ttlvs.add(
                new EnumerationValue()
                    .setValue(resultReason)
                    .setTag(MessageTag.RESULT_REASON)
            );
        }
        if (resultMessage != null) {
            ttlvs.add(
                new TextStringValue()
                    .setValue(resultMessage)
                    .setTag(MessageTag.RESULT_MESSAGE)
            );
        }
        if (asynchronousCorrelationValue != null) {
            ttlvs.add(
                new ByteStringValue()
                    .setValue(asynchronousCorrelationValue)
                    .setTag(MessageTag.ASYNCHRONOUS_CORRELATION_VALUE)
            );
        }
        if (payload != null && !payload.isEmpty()) {
            ttlvs.add(
                new Structure()
                    .addTtlvs(payload)
                    .setTag(MessageTag.RESPONSE_PAYLOAD)
            );
        }
        return ttlvs;
    }

    public ResponseBatchItem setVersion(ProtocolVersion version) {
        setHeader(new ResponseHeader().setVersion(version));
        return this;
    }

}
