package com.tesco.crypt.kmip.ttlv.model.enums;

import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.*;

import java.math.BigInteger;

public enum MessageTag {
    UNUSED_A("000000", "420000"),
    ACTIVATION_DATE("420001", (Class<ByteEnum<?>>) null),
    APPLICATION_DATA("420002", (Class<ByteEnum<?>>) null),
    APPLICATION_NAMESPACE("420003", (Class<ByteEnum<?>>) null),
    APPLICATION_SPECIFIC_INFORMATION("420004", (Class<ByteEnum<?>>) null),
    ARCHIVE_DATE("420005", (Class<ByteEnum<?>>) null),
    ASYNCHRONOUS_CORRELATION_VALUE("420006", (Class<ByteEnum<?>>) null),
    ASYNCHRONOUS_INDICATOR("420007", (Class<ByteEnum<?>>) null),
    ATTRIBUTE("420008", (Class<ByteEnum<?>>) null),
    ATTRIBUTE_INDEX("420009", (Class<ByteEnum<?>>) null),
    ATTRIBUTE_NAME("42000A", (Class<ByteEnum<?>>) null),
    ATTRIBUTE_VALUE("42000B", (Class<ByteEnum<?>>) null),
    AUTHENTICATION("42000C", (Class<ByteEnum<?>>) null),
    BATCH_COUNT("42000D", (Class<ByteEnum<?>>) null),
    BATCH_ERROR_CONTINUATION_OPTION("42000E", BatchErrorContinuation.class),
    BATCH_ITEM("42000F", (Class<ByteEnum<?>>) null),
    BATCH_ORDER_OPTION("420010", (Class<ByteEnum<?>>) null),
    BLOCK_CIPHER_MODE("420011", BlockCipherMode.class),
    CANCELLATION_RESULT("420012", CancellationResult.class),
    CERTIFICATE("420013", (Class<ByteEnum<?>>) null),
    CERTIFICATE_IDENTIFIER_DEPRECATED_1_1("420014", (Class<ByteEnum<?>>) null),
    CERTIFICATE_ISSUER_DEPRECATED_1_1("420015", (Class<ByteEnum<?>>) null),
    CERTIFICATE_ISSUER_ALTERNATIVE_NAME_DEPRECATED_1_1("420016", (Class<ByteEnum<?>>) null),
    CERTIFICATE_ISSUER_DISTINGUISHED_NAME_DEPRECATED_1_1("420017", (Class<ByteEnum<?>>) null),
    CERTIFICATE_REQUEST("420018", (Class<ByteEnum<?>>) null),
    CERTIFICATE_REQUEST_TYPE("420019", CertificateRequestType.class),
    CERTIFICATE_SUBJECT_DEPRECATED_1_1("42001A", (Class<ByteEnum<?>>) null),
    CERTIFICATE_SUBJECT_ALTERNATIVE_NAME_DEPRECATED_1_1("42001B", (Class<ByteEnum<?>>) null),
    CERTIFICATE_SUBJECT_DISTINGUISHED_NAME_DEPRECATED_1_1("42001C", (Class<ByteEnum<?>>) null),
    CERTIFICATE_TYPE("42001D", CertificateType.class),
    CERTIFICATE_VALUE("42001E", (Class<ByteEnum<?>>) null),
    COMMON_TEMPLATE_ATTRIBUTE("42001F", (Class<ByteEnum<?>>) null),
    COMPROMISE_DATE("420020", (Class<ByteEnum<?>>) null),
    COMPROMISE_OCCURRENCE_DATE("420021", (Class<ByteEnum<?>>) null),
    CONTACT_INFORMATION("420022", (Class<ByteEnum<?>>) null),
    CREDENTIAL("420023", (Class<ByteEnum<?>>) null),
    CREDENTIAL_TYPE("420024", CredentialType.class),
    CREDENTIAL_VALUE("420025", (Class<ByteEnum<?>>) null),
    CRITICALITY_INDICATOR("420026", (Class<ByteEnum<?>>) null),
    CRT_COEFFICIENT("420027", (Class<ByteEnum<?>>) null),
    CRYPTOGRAPHIC_ALGORITHM("420028", CryptographicAlgorithm.class),
    CRYPTOGRAPHIC_DOMAIN_PARAMETERS("420029", (Class<ByteEnum<?>>) null),
    CRYPTOGRAPHIC_LENGTH("42002A", (Class<ByteEnum<?>>) null),
    CRYPTOGRAPHIC_PARAMETERS("42002B", (Class<ByteEnum<?>>) null),
    CRYPTOGRAPHIC_USAGE_MASK("42002C", (Class<ByteEnum<?>>) null),
    CUSTOM_ATTRIBUTE("42002D", (Class<ByteEnum<?>>) null),
    D("42002E", (Class<ByteEnum<?>>) null),
    DEACTIVATION_DATE("42002F", (Class<ByteEnum<?>>) null),
    DERIVATION_DATA("420030", (Class<ByteEnum<?>>) null),
    DERIVATION_METHOD("420031", DerivationMethod.class),
    DERIVATION_PARAMETERS("420032", (Class<ByteEnum<?>>) null),
    DESTROY_DATE("420033", (Class<ByteEnum<?>>) null),
    DIGEST("420034", (Class<ByteEnum<?>>) null),
    DIGEST_VALUE("420035", (Class<ByteEnum<?>>) null),
    ENCRYPTION_KEY_INFORMATION("420036", (Class<ByteEnum<?>>) null),
    G("420037", (Class<ByteEnum<?>>) null),
    HASHING_ALGORITHM("420038", HashingAlgorithm.class),
    INITIAL_DATE("420039", (Class<ByteEnum<?>>) null),
    INITIALIZATION_VECTOR("42003A", (Class<ByteEnum<?>>) null),
    ISSUER_DEPRECATED_1_1("42003B", (Class<ByteEnum<?>>) null),
    ITERATION_COUNT("42003C", (Class<ByteEnum<?>>) null),
    IV_COUNTER_NONCE("42003D", (Class<ByteEnum<?>>) null),
    J("42003E", (Class<ByteEnum<?>>) null),
    KEY("42003F", (Class<ByteEnum<?>>) null),
    KEY_BLOCK("420040", (Class<ByteEnum<?>>) null),
    KEY_COMPRESSION_TYPE("420041", KeyCompressionType.class),
    KEY_FORMAT_TYPE("420042", KeyFormatType.class),
    KEY_MATERIAL("420043", (Class<ByteEnum<?>>) null),
    KEY_PART_IDENTIFIER("420044", (Class<ByteEnum<?>>) null),
    KEY_VALUE("420045", (Class<ByteEnum<?>>) null),
    KEY_WRAPPING_DATA("420046", (Class<ByteEnum<?>>) null),
    KEY_WRAPPING_SPECIFICATION("420047", (Class<ByteEnum<?>>) null),
    LAST_CHANGE_DATE("420048", (Class<ByteEnum<?>>) null),
    LEASE_TIME("420049", (Class<ByteEnum<?>>) null),
    LINK("42004A", (Class<ByteEnum<?>>) null),
    LINK_TYPE("42004B", LinkType.class),
    LINKED_OBJECT_IDENTIFIER("42004C", (Class<ByteEnum<?>>) null),
    MAC_SIGNATURE("42004D", (Class<ByteEnum<?>>) null),
    MAC_SIGNATURE_KEY_INFORMATION("42004E", (Class<ByteEnum<?>>) null),
    MAXIMUM_ITEMS("42004F", (Class<ByteEnum<?>>) null),
    MAXIMUM_RESPONSE_SIZE("420050", (Class<ByteEnum<?>>) null),
    MESSAGE_EXTENSION("420051", (Class<ByteEnum<?>>) null),
    MODULUS("420052", (Class<ByteEnum<?>>) null),
    NAME("420053", (Class<ByteEnum<?>>) null),
    NAME_TYPE("420054", NameType.class),
    NAME_VALUE("420055", (Class<ByteEnum<?>>) null),
    OBJECT_GROUP("420056", ObjectGroupMemberOption.class),
    OBJECT_TYPE("420057", ObjectType.class),
    OFFSET("420058", (Class<ByteEnum<?>>) null),
    OPAQUE_DATA_TYPE("420059", OpaqueDataType.class),
    OPAQUE_DATA_VALUE("42005A", (Class<ByteEnum<?>>) null),
    OPAQUE_OBJECT("42005B", (Class<ByteEnum<?>>) null),
    OPERATION("42005C", Operation.class),
    OPERATION_POLICY_NAME("42005D", (Class<ByteEnum<?>>) null),
    P("42005E", (Class<ByteEnum<?>>) null),
    PADDING_METHOD("42005F", PaddingMethod.class),
    PRIME_EXPONENT_P("420060", (Class<ByteEnum<?>>) null),
    PRIME_EXPONENT_Q("420061", (Class<ByteEnum<?>>) null),
    PRIME_FIELD_SIZE("420062", (Class<ByteEnum<?>>) null),
    PRIVATE_EXPONENT("420063", (Class<ByteEnum<?>>) null),
    PRIVATE_KEY("420064", (Class<ByteEnum<?>>) null),
    PRIVATE_KEY_TEMPLATE_ATTRIBUTE("420065", (Class<ByteEnum<?>>) null),
    PRIVATE_KEY_UNIQUE_IDENTIFIER("420066", (Class<ByteEnum<?>>) null),
    PROCESS_START_DATE("420067", (Class<ByteEnum<?>>) null),
    PROTECT_STOP_DATE("420068", (Class<ByteEnum<?>>) null),
    PROTOCOL_VERSION("420069", (Class<ByteEnum<?>>) null),
    PROTOCOL_VERSION_MAJOR("42006A", (Class<ByteEnum<?>>) null),
    PROTOCOL_VERSION_MINOR("42006B", (Class<ByteEnum<?>>) null),
    PUBLIC_EXPONENT("42006C", (Class<ByteEnum<?>>) null),
    PUBLIC_KEY("42006D", (Class<ByteEnum<?>>) null),
    PUBLIC_KEY_TEMPLATE_ATTRIBUTE("42006E", (Class<ByteEnum<?>>) null),
    PUBLIC_KEY_UNIQUE_IDENTIFIER("42006F", (Class<ByteEnum<?>>) null),
    PUT_FUNCTION("420070", PutFunction.class),
    Q("420071", (Class<ByteEnum<?>>) null),
    Q_STRING("420072", (Class<ByteEnum<?>>) null),
    QLENGTH("420073", (Class<ByteEnum<?>>) null),
    QUERY_FUNCTION("420074", QueryFunction.class),
    RECOMMENDED_CURVE("420075", RecommendedCurveEnumeration.class),
    REPLACED_UNIQUE_IDENTIFIER("420076", (Class<ByteEnum<?>>) null),
    REQUEST_HEADER("420077", (Class<ByteEnum<?>>) null),
    REQUEST_MESSAGE("420078", (Class<ByteEnum<?>>) null),
    REQUEST_PAYLOAD("420079", (Class<ByteEnum<?>>) null),
    RESPONSE_HEADER("42007A", (Class<ByteEnum<?>>) null),
    RESPONSE_MESSAGE("42007B", (Class<ByteEnum<?>>) null),
    RESPONSE_PAYLOAD("42007C", (Class<ByteEnum<?>>) null),
    RESULT_MESSAGE("42007D", (Class<ByteEnum<?>>) null),
    RESULT_REASON("42007E", ResultReason.class),
    RESULT_STATUS("42007F", ResultStatus.class),
    REVOCATION_MESSAGE("420080", (Class<ByteEnum<?>>) null),
    REVOCATION_REASON("420081", (Class<ByteEnum<?>>) null),
    REVOCATION_REASON_CODE("420082", RevocationReasonCode.class),
    KEY_ROLE_TYPE("420083", KeyRoleType.class),
    SALT("420084", (Class<ByteEnum<?>>) null),
    SECRET_DATA("420085", (Class<ByteEnum<?>>) null),
    SECRET_DATA_TYPE("420086", SecretDataType.class),
    SERIAL_NUMBER_DEPRECATED_1_1("420087", (Class<ByteEnum<?>>) null),
    SERVER_INFORMATION("420088", (Class<ByteEnum<?>>) null),
    SPLIT_KEY("420089", (Class<ByteEnum<?>>) null),
    SPLIT_KEY_METHOD("42008A", SplitKeyMethod.class),
    SPLIT_KEY_PARTS("42008B", (Class<ByteEnum<?>>) null),
    SPLIT_KEY_THRESHOLD("42008C", (Class<ByteEnum<?>>) null),
    STATE("42008D", State.class),
    STORAGE_STATUS_MASK("42008E", (Class<ByteEnum<?>>) null),
    SYMMETRIC_KEY("42008F", (Class<ByteEnum<?>>) null),
    TEMPLATE("420090", (Class<ByteEnum<?>>) null),
    TEMPLATE_ATTRIBUTE("420091", (Class<ByteEnum<?>>) null),
    TIME_STAMP("420092", (Class<ByteEnum<?>>) null),
    UNIQUE_BATCH_ITEM_ID("420093", (Class<ByteEnum<?>>) null),
    UNIQUE_IDENTIFIER("420094", (Class<ByteEnum<?>>) null),
    USAGE_LIMITS("420095", (Class<ByteEnum<?>>) null),
    USAGE_LIMITS_COUNT("420096", (Class<ByteEnum<?>>) null),
    USAGE_LIMITS_TOTAL("420097", (Class<ByteEnum<?>>) null),
    USAGE_LIMITS_UNIT("420098", UsageLimitsUnit.class),
    USERNAME("420099", (Class<ByteEnum<?>>) null),
    VALIDITY_DATE("42009A", (Class<ByteEnum<?>>) null),
    VALIDITY_INDICATOR("42009B", ValidityIndicator.class),
    VENDOR_EXTENSION("42009C", (Class<ByteEnum<?>>) null),
    VENDOR_IDENTIFICATION("42009D", (Class<ByteEnum<?>>) null),
    WRAPPING_METHOD("42009E", WrappingMethod.class),
    X("42009F", (Class<ByteEnum<?>>) null),
    Y("4200A0", (Class<ByteEnum<?>>) null),
    PASSWORD("4200A1", (Class<ByteEnum<?>>) null),
    DEVICE_IDENTIFIER("4200A2", (Class<ByteEnum<?>>) null),
    ENCODING_OPTION("4200A3", KeyWrapEncodingOption.class),
    EXTENSION_INFORMATION("4200A4", (Class<ByteEnum<?>>) null),
    EXTENSION_NAME("4200A5", (Class<ByteEnum<?>>) null),
    EXTENSION_TAG("4200A6", (Class<ByteEnum<?>>) null),
    EXTENSION_TYPE("4200A7", (Class<ByteEnum<?>>) null),
    FRESH("4200A8", (Class<ByteEnum<?>>) null),
    MACHINE_IDENTIFIER("4200A9", (Class<ByteEnum<?>>) null),
    MEDIA_IDENTIFIER("4200AA", (Class<ByteEnum<?>>) null),
    NETWORK_IDENTIFIER("4200AB", (Class<ByteEnum<?>>) null),
    OBJECT_GROUP_MEMBER("4200AC", (Class<ByteEnum<?>>) null),
    CERTIFICATE_LENGTH("4200AD", (Class<ByteEnum<?>>) null),
    DIGITAL_SIGNATURE_ALGORITHM("4200AE", DigitalSignatureAlgorithm.class),
    CERTIFICATE_SERIAL_NUMBER("4200AF", (Class<ByteEnum<?>>) null),
    DEVICE_SERIAL_NUMBER("4200B0", (Class<ByteEnum<?>>) null),
    ISSUER_ALTERNATIVE_NAME("4200B1", (Class<ByteEnum<?>>) null),
    ISSUER_DISTINGUISHED_NAME("4200B2", (Class<ByteEnum<?>>) null),
    SUBJECT_ALTERNATIVE_NAME("4200B3", (Class<ByteEnum<?>>) null),
    SUBJECT_DISTINGUISHED_NAME("4200B4", (Class<ByteEnum<?>>) null),
    X_509_CERTIFICATE_IDENTIFIER("4200B5", (Class<ByteEnum<?>>) null),
    X_509_CERTIFICATE_ISSUER("4200B6", (Class<ByteEnum<?>>) null),
    X_509_CERTIFICATE_SUBJECT("4200B7", (Class<ByteEnum<?>>) null),
    RESERVED("4200B8", "42FFFF"),
    UNUSED_B("430000", "53FFFF"),
    EXTENSIONS("540000", "54FFFF"),
    UNUSED_C("550000", "FFFFFF");

    private final BigInteger start;
    private final BigInteger end;
    private final Class<? extends ByteEnum<?>> enumeration;

    MessageTag(String hexValue, Class<? extends ByteEnum<?>> enumeration) {
        start = new BigInteger(hexValue, 16);
        end = start;
        this.enumeration = enumeration;
    }

    MessageTag(String hexStart, String hexEnd) {
        start = new BigInteger(hexStart, 16);
        end = new BigInteger(hexEnd, 16);
        enumeration = null;
    }

    public BigInteger getStart() {
        return start;
    }

    public BigInteger getEnd() {
        return end;
    }

    public static MessageTag fromBytes(byte[] bytes) {
        BigInteger comparator = new BigInteger(bytes);
        for (MessageTag value : values()) {
            if (value.start.compareTo(comparator) >= 0 && value.end.compareTo(comparator) <= 0) {
                return value;
            }
        }
        return null;
    }

    public Class<? extends ByteEnum<?>> getEnumeration() {
        return enumeration;
    }
}
