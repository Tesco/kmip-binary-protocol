package com.tesco.crypt.kmip.operation.model.messages;

import com.tesco.crypt.kmip.ttlv.model.TTLV;

import java.util.Date;

public interface Header {

    Integer getBatchCount();

    Header setBatchCount(Integer batchCount);

    Date getTimeStamp();

    TTLV encode();

}
