package com.tesco.crypt.kmip.ttlv.examples;

import com.tesco.crypt.kmip.ttlv.TTLVToBytesEncoder;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.tesco.crypt.kmip.examples.ExampleRealAndConformanceTTLVs.*;
import static com.tesco.crypt.kmip.ttlv.Hex.bytesToHex;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Tag("UnitTest")
class TTLVToBytesEncoderCompleteMessagesTest {

    @Test
    public void shouldEncodeDiscoverVersionsRequest() {
        shouldEncode(discoverVersionsRequestTTLV, discoverVersionsRequestHex);
    }

    @Test
    public void shouldEncodeDiscoverVersionsResponse() {
        shouldEncode(discoverVersionResponseTTLV, discoverVersionResponseHex);
    }

    @Test
    public void shouldEncodeCreateSymmetricKeyRequest() {
        shouldEncode(createSymmetricKeyRequestTTLV, createSymmetricKeyRequestHex);
    }

    @Test
    public void shouldEncodeCreateSymmetricKeyResponse() {
        shouldEncode(createSymmetricKeyResponseTTLV, createSymmetricKeyResponseHex);
    }

    @Test
    public void shouldEncodeCreateSymmetricKeyWithDataRequest() {
        shouldEncode(createSymmetricKeyWithDataRequestTTLV, createSymmetricKeyWithDataRequestHex);
    }

    @Test
    public void shouldEncodeCreateSymmetricKeyWithDataResponse() {
        shouldEncode(createSymmetricKeyWithDataResponseTTLV, createSymmetricKeyWithDataResponseHex);
    }

    @Test
    public void shouldEncodeGetAttributesRequest() {
        shouldEncode(getAttributeRequestTTLV, getAttributeRequestHex);
    }

    @Test
    public void shouldEncodeGetAttributesResponse() {
        shouldEncode(getAttributesResponseTTLV, getAttributesResponseHex);
    }

    @Test
    public void shouldEncodeGetSymmetricKeyRequest() {
        shouldEncode(getSymmetricKeyRequestTTLV, getSymmetricKeyRequestHex);
    }

    @Test
    public void shouldEncodeGetSymmetricKeyResponse() {
        shouldEncode(getGetSymmetricKeyResponseTTLV, getGetSymmetricKeyResponseHex);
    }

    @Test
    public void shouldEncodeGetNotFoundResponse() {
        shouldEncode(getGetNotFoundResponseTTLV, getGetNotFoundResponseHex);
    }

    @Test
    public void shouldEncodeActivateRequest() {
        shouldEncode(activateRequestTTLV, activateRequestHex);
    }

    @Test
    public void shouldEncodeActivateResponse() {
        shouldEncode(activateResponseTTLV, activateResponseHex);
    }

    @Test
    public void shouldEncodeDestroyRequest() {
        shouldEncode(destroyRequestTTLV, destroyRequestHex);
    }

    @Test
    public void shouldEncodeDestroyResponse() {
        shouldEncode(destroyResponseTTLV, destroyResponseHex);
    }

    private void shouldEncode(TTLV ttlv, String expected) {
        // when
        byte[] bytes = new TTLVToBytesEncoder().encode(ttlv);

        // then
        assertThat(bytesToHex(bytes), is(expected.toLowerCase()));
    }
}