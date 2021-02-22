package com.tesco.crypt.kmip.operation.examples;

import com.tesco.crypt.kmip.examples.ExampleRealAndConformanceMessages;
import com.tesco.crypt.kmip.examples.ExampleRealAndConformanceTTLVs;
import com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder;
import com.tesco.crypt.kmip.operation.model.messages.BatchItem;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.tesco.crypt.kmip.examples.ExampleRealAndConformanceMessages.*;
import static com.tesco.crypt.kmip.examples.ExampleRealAndConformanceTTLVs.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Tag("UnitTest")
class TTLVToBatchItemsDecoderCompleteMessagesTest {

    @Test
    public void shouldDecodeDiscoverVersionsRequest() {
        shouldDecode(discoverVersionsRequestTTLV, discoverVersionsRequest);
    }

    @Test
    public void shouldDecodeDiscoverVersionsResponse() {
        shouldDecode(discoverVersionResponseTTLV, discoverVersionResponse);
    }

    @Test
    public void shouldDecodeCreateSymmetricKeyRequest() {
        shouldDecode(createSymmetricKeyRequestTTLV, createSymmetricKeyRequest);
    }

    @Test
    public void shouldDecodeCreateSymmetricKeyResponse() {
        shouldDecode(createSymmetricKeyResponseTTLV, createSymmetricKeyResponse);
    }

    @Test
    public void shouldDecodeCreateSymmetricKeyWithDataRequest() {
        shouldDecode(createSymmetricKeyWithDataRequestTTLV, createSymmetricKeyWithDataRequest);
    }

    @Test
    public void shouldDecodeCreateSymmetricKeyWithDataResponse() {
        shouldDecode(createSymmetricKeyWithDataResponseTTLV, createSymmetricKeyWithDataResponse);
    }

    @Test
    public void shouldDecodeGetAttributeRequest() {
        shouldDecode(getAttributeRequestTTLV, getAttributeRequest);
    }

    @Test
    public void shouldDecodeGetAttributeResponse() {
        shouldDecode(getAttributesResponseTTLV, getAttributesResponse);
    }

    @Test
    public void shouldDecodeGetRequest() {
        shouldDecode(getSymmetricKeyRequestTTLV, getSymmetricKeyRequest);
    }

    @Test
    public void shouldDecodeGetSymmetricKeyResponse() {
        shouldDecode(getGetSymmetricKeyResponseTTLV, getGetSymmetricKeyResponse);
    }

    @Test
    public void shouldDecodeGeNotFoundResponse() {
        shouldDecode(getGetNotFoundResponseTTLV, getGetNotFoundResponse);
    }

    @Test
    public void shouldEncodeActivateRequest() {
        shouldDecode(activateRequestTTLV, activateRequest);
    }

    @Test
    public void shouldEncodeActivateResponse() {
        shouldDecode(activateResponseTTLV, activateResponse);
    }

    @Test
    public void shouldDecodeDestroyRequest() {
        shouldDecode(destroyRequestTTLV, destroyRequest);
    }

    @Test
    public void shouldDecodeDestroyResponse() {
        shouldDecode(destroyResponseTTLV, destroyResponse);
    }

    @Test
    public void shouldDecodeLocateRequest() {
        shouldDecode(locateRequestTTLV, locateRequest);
    }

    @Test
    public void shouldDecodeLocateResponse() {
        shouldDecode(locateResponseTTLV, locateResponse);
    }

    private void shouldDecode(TTLV ttlv, BatchItem expected) {
        // when
        List<BatchItem> batchItems = new ArrayList<>();
        new TTLVToBatchItemsDecoder<>().decode(ttlv, batchItems::add);

        // then
        assertThat(batchItems.size(), is(1));
        assertThat(batchItems.get(0), is(expected));
    }
}