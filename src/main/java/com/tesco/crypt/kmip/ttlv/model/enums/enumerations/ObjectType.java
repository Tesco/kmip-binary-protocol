package com.tesco.crypt.kmip.ttlv.model.enums.enumerations;

import com.tesco.crypt.kmip.operation.model.objects.KMIPObject;
import com.tesco.crypt.kmip.operation.model.objects.SymmetricKey;
import com.tesco.crypt.kmip.operation.model.templates.KMIPTemplate;
import com.tesco.crypt.kmip.operation.model.templates.SymmetricKeyTemplate;
import com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum;
import com.tesco.crypt.kmip.ttlv.model.enums.MessageTag;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum ObjectType implements ByteEnum<ObjectType> {
    CERTIFICATE("1", null, null),
    SYMMETRIC_KEY("2", SymmetricKey.class, SymmetricKeyTemplate.class),
    PUBLIC_KEY("3", null, null),
    PRIVATE_KEY("4", null, null),
    SPLIT_KEY("5", null, null),
    TEMPLATE("6", null, null),
    SECRET_DATA("7", null, null),
    OPAQUE_OBJECT("8", null, null),
    EXTENSIONS("8XXXXXXX", null, null);

    private final BigInteger start;
    private final BigInteger end;
    private final Class<? extends KMIPObject> objectClass;
    private final Class<? extends KMIPTemplate> templateClass;

    public static final List<MessageTag> OBJECT_MESSAGE_TAGS = Arrays.stream(ObjectType.values()).map(Enum::name).map(enumName -> MessageTag.valueOf(MessageTag.class, enumName)).collect(Collectors.toList());

    ObjectType(String hexValue, Class<? extends KMIPObject> objectClass, Class<? extends KMIPTemplate> templateClass) {
        this.objectClass = objectClass;
        this.templateClass = templateClass;
        if (hexValue.contains("X")) {
            start = new BigInteger(hexValue.replaceAll("X", "0"), 16);
            end = new BigInteger(hexValue.replaceAll("X", "F"), 16);
        } else {
            start = new BigInteger(hexValue, 16);
            end = start;
        }
    }

    @Override
    public Class<ObjectType> getType() {
        return this.getDeclaringClass();
    }

    public BigInteger getStart() {
        return start;
    }

    public BigInteger getEnd() {
        return end;
    }

    public Class<? extends KMIPObject> getObjectClass() {
        return objectClass;
    }

    public Class<? extends KMIPTemplate> getTemplateClass() {
        return templateClass;
    }
}
