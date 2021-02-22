package com.tesco.crypt.kmip.examples;

import com.tesco.crypt.kmip.operation.model.ProtocolVersion;
import com.tesco.crypt.kmip.operation.model.attributes.*;
import com.tesco.crypt.kmip.operation.model.messages.RequestBatchItem;
import com.tesco.crypt.kmip.operation.model.messages.RequestHeader;
import com.tesco.crypt.kmip.operation.model.messages.ResponseBatchItem;
import com.tesco.crypt.kmip.operation.model.messages.ResponseHeader;
import com.tesco.crypt.kmip.operation.model.messages.activate.ActivateRequest;
import com.tesco.crypt.kmip.operation.model.messages.activate.ActivateResponse;
import com.tesco.crypt.kmip.operation.model.messages.create.CreateRequest;
import com.tesco.crypt.kmip.operation.model.messages.create.CreateResponse;
import com.tesco.crypt.kmip.operation.model.messages.destroy.DestroyRequest;
import com.tesco.crypt.kmip.operation.model.messages.destroy.DestroyResponse;
import com.tesco.crypt.kmip.operation.model.messages.discoverversions.DiscoverVersionsRequest;
import com.tesco.crypt.kmip.operation.model.messages.discoverversions.DiscoverVersionsResponse;
import com.tesco.crypt.kmip.operation.model.messages.get.GetRequest;
import com.tesco.crypt.kmip.operation.model.messages.get.GetResponse;
import com.tesco.crypt.kmip.operation.model.messages.getattributes.GetAttributesRequest;
import com.tesco.crypt.kmip.operation.model.messages.getattributes.GetAttributesResponse;
import com.tesco.crypt.kmip.operation.model.messages.locate.LocateRequest;
import com.tesco.crypt.kmip.operation.model.messages.locate.LocateResponse;
import com.tesco.crypt.kmip.operation.model.objects.SymmetricKey;
import com.tesco.crypt.kmip.operation.model.templates.SymmetricKeyTemplate;
import com.tesco.crypt.kmip.ttlv.model.Structure;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.MessageTag;
import com.tesco.crypt.kmip.ttlv.model.enums.bitmasks.CryptographicUsageMask;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.*;
import com.tesco.crypt.kmip.ttlv.model.values.EnumerationValue;
import com.tesco.crypt.kmip.ttlv.model.values.TextStringValue;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static com.tesco.crypt.kmip.ttlv.Hex.hexToBytes;

public class ExampleRealAndConformanceMessages {

    public static RequestBatchItem discoverVersionsRequest = (RequestBatchItem) new DiscoverVersionsRequest()
        .setHeader(
            new RequestHeader()
                .setBatchCount(1)
                .setVersion(
                    new ProtocolVersion()
                        .setMajor(1)
                        .setMinor(1)
                )
        );

    public static ResponseBatchItem discoverVersionResponse = (ResponseBatchItem) new DiscoverVersionsResponse()
        .setVersions(Arrays.asList(
            new ProtocolVersion()
                .setMajor(1)
                .setMinor(1),
            new ProtocolVersion()
                .setMajor(1)
                .setMinor(0)
        ))
        .setResultStatus(ResultStatus.SUCCESS)
        .setHeader(
            new ResponseHeader()
                .setBatchCount(1)
                .setTimeStamp(parseDate("Mon May 18 11:33:31 BST 2020"))
                .setVersion(
                    new ProtocolVersion()
                        .setMajor(1)
                        .setMinor(1)
                )
        );

    public static RequestBatchItem createSymmetricKeyRequest = (RequestBatchItem) new CreateRequest()
        .setObjectType(ObjectType.SYMMETRIC_KEY)
        .setTemplateAttributes(new TemplateAttributes().setAttributes(new Attributes().setAttributes(Arrays.asList(
            new EnumerationAttributeValue().setName("Cryptographic Algorithm").setValue(CryptographicAlgorithm.AES),
            new IntegerAttributeValue().setName("Cryptographic Length").setValue(128),
            new MaskAttributeValue().setName("Cryptographic Usage Mask").setValue(Arrays.asList(CryptographicUsageMask.ENCRYPT, CryptographicUsageMask.DECRYPT))
        ))))
        .setKmipTemplate(
            new SymmetricKeyTemplate()
                .setCryptographicAlgorithm(CryptographicAlgorithm.AES)
                .setCryptographicLength(128)
                .setCryptographicUsageMask(Arrays.asList(CryptographicUsageMask.ENCRYPT, CryptographicUsageMask.DECRYPT))
        )
        .setHeader(
            new RequestHeader()
                .setBatchCount(1)
                .setVersion(
                    new ProtocolVersion()
                        .setMajor(1)
                        .setMinor(1)
                )
        );

    public static ResponseBatchItem createSymmetricKeyResponse = (ResponseBatchItem) new CreateResponse()
        .setObjectType(ObjectType.SYMMETRIC_KEY)
        .setObjectId("49a1ca88-6bea-4fb2-b450-7e58802c3038")
        .setResultStatus(ResultStatus.SUCCESS)
        .setHeader(
            new ResponseHeader()
                .setBatchCount(1)
                .setTimeStamp(parseDate("Fri Apr 27 09:12:22 BST 2012"))
                .setVersion(
                    new ProtocolVersion()
                        .setMajor(1)
                        .setMinor(1)
                )
        );

    public static RequestBatchItem createSymmetricKeyWithDataRequest = (RequestBatchItem) new CreateRequest()
        .setObjectType(ObjectType.SYMMETRIC_KEY)
        .setTemplateAttributes(new TemplateAttributes().setAttributes(new Attributes().setAttributes(Arrays.asList(
            new EnumerationAttributeValue().setName("Cryptographic Algorithm").setValue(CryptographicAlgorithm.AES),
            new IntegerAttributeValue().setName("Cryptographic Length").setValue(256),
            new MaskAttributeValue().setName("Cryptographic Usage Mask").setValue(Arrays.asList(CryptographicUsageMask.ENCRYPT, CryptographicUsageMask.DECRYPT)),
            new NameAttributeValue().setName("Name").setValue("FirstTestName").setType(NameType.UNINTERPRETED_TEXT_STRING),
            new NameAttributeValue().setName("Name").setValue("SecondTestName").setType(NameType.UNINTERPRETED_TEXT_STRING),
            new TextStringAttributeValue().setName("Contact Information").setValue("admin@localhost")
        ))))
        .setKmipTemplate(
            new SymmetricKeyTemplate()
                .setCryptographicAlgorithm(CryptographicAlgorithm.AES)
                .setCryptographicLength(256)
                .setCryptographicUsageMask(Arrays.asList(CryptographicUsageMask.ENCRYPT, CryptographicUsageMask.DECRYPT))
        )
        .setHeader(
            new RequestHeader()
                .setBatchCount(1)
                .setVersion(
                    new ProtocolVersion()
                        .setMajor(1)
                        .setMinor(1)
                )
        );
    public static ResponseBatchItem createSymmetricKeyWithDataResponse = (ResponseBatchItem) new CreateResponse()
        .setObjectType(ObjectType.SYMMETRIC_KEY)
        .setObjectId("28c7bad1-bc9b-41df-b439-1ba04a6fd982")
        .setResultStatus(ResultStatus.SUCCESS)
        .setHeader(
            new ResponseHeader()
                .setBatchCount(1)
                .setTimeStamp(parseDate("Fri Apr 27 09:14:44 BST 2012"))
                .setVersion(
                    new ProtocolVersion()
                        .setMajor(1)
                        .setMinor(1)
                )
        );

    public static RequestBatchItem getAttributeRequest = (RequestBatchItem) new GetAttributesRequest()
        .setObjectId("1703250b-4d40-4de2-93a0-c494a1d4ae40")
        .setAttributeNames(Arrays.asList(
            "Object Group",
            "Application Specific Information",
            "Contact Information",
            "x-Purpose"
        ))
        .setHeader(
            new RequestHeader()
                .setBatchCount(1)
                .setVersion(
                    new ProtocolVersion()
                        .setMajor(1)
                        .setMinor(1)
                )
        );

    public static ResponseBatchItem getAttributesResponse = (ResponseBatchItem) new GetAttributesResponse()
        .setObjectId("1703250b-4d40-4de2-93a0-c494a1d4ae40")
        .setAttributes(new Attributes().setAttributes(Arrays.asList(
            new TextStringAttributeValue().setName("Object Group").setValue("Group1"),
            new NamespacedAttributeValue()
                .setName("Application Specific Information")
                .setNamespace("ssl")
                .setValue("www.example.com"),
            new TextStringAttributeValue().setName("Contact Information").setValue("Joe"),
            new TextStringAttributeValue().setName("x-Purpose").setValue("demonstration")
        )))
        .setResultStatus(ResultStatus.SUCCESS)
        .setHeader(
            new ResponseHeader()
                .setBatchCount(1)
                .setTimeStamp(parseDate("Fri Apr 27 09:12:22 BST 2012"))
                .setVersion(
                    new ProtocolVersion()
                        .setMajor(1)
                        .setMinor(1)
                )
        );
    public static RequestBatchItem getSymmetricKeyRequest = (RequestBatchItem) new GetRequest()
        .setObjectId("49a1ca88-6bea-4fb2-b450-7e58802c3038")
        .setHeader(
            new RequestHeader()
                .setBatchCount(1)
                .setVersion(
                    new ProtocolVersion()
                        .setMajor(1)
                        .setMinor(1)
                )
        );

    public static ResponseBatchItem getGetSymmetricKeyResponse = (ResponseBatchItem) new GetResponse()
        .setKmipObject(
            new SymmetricKey()
                .setKeyFormatType(KeyFormatType.RAW)
                .setKeyBytes(hexToBytes("7367578051012a6d134a855e25c8cd5e4ca131455729d3c8"))
                .setCryptographicAlgorithm(CryptographicAlgorithm.TRIPLE_DES)
                .setCryptographicLength(168)
                .setObjectId("49a1ca88-6bea-4fb2-b450-7e58802c3038")
        )
        .setResultStatus(ResultStatus.SUCCESS)
        .setHeader(
            new ResponseHeader()
                .setBatchCount(1)
                .setTimeStamp(parseDate("Fri Apr 27 09:12:23 BST 2012"))
                .setVersion(
                    new ProtocolVersion()
                        .setMajor(1)
                        .setMinor(1)
                )
        );

    public static ResponseBatchItem getGetNotFoundResponse = (ResponseBatchItem) new GetResponse()
        .setResultStatus(ResultStatus.OPERATION_FAILED)
        .setResultReason(ResultReason.ITEM_NOT_FOUND)
        .setResultMessage("Object with unique identifier '1da34abd-3fc4-1056-1975-cebdd909ea10' not found.")
        .setId("514c4b4301000000")
        .setHeader(
            new ResponseHeader()
                .setBatchCount(1)
                .setTimeStamp(parseDate("Mon Jun 15 09:41:45 BST 2020"))
                .setVersion(
                    new ProtocolVersion()
                        .setMajor(1)
                        .setMinor(1)
                )
        );




    public static RequestBatchItem activateRequest = (RequestBatchItem) new ActivateRequest()
        .setObjectId("28c7bad1-bc9b-41df-b439-1ba04a6fd982")
        .setHeader(
            new RequestHeader()
                .setBatchCount(1)
                .setVersion(
                    new ProtocolVersion()
                        .setMajor(1)
                        .setMinor(1)
                )
        );

    public static ResponseBatchItem activateResponse = (ResponseBatchItem) new ActivateResponse()
        .setObjectId("28c7bad1-bc9b-41df-b439-1ba04a6fd982")
        .setResultStatus(ResultStatus.SUCCESS)
        .setHeader(
            new ResponseHeader()
                .setBatchCount(1)
                .setTimeStamp(parseDate("Fri Apr 27 09:14:44 BST 2012"))
                .setVersion(
                    new ProtocolVersion()
                        .setMajor(1)
                        .setMinor(1)
                )
        );

    public static RequestBatchItem destroyRequest = (RequestBatchItem) new DestroyRequest()
        .setObjectId("28c7bad1-bc9b-41df-b439-1ba04a6fd982")
        .setHeader(
            new RequestHeader()
                .setBatchCount(1)
                .setVersion(
                    new ProtocolVersion()
                        .setMajor(1)
                        .setMinor(1)
                )
        );

    public static ResponseBatchItem destroyResponse = (ResponseBatchItem) new DestroyResponse()
        .setObjectId("28c7bad1-bc9b-41df-b439-1ba04a6fd982")
        .setResultStatus(ResultStatus.SUCCESS)
        .setHeader(
            new ResponseHeader()
                .setBatchCount(1)
                .setTimeStamp(parseDate("Fri Apr 27 09:14:44 BST 2012"))
                .setVersion(
                    new ProtocolVersion()
                        .setMajor(1)
                        .setMinor(1)
                )
        );

    public static RequestBatchItem locateRequest = (RequestBatchItem) new LocateRequest()
        .setAttributes(new Attributes().setAttributes(Arrays.asList(
            new EnumerationAttributeValue().setName("Object Type").setValue(ObjectType.SYMMETRIC_KEY),
            new NameAttributeValue().setName("Name").setType(NameType.UNINTERPRETED_TEXT_STRING).setValue("Key1")
        )))
        .setHeader(
            new RequestHeader()
                .setBatchCount(1)
                .setVersion(
                    new ProtocolVersion()
                        .setMajor(1)
                        .setMinor(1)
                )
        );

    public static ResponseBatchItem locateResponse = (ResponseBatchItem) new LocateResponse()
        .setObjectId("49a1ca88-6bea-4fb2-b450-7e58802c3038")
        .setResultStatus(ResultStatus.SUCCESS)
        .setHeader(
            new ResponseHeader()
                .setBatchCount(1)
                .setTimeStamp(parseDate("Fri Apr 27 09:12:22 BST 2012"))
                .setVersion(
                    new ProtocolVersion()
                        .setMajor(1)
                        .setMinor(1)
                )
        );

    @SneakyThrows
    private static Date parseDate(String s) {
        return new SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy").parse(s);
    }

}
