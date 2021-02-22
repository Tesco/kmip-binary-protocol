package com.tesco.crypt.kmip.operation.model.messages.get;

import com.tesco.crypt.kmip.operation.model.messages.BatchItem;
import com.tesco.crypt.kmip.operation.model.messages.ResponseBatchItem;
import com.tesco.crypt.kmip.operation.model.objects.KMIPObject;
import com.tesco.crypt.kmip.ttlv.model.Structure;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.MessageTag;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.ObjectType;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.Operation;
import com.tesco.crypt.kmip.ttlv.model.values.TextStringValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.checkAndCast;
import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.invalidMessageTag;
import static com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum.parseEnumeration;
import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.OBJECT_TYPE;
import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.UNIQUE_IDENTIFIER;
import static com.tesco.crypt.kmip.ttlv.model.enums.enumerations.ObjectType.OBJECT_MESSAGE_TAGS;

@Data
public class GetResponse extends ResponseBatchItem {

    private KMIPObject kmipObject;

    @Override
    public Operation getOperation() {
        return Operation.GET;
    }

    @Override
    public void decode(List<TTLV> payloadItems) {
        ObjectType objectType = null;
        String objectId = null;
        List<TTLV> objectItems = null;
        MessageTag objectMessageTag = null;
        for (TTLV payloadItem : payloadItems) {
            if (payloadItem.getTag() == OBJECT_TYPE) {
                objectType = parseEnumeration(payloadItem, ObjectType.class);
            } else if (payloadItem.getTag() == UNIQUE_IDENTIFIER) {
                objectId = checkAndCast(payloadItem, TextStringValue.class).getValue();
            } else if (OBJECT_MESSAGE_TAGS.contains(payloadItem.getTag())) {
                objectMessageTag = payloadItem.getTag();
                objectItems = checkAndCast(payloadItem, Structure.class).getTtlvs();
            } else {
                List<MessageTag> allowedMessageTags = new ArrayList<>(Arrays.asList(OBJECT_TYPE, UNIQUE_IDENTIFIER));
                allowedMessageTags.addAll(OBJECT_MESSAGE_TAGS);
                invalidMessageTag(payloadItem, allowedMessageTags);
            }
        }
        if (objectType == null) {
            throw new IllegalArgumentException("OBJECT_TYPE not specified in GET RESPONSE_PAYLOAD");
        }
        if (objectId == null) {
            throw new IllegalArgumentException("object UNIQUE_IDENTIFIER not specified in GET RESPONSE_PAYLOAD");
        }
        if (objectItems == null || objectMessageTag == null) {
            throw new IllegalArgumentException("one of " + OBJECT_MESSAGE_TAGS + " not specified in GET RESPONSE_PAYLOAD");
        } else if (!objectMessageTag.name().equals(objectType.name())) {
            throw new IllegalArgumentException("object structure with MessageTag of " + objectType + " does not match OBJECT_TYPE of " + objectMessageTag);
        }
        try {
            kmipObject = objectType.getObjectClass().getDeclaredConstructor().newInstance();
            kmipObject.decode(objectId, objectItems);
        } catch (Exception ex) {
            throw new RuntimeException("Exception constructing and initialising " + objectType.getObjectClass(), ex);
        }
    }

    @Override
    public List<TTLV> encode() {
        List<TTLV> payload = new ArrayList<>();
        if (kmipObject != null) {
            payload.addAll(kmipObject.encode());
        }
        return getOperationResponse(payload, getOperation());
    }

    @Override
    public String getObjectId() {
        return kmipObject == null ? null : kmipObject.getObjectId();
    }

    @Override
    public BatchItem setObjectId(String objectId) {
        kmipObject.setObjectId(objectId);
        return this;
    }
}
