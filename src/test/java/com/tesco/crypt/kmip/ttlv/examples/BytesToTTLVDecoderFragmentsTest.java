package com.tesco.crypt.kmip.ttlv.examples;

import com.tesco.crypt.kmip.ttlv.BytesToTTLVDecoder;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import static com.tesco.crypt.kmip.examples.ExampleSimpleTTLVs.*;
import static com.tesco.crypt.kmip.ttlv.Hex.hexToBytes;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Tag("UnitTest")
class BytesToTTLVDecoderFragmentsTest {

    @Test
    public void shouldDecodeIntegerValue() {
        shouldDecode(integerValueHex, integerValue);
    }

    @Test
    public void shouldDecodeLongIntegerValue() {
        shouldDecode(longIntegerValueHex, longIntegerValue);
    }

    @Test
    public void shouldDecodeBigIntegerValue() {
        shouldDecode(bigIntegerValueHex, bigIntegerValue);
    }

    @Test
    public void shouldDecodeBooleanValue() {
        shouldDecode(booleanValueHex, booleanValue);
    }

    @Test
    public void shouldDecodeStringValue() {
        shouldDecode(stringValueHex, stringValue);
    }

    @Test
    public void shouldDecodeStringValueWithoutPadding() {
        shouldDecode(stringValueWithoutPaddingHex, stringValueWithoutPadding);
    }

    @Test
    public void shouldDecodeByteStringValue() {
        shouldDecode(byteStringValueHex, byteStringValue);
    }

    @Test
    public void shouldDecodeByteStringValueWithoutPadding() {
        shouldDecode(byteStringValueWithoutPaddingHex, byteStringValueWithoutPadding);
    }

    @Test
    public void shouldDecodeDateTimeValue() {
        shouldDecode(dataTimeValueHex, dataTimeValue);
    }

    @Test
    public void shouldDecodeIntervalValue() {
        shouldDecode(intervalValueHex, intervalValue);
    }

    @Test
    public void shouldDecodeEnumerationValue() {
        shouldDecode(enumerationValueHex, enumerationValue);
    }

    @Test
    public void shouldDecodeEnumerationValueMatchingTag() {
        shouldDecode(enumerationValueMatchingTagHex, enumerationValueMatchingTag);
    }

    @Test
    public void shouldDecodeStructureWithTwoValues() {
        shouldDecode(structureEnumerationAndIntegerHex, structureEnumerationAndInteger);
    }

    @Test
    public void shouldDecodeMaskStructure() {
        shouldDecode(maskStructureHex, maskStructure);
    }

    @Test
    public void shouldDecodeAttributeEnumerationStructure() {
        shouldDecode(attributeValueEnumerationStructureHex, attributeValueEnumerationStructure);
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