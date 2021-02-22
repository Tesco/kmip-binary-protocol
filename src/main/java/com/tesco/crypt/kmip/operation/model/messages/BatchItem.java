package com.tesco.crypt.kmip.operation.model.messages;

import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.Operation;
import lombok.Data;

import java.util.Base64;
import java.util.List;

@Data
public abstract class BatchItem {

    String id;
    Header header;

    public abstract Operation getOperation();

    public abstract void decode(List<TTLV> payloadItems);

    public abstract List<TTLV> encode();

    public String getObjectId() {
        return null;
    }

    public BatchItem setObjectId(String objectId) {
        return this;
    }
}
