package com.tesco.crypt.kmip.operation.model.messages.unsupported;

import com.tesco.crypt.kmip.operation.model.messages.RequestBatchItem;
import com.tesco.crypt.kmip.ttlv.model.Structure;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.MessageTag;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.Operation;
import com.tesco.crypt.kmip.ttlv.model.values.EnumerationValue;
import lombok.Data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
public class UnsupportedRequest extends RequestBatchItem {

    Operation operation;

    @Override
    public Operation getOperation() {
        return operation;
    }

    @Override
    public void decode(List<TTLV> payloadItems) {
        // do nothing
    }

    @Override
    public List<TTLV> encode() {
        return getOperationRequest(Collections.emptyList(), getOperation());
    }

}
