package com.tesco.crypt.kmip.operation;

import com.tesco.crypt.kmip.Decoder;
import com.tesco.crypt.kmip.operation.model.messages.*;
import com.tesco.crypt.kmip.operation.model.messages.activate.ActivateRequest;
import com.tesco.crypt.kmip.operation.model.messages.activate.ActivateResponse;
import com.tesco.crypt.kmip.operation.model.messages.create.CreateRequest;
import com.tesco.crypt.kmip.operation.model.messages.create.CreateResponse;
import com.tesco.crypt.kmip.operation.model.messages.destroy.DestroyRequest;
import com.tesco.crypt.kmip.operation.model.messages.destroy.DestroyResponse;
import com.tesco.crypt.kmip.operation.model.messages.discoverversions.DiscoverVersionsRequest;
import com.tesco.crypt.kmip.operation.model.messages.discoverversions.DiscoverVersionsResponse;
import com.tesco.crypt.kmip.operation.model.messages.get.GetRequest;
import com.tesco.crypt.kmip.operation.model.messages.get.GetResponse;
import com.tesco.crypt.kmip.operation.model.messages.getattributes.GetAttributesRequest;
import com.tesco.crypt.kmip.operation.model.messages.getattributes.GetAttributesResponse;
import com.tesco.crypt.kmip.operation.model.messages.locate.LocateRequest;
import com.tesco.crypt.kmip.operation.model.messages.locate.LocateResponse;
import com.tesco.crypt.kmip.operation.model.messages.unsupported.UnsupportedRequest;
import com.tesco.crypt.kmip.operation.model.messages.unsupported.UnsupportedResponse;
import com.tesco.crypt.kmip.ttlv.model.Structure;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.MessageTag;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.Operation;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.ResultReason;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.ResultStatus;
import com.tesco.crypt.kmip.ttlv.model.values.ByteStringValue;
import com.tesco.crypt.kmip.ttlv.model.values.TextStringValue;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

import static com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum.parseEnumeration;
import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.*;

@Slf4j
public class TTLVToBatchItemsDecoder<T extends BatchItem> implements Decoder<TTLV, T> {

    public static void invalidMessageTag(TTLV ttlv, List<MessageTag> messageTags) {
        throw new IllegalArgumentException("invalid tag " + ttlv.getTag() + " expected " + messageTags + " at " + ttlv.getPath());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void decode(TTLV ttlv, Consumer<T> batchItemConsumer) {
        if (ttlv instanceof Structure) {
            switch (ttlv.getTag()) {
                case REQUEST_MESSAGE:
                    decodeRequest(ttlv, (Consumer<RequestBatchItem>) batchItemConsumer);
                    break;
                case RESPONSE_MESSAGE:
                    decodeResponse(ttlv, (Consumer<ResponseBatchItem>) batchItemConsumer);
                    break;
                default:
                    invalidMessageTag(ttlv, Arrays.asList(REQUEST_MESSAGE, RESPONSE_MESSAGE));
            }
        }
    }

    public void decodeRequest(TTLV ttlv, Consumer<RequestBatchItem> batchItemConsumer) {
        if (ttlv instanceof Structure) {
            Header header = null;
            Integer batchItemsFound = 0;
            if (ttlv.getTag() == REQUEST_MESSAGE) {
                for (TTLV messageItem : ((Structure) ttlv).getTtlvs()) {
                    switch (messageItem.getTag()) {
                        case REQUEST_HEADER:
                            header = new RequestHeader().decode(checkAndCast(messageItem, Structure.class).getTtlvs());
                            break;
                        case BATCH_ITEM:
                            batchItemsFound++;
                            RequestBatchItem batchItem = null;
                            String batchItemId = null;
                            List<TTLV> payload = null;
                            for (TTLV batchItemItem : ((Structure) messageItem).getTtlvs()) {
                                switch (batchItemItem.getTag()) {
                                    case OPERATION:
                                        Operation operation = parseEnumeration(batchItemItem, Operation.class);
                                        switch (operation) {
                                            case DISCOVER_VERSIONS:
                                                batchItem = new DiscoverVersionsRequest();
                                                break;
                                            case CREATE:
                                                batchItem = new CreateRequest();
                                                break;
                                            case GET:
                                                batchItem = new GetRequest();
                                                break;
                                            case GET_ATTRIBUTES:
                                                batchItem = new GetAttributesRequest();
                                                break;
                                            case DESTROY:
                                                batchItem = new DestroyRequest();
                                                break;
                                            case ACTIVATE:
                                                batchItem = new ActivateRequest();
                                                break;
                                            case LOCATE:
                                                batchItem = new LocateRequest();
                                                break;
                                            case GET_ATTRIBUTE_LIST:
                                            case REVOKE:
                                            case QUERY:
                                            case CANCEL:
                                            case GET_USAGE_ALLOCATION:
                                            case CREATE_KEY_PAIR:
                                            case REGISTER:
                                            case RE_KEY:
                                            case DERIVE_KEY:
                                            case CERTIFY:
                                            case RE_CERTIFY:
                                            case CHECK:
                                            case ADD_ATTRIBUTE:
                                            case MODIFY_ATTRIBUTE:
                                            case DELETE_ATTRIBUTE:
                                            case OBTAIN_LEASE:
                                            case ARCHIVE:
                                            case RECOVER:
                                            case VALIDATE:
                                            case POLL:
                                            case NOTIFY:
                                            case PUT:
                                            case RE_KEY_KEY_PAIR:
                                            case EXTENSIONS:
                                            default:
                                                batchItem = new UnsupportedRequest().setOperation(operation);
                                                log.warn(
                                                    operation + " is not a supported operation at " + ttlv.getPath(),
                                                    new UnsupportedOperationException(operation + " is not a supported operation at " + ttlv.getPath())
                                                );
                                        }
                                        break;
                                    case UNIQUE_BATCH_ITEM_ID:
                                        batchItemId = checkAndCast(batchItemItem, ByteStringValue.class).getValue();
                                        break;
                                    case REQUEST_PAYLOAD:
                                        payload = checkAndCast(batchItemItem, Structure.class).getTtlvs();
                                        break;
                                    default:
                                        invalidMessageTag(batchItemItem, Arrays.asList(OPERATION, UNIQUE_BATCH_ITEM_ID, REQUEST_PAYLOAD));
                                }
                            }
                            if (batchItem == null) {
                                batchItem = new UnsupportedRequest();
                            }
                            batchItem.setHeader(header);
                            batchItem.setId(batchItemId);
                            if (payload != null && !payload.isEmpty()) {
                                batchItem.decode(payload);
                            }
                            batchItemConsumer.accept(batchItem);
                            break;
                        default:
                            invalidMessageTag(messageItem, Arrays.asList(REQUEST_HEADER, BATCH_ITEM));
                    }
                }
            } else {
                invalidMessageTag(ttlv, Collections.singletonList(REQUEST_MESSAGE));
            }
            if (header != null && !batchItemsFound.equals(header.getBatchCount())) {
                throw new IllegalArgumentException("invalid number of batch items found, expected " + header.getBatchCount() + " found " + batchItemsFound + " at " + ttlv.getPath());
            }
        }
    }

    public void decodeResponse(TTLV ttlv, Consumer<ResponseBatchItem> batchItemConsumer) {
        if (ttlv instanceof Structure) {
            Header header = null;
            Integer batchItemsFound = 0;
            if (ttlv.getTag() == RESPONSE_MESSAGE) {
                for (TTLV messageItem : ((Structure) ttlv).getTtlvs()) {
                    switch (messageItem.getTag()) {
                        case RESPONSE_HEADER:
                            header = new ResponseHeader().decode(checkAndCast(messageItem, Structure.class).getTtlvs());
                            break;
                        case BATCH_ITEM:
                            batchItemsFound++;
                            ResponseBatchItem batchItem = null;
                            String batchItemId = null;
                            ResultStatus resultStatus = null;
                            ResultReason resultReason = null;
                            String resultMessage = null;
                            String asynchronousCorrelationValue = null;
                            List<TTLV> payload = null;
                            for (TTLV batchItemItem : ((Structure) messageItem).getTtlvs()) {
                                switch (batchItemItem.getTag()) {
                                    case OPERATION:
                                        Operation operation = parseEnumeration(batchItemItem, Operation.class);
                                        switch (operation) {
                                            case DISCOVER_VERSIONS:
                                                batchItem = new DiscoverVersionsResponse();
                                                break;
                                            case CREATE:
                                                batchItem = new CreateResponse();
                                                break;
                                            case GET:
                                                batchItem = new GetResponse();
                                                break;
                                            case GET_ATTRIBUTES:
                                                batchItem = new GetAttributesResponse();
                                                break;
                                            case DESTROY:
                                                batchItem = new DestroyResponse();
                                                break;
                                            case ACTIVATE:
                                                batchItem = new ActivateResponse();
                                                break;
                                            case LOCATE:
                                                batchItem = new LocateResponse();
                                                break;
                                            case GET_ATTRIBUTE_LIST:
                                            case REVOKE:
                                            case QUERY:
                                            case CANCEL:
                                            case GET_USAGE_ALLOCATION:
                                            case CREATE_KEY_PAIR:
                                            case REGISTER:
                                            case RE_KEY:
                                            case DERIVE_KEY:
                                            case CERTIFY:
                                            case RE_CERTIFY:
                                            case CHECK:
                                            case ADD_ATTRIBUTE:
                                            case MODIFY_ATTRIBUTE:
                                            case DELETE_ATTRIBUTE:
                                            case OBTAIN_LEASE:
                                            case ARCHIVE:
                                            case RECOVER:
                                            case VALIDATE:
                                            case POLL:
                                            case NOTIFY:
                                            case PUT:
                                            case RE_KEY_KEY_PAIR:
                                            case EXTENSIONS:
                                            default:
                                                batchItem = new UnsupportedResponse().setOperation(operation);
                                                log.warn(
                                                    operation + " is not a supported operation at " + ttlv.getPath(),
                                                    new UnsupportedOperationException(operation + " is not a supported operation at " + ttlv.getPath())
                                                );
                                        }
                                        break;
                                    case RESULT_STATUS:
                                        resultStatus = parseEnumeration(batchItemItem, ResultStatus.class);
                                        break;
                                    case RESULT_REASON:
                                        resultReason = parseEnumeration(batchItemItem, ResultReason.class);
                                        break;
                                    case RESULT_MESSAGE:
                                        resultMessage = checkAndCast(batchItemItem, TextStringValue.class).getValue();
                                        break;
                                    case ASYNCHRONOUS_CORRELATION_VALUE:
                                        asynchronousCorrelationValue = checkAndCast(batchItemItem, ByteStringValue.class).getValue();
                                        break;
                                    case UNIQUE_BATCH_ITEM_ID:
                                        batchItemId = checkAndCast(batchItemItem, ByteStringValue.class).getValue();
                                        break;
                                    case RESPONSE_PAYLOAD:
                                        payload = checkAndCast(batchItemItem, Structure.class).getTtlvs();
                                        break;
                                    default:
                                        invalidMessageTag(batchItemItem, Arrays.asList(OPERATION, RESULT_STATUS, RESULT_REASON, RESULT_MESSAGE, UNIQUE_BATCH_ITEM_ID, REQUEST_PAYLOAD));
                                }
                            }
                            if (batchItem == null) {
                                batchItem = new UnsupportedResponse();
                            }
                            batchItem.setHeader(header);
                            batchItem.setId(batchItemId);
                            batchItem.setResultStatus(resultStatus);
                            batchItem.setResultReason(resultReason);
                            batchItem.setResultMessage(resultMessage);
                            batchItem.setAsynchronousCorrelationValue(asynchronousCorrelationValue);
                            if (payload != null && !payload.isEmpty()) {
                                batchItem.decode(payload);
                            }
                            batchItemConsumer.accept(batchItem);
                            break;
                        default:
                            invalidMessageTag(messageItem, Arrays.asList(RESPONSE_HEADER, BATCH_ITEM));
                    }
                }
            } else {
                invalidMessageTag(ttlv, Collections.singletonList(RESPONSE_MESSAGE));
            }
            if (header != null && !batchItemsFound.equals(header.getBatchCount())) {
                throw new IllegalArgumentException("invalid number of batch items found, expected " + header.getBatchCount() + " found " + batchItemsFound + " at " + ttlv.getPath());
            }
        }
    }


    public static <T> T checkAndCast(TTLV ttlv, Class<T> expectedType) {
        if (expectedType.isAssignableFrom(ttlv.getClass())) {
            return expectedType.cast(ttlv);
        } else {
            throw new IllegalArgumentException("invalid type " + ttlv.getClass() + ", expected " + expectedType + " at " + ttlv.getPath());
        }
    }

}
