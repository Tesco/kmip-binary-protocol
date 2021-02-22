package com.tesco.crypt.kmip.operation;

import com.tesco.crypt.kmip.Encoder;
import com.tesco.crypt.kmip.operation.model.messages.*;
import com.tesco.crypt.kmip.ttlv.model.Structure;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.MessageTag;

import java.util.ArrayList;
import java.util.List;

import static com.tesco.crypt.kmip.operation.model.ProtocolVersion.VERSION_ONE_ONE;

public class BatchItemsToTTLVEncoder implements Encoder<List<BatchItem>, TTLV> {

    public TTLV encode(List<BatchItem> batchItems) {
        List<TTLV> messages = new ArrayList<>();
        Header header = null;
        MessageTag messageTag = null;

        // process each batch item
        for (BatchItem batchItem : batchItems) {
            // request or response
            messageTag = buildMessageTag(messageTag, batchItem);
            // header
            if (header == null && batchItem.getHeader() != null) {
                header = batchItem.getHeader();
            }
            // batch items
            List<TTLV> decode = batchItem.encode();
            if (decode != null) {
                messages.addAll(decode);
            }
        }

        // enforce correct header
        header = enforceCorrectHeader(batchItems, header);

        // return message
        return new Structure()
            .addTtlvs(
                header.encode(),
                new Structure()
                    .addTtlvs(messages)
                    .setTag(MessageTag.BATCH_ITEM)
            )
            .setTag(messageTag);
    }

    private MessageTag buildMessageTag(MessageTag messageTag, BatchItem batchItem) {
        if (messageTag == null) {
            if (batchItem instanceof RequestBatchItem) {
                messageTag = MessageTag.REQUEST_MESSAGE;
            } else if (batchItem instanceof ResponseBatchItem) {
                messageTag = MessageTag.RESPONSE_MESSAGE;
            }
        } else {
            if ((messageTag == MessageTag.REQUEST_MESSAGE && !(batchItem instanceof RequestBatchItem)) ||
                (messageTag == MessageTag.RESPONSE_MESSAGE && !(batchItem instanceof ResponseBatchItem))) {
                throw new IllegalArgumentException("batch items should contain only RequestBatchItem or ResponseBatchItem");
            }
        }
        return messageTag;
    }

    private Header enforceCorrectHeader(List<BatchItem> batchItems, Header header) {
        if (batchItems != null && !batchItems.isEmpty()) {
            if (batchItems.get(0) instanceof RequestBatchItem) {
                if (header == null) {
                    header = new RequestHeader().setVersion(VERSION_ONE_ONE).setBatchCount(batchItems.size());
                } else {
                    if (!(header instanceof RequestHeader)) {
                        throw new IllegalArgumentException("Found " + header.getClass().getSimpleName() + " expected " + RequestHeader.class.getSimpleName());
                    }
                    header.setBatchCount(batchItems.size());
                }
            } else if (batchItems.get(0) instanceof ResponseBatchItem) {
                if (header == null) {
                    header = new ResponseHeader().setVersion(VERSION_ONE_ONE).setBatchCount(batchItems.size());
                } else {
                    if (!(header instanceof ResponseHeader)) {
                        throw new IllegalArgumentException("Found " + header.getClass().getSimpleName() + " expected " + ResponseHeader.class.getSimpleName());
                    }
                    header.setBatchCount(batchItems.size());
                }
            } else {
                throw new IllegalArgumentException("Found " + batchItems.get(0).getClass().getSimpleName() + " expected " + RequestBatchItem.class.getSimpleName() + " or " + ResponseBatchItem.class.getSimpleName());
            }
        }
        return header;
    }

}
