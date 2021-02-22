package com.tesco.crypt.kmip.ttlv.examples;

import com.tesco.crypt.kmip.ttlv.BytesToTTLVDecoder;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static com.tesco.crypt.kmip.examples.ExampleRealAndConformanceTTLVs.*;
import static com.tesco.crypt.kmip.ttlv.Hex.hexToBytes;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Tag("UnitTest")
class BytesToTTLVDecoderCompleteMessagesTest {

    @Test
    public void shouldDecodeDiscoverVersionsRequest() {
        shouldDecode(discoverVersionsRequestHex, discoverVersionsRequestTTLV);
    }

    @Test
    public void shouldDecodeDiscoverVersionsResponse() {
        shouldDecode(discoverVersionResponseHex, discoverVersionResponseTTLV);
    }

    @Test
    public void shouldDecodeCreateSymmetricKeyRequest() {
        shouldDecode(createSymmetricKeyRequestHex, createSymmetricKeyRequestTTLV);
    }

    @Test
    public void shouldDecodeCreateSymmetricKeyResponse() {
        shouldDecode(createSymmetricKeyResponseHex, createSymmetricKeyResponseTTLV);
    }

    @Test
    public void shouldDecodeCreateSymmetricKeyWithDataRequest() {
        shouldDecode(createSymmetricKeyWithDataRequestHex, createSymmetricKeyWithDataRequestTTLV);
    }

    @Test
    public void shouldDecodeCreateSymmetricKeyWithDataResponse() {
        shouldDecode(createSymmetricKeyWithDataResponseHex, createSymmetricKeyWithDataResponseTTLV);
    }

    @Test
    public void shouldDecodeGetAttributesRequest() {
        shouldDecode(getAttributeRequestHex, getAttributeRequestTTLV);
    }

    @Test
    public void shouldDecodeGetAttributesResponse() {
        shouldDecode(getAttributesResponseHex, getAttributesResponseTTLV);
    }

    @Test
    public void shouldDecodeGetSymmetricKeyRequest() {
        shouldDecode(getSymmetricKeyRequestHex, getSymmetricKeyRequestTTLV);
    }

    @Test
    public void shouldDecodeGetSymmetricKeyResponse() {
        shouldDecode(getGetSymmetricKeyResponseHex, getGetSymmetricKeyResponseTTLV);
    }

    @Test
    public void shouldDecodeGetNotFoundResponse() {
        shouldDecode(getGetNotFoundResponseHex, getGetNotFoundResponseTTLV);
    }

    @Test
    public void shouldDecodeActivateRequest() {
        shouldDecode(activateRequestHex, activateRequestTTLV);
    }

    @Test
    public void shouldDecodeActivateResponse() {
        shouldDecode(activateResponseHex, activateResponseTTLV);
    }

    @Test
    public void shouldDecodeDestroyRequest() {
        shouldDecode(destroyRequestHex, destroyRequestTTLV);
    }

    @Test
    public void shouldDecodeDestroyResponse() {
        shouldDecode(destroyResponseHex, destroyResponseTTLV);
    }

    @Test
    public void shouldDecodeLocateRequest() {
        shouldDecode(locateRequestHex, locateRequestTTLV);
    }

    @Test
    public void shouldDecodeLocateResponse() {
        shouldDecode(locateResponseHex, locateResponseTTLV);
    }

    private void shouldDecode(String hex, TTLV expected) {
        // when
        List<TTLV> ttlvs = new ArrayList<>();
        new BytesToTTLVDecoder().decode(new ByteArrayInputStream(hexToBytes(hex)), ttlvs::add);

        // then
        assertThat(ttlvs.size(), is(1));
        assertThat(ttlvs.get(0), is(expected));
    }

}