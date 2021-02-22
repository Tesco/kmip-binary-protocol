package com.tesco.crypt.kmip.operation.examples;

import com.tesco.crypt.kmip.operation.BatchItemsToTTLVEncoder;
import com.tesco.crypt.kmip.operation.model.messages.BatchItem;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.tesco.crypt.kmip.examples.ExampleRealAndConformanceMessages.*;
import static com.tesco.crypt.kmip.examples.ExampleRealAndConformanceTTLVs.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Tag("UnitTest")
class BatchItemsToTTLVEncoderCompleteMessagesTest {

    @Test
    public void shouldEncodeDiscoverVersionsRequest() {
        shouldEncode(discoverVersionsRequest, discoverVersionsRequestTTLV);
    }

    @Test
    public void shouldEncodeDiscoverVersionsResponse() {
        shouldEncode(discoverVersionResponse, discoverVersionResponseTTLV);
    }

    @Test
    public void shouldEncodeCreateSymmetricKeyRequest() {
        shouldEncode(createSymmetricKeyRequest, createSymmetricKeyRequestTTLV);
    }

    @Test
    public void shouldEncodeCreateSymmetricKeyResponse() {
        shouldEncode(createSymmetricKeyResponse, createSymmetricKeyResponseTTLV);
    }


    @Test
    public void shouldEncodeCreateSymmetricKeyWithDataRequest() {
        shouldEncode(createSymmetricKeyWithDataRequest, createSymmetricKeyWithDataRequestTTLV);
    }

    @Test
    public void shouldEncodeCreateSymmetricKeyWithDataResponse() {
        shouldEncode(createSymmetricKeyWithDataResponse, createSymmetricKeyWithDataResponseTTLV);
    }

    @Test
    public void shouldEncodeGetAttributeRequest() {
        shouldEncode(getAttributeRequest, getAttributeRequestTTLV);
    }

    @Test
    public void shouldEncodeGetAttributeResponse() {
        shouldEncode(getAttributesResponse, getAttributesResponseTTLV);
    }

    @Test
    public void shouldEncodeGetRequest() {
        shouldEncode(getSymmetricKeyRequest, getSymmetricKeyRequestTTLV);
    }

    @Test
    public void shouldEncodeGetSymmetricKeyResponse() {
        shouldEncode(getGetSymmetricKeyResponse, getGetSymmetricKeyResponseTTLV);
    }

    @Test
    public void shouldEncodeGetNotFoundResponse() {
        shouldEncode(getGetNotFoundResponse, getGetNotFoundResponseTTLV);
    }

    @Test
    public void shouldEncodeActivateRequest() {
        shouldEncode(activateRequest, activateRequestTTLV);
    }

    @Test
    public void shouldEncodeActivateResponse() {
        shouldEncode(activateResponse, activateResponseTTLV);
    }

    @Test
    public void shouldEncodeDestroyRequest() {
        shouldEncode(destroyRequest, destroyRequestTTLV);
    }

    @Test
    public void shouldEncodeDestroyResponse() {
        shouldEncode(destroyResponse, destroyResponseTTLV);
    }

    @Test
    public void shouldEncodeLocateRequest() {
        shouldEncode(locateRequest, locateRequestTTLV);
    }

    @Test
    public void shouldEncodeLocateResponse() {
        shouldEncode(locateResponse, locateResponseTTLV);
    }

    private void shouldEncode(BatchItem batchItem, TTLV expected) {
        // when
        TTLV actual = new BatchItemsToTTLVEncoder().encode(List.of(batchItem));

        // then
        assertThat(expected, is(actual));
    }

}