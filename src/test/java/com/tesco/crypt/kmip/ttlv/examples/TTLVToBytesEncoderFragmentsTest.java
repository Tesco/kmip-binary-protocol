package com.tesco.crypt.kmip.ttlv.examples;

import com.tesco.crypt.kmip.ttlv.TTLVToBytesEncoder;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.tesco.crypt.kmip.examples.ExampleSimpleTTLVs.*;
import static com.tesco.crypt.kmip.ttlv.Hex.bytesToHex;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Tag("UnitTest")
class TTLVToBytesEncoderFragmentsTest {

    @Test
    public void shouldEncodeIntegerValue() {
        shouldEncode(integerValue, integerValueHex);
    }

    @Test
    public void shouldEncodeLongIntegerValue() {
        shouldEncode(longIntegerValue, longIntegerValueHex);
    }

    @Test
    public void shouldEncodeBigIntegerValue() {
        shouldEncode(bigIntegerValue, bigIntegerValueHex);
    }

    @Test
    public void shouldEncodeBooleanValue() {
        shouldEncode(booleanValue, booleanValueHex);
    }

    @Test
    public void shouldEncodeStringValue() {
        shouldEncode(stringValue, stringValueHex);
    }

    @Test
    public void shouldEncodeStringValueWithoutPadding() {
        shouldEncode(stringValueWithoutPadding, stringValueWithoutPaddingHex);
    }

    @Test
    public void shouldEncodeByteStringValue() {
        shouldEncode(byteStringValue, byteStringValueHex);
    }

    @Test
    public void shouldEncodeByteStringValueWithoutPadding() {
        shouldEncode(byteStringValueWithoutPadding, byteStringValueWithoutPaddingHex);
    }

    @Test
    public void shouldEncodeDateTimeValue() {
        shouldEncode(dataTimeValue, dataTimeValueHex);
    }

    @Test
    public void shouldEncodeIntervalValue() {
        shouldEncode(intervalValue, intervalValueHex);
    }

    @Test
    public void shouldEncodeEnumerationValue() {
        shouldEncode(enumerationValue, enumerationValueHex);
    }

    @Test
    public void shouldEncodeEnumerationValueMatchingTag() {
        shouldEncode(enumerationValueMatchingTag, enumerationValueMatchingTagHex);
    }

    @Test
    public void shouldEncodeStructureWithTwoValues() {
        shouldEncode(structureEnumerationAndInteger, structureEnumerationAndIntegerHex);
    }

    @Test
    public void shouldEncodeMaskStructure() {
        shouldEncode(maskStructure, maskStructureHex);
    }

    @Test
    public void shouldEncodeAttributeEnumerationStructure() {
        shouldEncode(attributeValueEnumerationStructure, attributeValueEnumerationStructureHex);
    }

    private void shouldEncode(TTLV ttlv, String expected) {
        // when
        byte[] bytes = new TTLVToBytesEncoder().encode(ttlv);

        // then
        assertThat(bytesToHex(bytes), is(expected.toLowerCase()));
    }

}