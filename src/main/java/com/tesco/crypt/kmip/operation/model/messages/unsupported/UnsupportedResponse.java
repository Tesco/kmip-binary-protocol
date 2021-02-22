package com.tesco.crypt.kmip.operation.model.messages.unsupported;

import com.tesco.crypt.kmip.operation.model.messages.ResponseBatchItem;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.Operation;
import lombok.Data;

import java.util.Collections;
import java.util.List;

@Data
public class UnsupportedResponse extends ResponseBatchItem {

    Operation operation;

    @Override
    public Operation getOperation() {
        return operation;
    }

    @Override
    public void decode(List<TTLV> payloadItems) {

    }

    @Override
    public List<TTLV> encode() {
        return getOperationResponse(Collections.emptyList(), getOperation());
    }

}
