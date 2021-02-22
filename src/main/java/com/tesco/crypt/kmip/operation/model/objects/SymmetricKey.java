package com.tesco.crypt.kmip.operation.model.objects;

import com.tesco.crypt.kmip.operation.model.attributes.Attributes;
import com.tesco.crypt.kmip.ttlv.model.Structure;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.MessageTag;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.CryptographicAlgorithm;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.KeyCompressionType;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.KeyFormatType;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.ObjectType;
import com.tesco.crypt.kmip.ttlv.model.values.ByteStringValue;
import com.tesco.crypt.kmip.ttlv.model.values.EnumerationValue;
import com.tesco.crypt.kmip.ttlv.model.values.IntegerValue;
import com.tesco.crypt.kmip.ttlv.model.values.TextStringValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.checkAndCast;
import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.invalidMessageTag;
import static com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum.parseEnumeration;
import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.*;

@Data
public class SymmetricKey extends KMIPObject {

    private KeyFormatType keyFormatType;
    private KeyCompressionType keyCompressionType;
    private byte[] keyBytes;
    private Attributes attributes = new Attributes();
    private CryptographicAlgorithm cryptographicAlgorithm;
    private Integer cryptographicLength;

    public SymmetricKey() {
        setObjectType(ObjectType.SYMMETRIC_KEY);
    }

    @Override
    public void decode(String objectId, List<TTLV> objectItems) {
        setObjectId(objectId);
        for (TTLV objectItem : objectItems) {
            if (objectItem.getTag() == KEY_BLOCK) {
                for (TTLV keyBlockItem : checkAndCast(objectItem, Structure.class).getTtlvs()) {
                    switch (keyBlockItem.getTag()) {
                        case KEY_FORMAT_TYPE:
                            keyFormatType = parseEnumeration(keyBlockItem, KeyFormatType.class);
                            break;
                        case KEY_COMPRESSION_TYPE:
                            keyCompressionType = parseEnumeration(keyBlockItem, KeyCompressionType.class);
                            break;
                        case KEY_VALUE:
                            for (TTLV keyValueItem : checkAndCast(keyBlockItem, Structure.class).getTtlvs()) {
                                switch (keyValueItem.getTag()) {
                                    case KEY_MATERIAL:
                                        keyBytes = checkAndCast(keyValueItem, ByteStringValue.class).getBytes();
                                        break;
                                    case ATTRIBUTE:
                                        attributes = new Attributes().decode(checkAndCast(keyValueItem, Structure.class).getTtlvs());
                                        break;
                                    default:
                                        invalidMessageTag(keyValueItem, Collections.singletonList(KEY_MATERIAL));
                                }
                            }
                            break;
                        case CRYPTOGRAPHIC_ALGORITHM:
                            cryptographicAlgorithm = parseEnumeration(keyBlockItem, CryptographicAlgorithm.class);
                            break;
                        case CRYPTOGRAPHIC_LENGTH:
                            cryptographicLength = checkAndCast(keyBlockItem, IntegerValue.class).getValue();
                            break;
                        default:
                            invalidMessageTag(keyBlockItem, Arrays.asList(KEY_FORMAT_TYPE, KEY_COMPRESSION_TYPE, KEY_VALUE, CRYPTOGRAPHIC_ALGORITHM, CRYPTOGRAPHIC_LENGTH));
                    }
                }
            } else {
                invalidMessageTag(objectItem, Collections.singletonList(KEY_BLOCK));
            }
        }
    }

    @Override
    public List<TTLV> encode() {
        List<TTLV> ttlvs = new ArrayList<>();
        if (getObjectType() != null) {
            ttlvs.add(
                new EnumerationValue()
                    .setValue(getObjectType())
                    .setTag(OBJECT_TYPE)
            );
        }
        if (getObjectId() != null) {
            ttlvs.add(
                new TextStringValue()
                    .setValue(getObjectId())
                    .setTag(UNIQUE_IDENTIFIER)
            );
        }
        List<TTLV> objectValues = new ArrayList<>();
        if (keyFormatType != null) {
            objectValues.add(
                new EnumerationValue()
                    .setValue(keyFormatType)
                    .setTag(KEY_FORMAT_TYPE)
            );
        }
        if (keyCompressionType != null) {
            objectValues.add(
                new EnumerationValue()
                    .setValue(keyCompressionType)
                    .setTag(KEY_COMPRESSION_TYPE)
            );
        }
        if (keyBytes != null) {
            objectValues.add(
                new Structure()
                    .addTtlvs(
                        new ByteStringValue()
                            .setBytes(keyBytes)
                            .setTag(KEY_MATERIAL)
                    )
                    .setTag(KEY_VALUE)
            );
        }
        if (attributes != null) {
            objectValues.addAll(attributes.encode());
        }
        if (cryptographicAlgorithm != null) {
            objectValues.add(
                new EnumerationValue()
                    .setValue(cryptographicAlgorithm)
                    .setTag(CRYPTOGRAPHIC_ALGORITHM)
            );
        }
        if (cryptographicLength != null) {
            objectValues.add(
                new IntegerValue()
                    .setValue(cryptographicLength)
                    .setTag(CRYPTOGRAPHIC_LENGTH)
            );
        }
        if (!objectValues.isEmpty()) {
            ttlvs.add(
                new Structure()
                    .addTtlvs(
                        new Structure()
                            .addTtlvs(objectValues)
                            .setTag(MessageTag.KEY_BLOCK)
                    )
                    .setTag(MessageTag.SYMMETRIC_KEY)
            );
        }
        return ttlvs;
    }

}
