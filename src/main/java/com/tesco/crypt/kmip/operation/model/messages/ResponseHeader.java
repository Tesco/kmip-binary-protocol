package com.tesco.crypt.kmip.operation.model.messages;

import com.tesco.crypt.kmip.operation.model.ProtocolVersion;
import com.tesco.crypt.kmip.ttlv.model.Structure;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.values.DateTimeValue;
import com.tesco.crypt.kmip.ttlv.model.values.IntegerValue;
import lombok.Data;

import java.util.*;

import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.invalidMessageTag;
import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.*;

@Data
public class ResponseHeader implements Header {

    private Integer batchCount = 1;
    private ProtocolVersion version;
    private Date timeStamp = new Date();

    public ResponseHeader setVersion(ProtocolVersion version) {
        if (version == null || version.getMajor() == null || version.getMinor() == null) {
            throw new IllegalArgumentException("Version and it's major and minor value must all be non-null");
        }
        this.version = version;
        return this;
    }

    public ResponseHeader decode(List<TTLV> responseHeaderItems) {
        for (TTLV headerItem : responseHeaderItems) {
            switch (headerItem.getTag()) {
                case BATCH_COUNT:
                    if (headerItem instanceof IntegerValue) {
                        this.batchCount = ((IntegerValue) headerItem).getValue();
                    }
                    break;
                case TIME_STAMP:
                    if (headerItem instanceof DateTimeValue) {
                        this.timeStamp = ((DateTimeValue) headerItem).getValue();
                    }
                    break;
                case PROTOCOL_VERSION:
                    ProtocolVersion version = new ProtocolVersion();
                    for (TTLV protocolVersionItem : ((Structure) headerItem).getTtlvs()) {
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
                    if (version.getMajor() == null || version.getMinor() == null) {
                        throw new IllegalArgumentException("Version and it's major and minor value must all be non-null");
                    }
                    this.version = version;
                    break;
                default:
                    invalidMessageTag(headerItem, Arrays.asList(BATCH_COUNT, TIME_STAMP, PROTOCOL_VERSION));
            }
        }
        return this;
    }

    public TTLV encode() {
        if (this.version == null || this.version.getMajor() == null || this.version.getMinor() == null) {
            throw new IllegalArgumentException("Version must be set with a major and minor value");
        }
        return new Structure()
            .addTtlvs(
                new Structure()
                    .addTtlvs(
                        new IntegerValue()
                            .setValue(this.version.getMajor())
                            .setTag(PROTOCOL_VERSION_MAJOR),
                        new IntegerValue()
                            .setValue(this.version.getMinor())
                            .setTag(PROTOCOL_VERSION_MINOR)
                    )
                    .setTag(PROTOCOL_VERSION),
                new DateTimeValue()
                    .setValue(this.timeStamp)
                    .setTag(TIME_STAMP),
                new IntegerValue()
                    .setValue(this.batchCount)
                    .setTag(BATCH_COUNT)
            )
            .setTag(RESPONSE_HEADER);
    }
}
