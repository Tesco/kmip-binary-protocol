package com.tesco.crypt.kmip.operation.model.messages;

import com.tesco.crypt.kmip.operation.model.ProtocolVersion;
import com.tesco.crypt.kmip.ttlv.model.Structure;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.MessageTag;
import com.tesco.crypt.kmip.ttlv.model.values.BooleanValue;
import com.tesco.crypt.kmip.ttlv.model.values.DateTimeValue;
import com.tesco.crypt.kmip.ttlv.model.values.IntegerValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.checkAndCast;
import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.invalidMessageTag;
import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.*;

@Data
public class RequestHeader implements Header {

    private Integer batchCount;
    private ProtocolVersion version;
    private Date timeStamp;
    private Integer maximumResponseSize;
    private Boolean asynchronousSupported;
    private Boolean batchOrderImportant;

    public RequestHeader setVersion(ProtocolVersion version) {
        if (version == null || version.getMajor() == null || version.getMinor() == null) {
            throw new IllegalArgumentException("Version and it's major and minor value must all be non-null");
        }
        this.version = version;
        return this;
    }

    public RequestHeader decode(List<TTLV> requestHeaderItems) {
        for (TTLV headerItem : requestHeaderItems) {
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
                case MAXIMUM_RESPONSE_SIZE:
                    this.maximumResponseSize = checkAndCast(headerItem, IntegerValue.class).getValue();
                    break;
                case ASYNCHRONOUS_INDICATOR:
                    this.asynchronousSupported = checkAndCast(headerItem, BooleanValue.class).getValue();
                    break;
                case BATCH_ORDER_OPTION:
                    this.batchOrderImportant = checkAndCast(headerItem, BooleanValue.class).getValue();
                    break;
                default:
                    invalidMessageTag(headerItem, Arrays.asList(BATCH_COUNT, MAXIMUM_RESPONSE_SIZE, PROTOCOL_VERSION));
            }
        }
        return this;
    }

    public TTLV encode() {
        if (this.version == null || this.version.getMajor() == null || this.version.getMinor() == null) {
            throw new IllegalArgumentException("Version must be set with a major and minor value");
        }
        Structure headerStructure = (Structure) new Structure()
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
                new IntegerValue()
                    .setValue(this.batchCount != null ? this.batchCount : 1)
                    .setTag(BATCH_COUNT)
            )
            .setTag(REQUEST_HEADER);
        if (timeStamp != null) {
            headerStructure.addTtlvs(
                new DateTimeValue()
                    .setValue(timeStamp)
                    .setTag(TIME_STAMP)
            );
        }
        if (maximumResponseSize != null) {
            headerStructure.addTtlvs(
                new IntegerValue()
                    .setValue(maximumResponseSize)
                    .setTag(MessageTag.MAXIMUM_RESPONSE_SIZE)
            );
        }
        if (asynchronousSupported != null) {
            headerStructure.addTtlvs(
                new BooleanValue()
                    .setValue(asynchronousSupported)
                    .setTag(MessageTag.ASYNCHRONOUS_INDICATOR)
            );
        }
        if (batchOrderImportant != null) {
            headerStructure.addTtlvs(
                new BooleanValue()
                    .setValue(batchOrderImportant)
                    .setTag(MessageTag.BATCH_ORDER_OPTION)
            );
        }
        return headerStructure;
    }
}
