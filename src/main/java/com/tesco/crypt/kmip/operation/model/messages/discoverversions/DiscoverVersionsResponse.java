package com.tesco.crypt.kmip.operation.model.messages.discoverversions;

import com.tesco.crypt.kmip.operation.model.ProtocolVersion;
import com.tesco.crypt.kmip.operation.model.messages.ResponseBatchItem;
import com.tesco.crypt.kmip.ttlv.model.Structure;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.MessageTag;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.Operation;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.ResultStatus;
import com.tesco.crypt.kmip.ttlv.model.values.IntegerValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.invalidMessageTag;
import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.*;

@Data
public class DiscoverVersionsResponse extends ResponseBatchItem {

    private List<ProtocolVersion> versions;

    public DiscoverVersionsResponse() {
        setResultStatus(ResultStatus.SUCCESS);
    }

    @Override
    public Operation getOperation() {
        return Operation.DISCOVER_VERSIONS;
    }

    @Override
    public void decode(List<TTLV> payloadItems) {
        versions = new ArrayList<>();
        for (TTLV payloadItem : payloadItems) {
            if (payloadItem.getTag() == PROTOCOL_VERSION) {
                ProtocolVersion version = new ProtocolVersion();
                for (TTLV protocolVersionItem : ((Structure) payloadItem).getTtlvs()) {
                    switch (protocolVersionItem.getTag()) {
                        case PROTOCOL_VERSION_MAJOR:
                            if (protocolVersionItem instanceof IntegerValue) {
                                version.setMajor(((IntegerValue) protocolVersionItem).getValue());
                            }
                            break;
                        case PROTOCOL_VERSION_MINOR:
                            if (protocolVersionItem instanceof IntegerValue) {
                                version.setMinor(((IntegerValue) protocolVersionItem).getValue());
                            }
                            break;
                        default:
                            invalidMessageTag(protocolVersionItem, Arrays.asList(PROTOCOL_VERSION_MAJOR, PROTOCOL_VERSION_MINOR));
                    }
                }
                versions.add(version);
            } else {
                invalidMessageTag(payloadItem, Collections.singletonList(PROTOCOL_VERSION));
            }
        }
    }

    @Override
    public List<TTLV> encode() {
        List<TTLV> payload = new ArrayList<>();
        for (ProtocolVersion version : versions) {
            payload.add(
                new Structure()
                    .addTtlvs(
                        new IntegerValue()
                            .setValue(version.getMajor())
                            .setTag(MessageTag.PROTOCOL_VERSION_MAJOR),
                        new IntegerValue()
                            .setValue(version.getMinor())
                            .setTag(MessageTag.PROTOCOL_VERSION_MINOR)
                    )
                    .setTag(MessageTag.PROTOCOL_VERSION)
            );
        }
        return getOperationResponse(payload, getOperation());
    }

}
