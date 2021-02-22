package com.tesco.crypt.kmip.conformancetests;

import com.tesco.crypt.kmip.conformancetests.parser.TestCase;
import com.tesco.crypt.kmip.conformancetests.parser.TestCaseParser;
import com.tesco.crypt.kmip.ttlv.BytesToTTLVDecoder;
import com.tesco.crypt.kmip.ttlv.Hex;
import com.tesco.crypt.kmip.ttlv.TTLVToBytesEncoder;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.tesco.crypt.kmip.ttlv.Hex.hexToBytes;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@Tag("UnitTest")
public class ConformanceV11Test {

    private static Map<String, TestCase> conformanceTestCases;

    @BeforeAll
    public static void setUp() {
        conformanceTestCases = TestCaseParser.loadConformanceTest("/com/tesco/crypt/kmip/conformancetests/kmip-testcases-v1.1.txt");
    }

    // TODO try using parameterised test with method source

    @Test
    public void shouldDecodeTTLVsFromBytes() {
        List<TestResult> testResults = new ArrayList<>();
        conformanceTestCases.forEach((testName, testCase) -> new BytesToTTLVDecoder().decode(new ByteArrayInputStream(hexToBytes(testCase.hex)), ttlv -> testResults.add(new TestResult(testCase, ttlv))));

        for (TestResult testResult : testResults) {
            assertThat(testResult.testCase.name, testResult.actual, is(testResult.testCase.ttlv));
        }
    }

    @Test
    public void shouldEncodeTTLVsToBytes() {
        List<TestResult> testResults = new ArrayList<>();
        conformanceTestCases.forEach((testName, testCase) -> testResults.add(new TestResult(testCase, new TTLVToBytesEncoder().encode(testCase.ttlv))));

        for (TestResult testResult : testResults) {
            assertThat(testResult.testCase.name, Hex.bytesToHex((byte[]) testResult.actual).toUpperCase(), is(testResult.testCase.hex));
        }
    }

    @Data
    @AllArgsConstructor
    public static class TestResult {
        TestCase testCase;
        Object actual;
    }
}
