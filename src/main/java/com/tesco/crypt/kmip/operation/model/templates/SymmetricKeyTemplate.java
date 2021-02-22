package com.tesco.crypt.kmip.operation.model.templates;

import com.tesco.crypt.kmip.ttlv.model.Structure;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum;
import com.tesco.crypt.kmip.ttlv.model.enums.MaskEnum;
import com.tesco.crypt.kmip.ttlv.model.enums.MessageTag;
import com.tesco.crypt.kmip.ttlv.model.enums.bitmasks.CryptographicUsageMask;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.CryptographicAlgorithm;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.ObjectType;
import com.tesco.crypt.kmip.ttlv.model.values.EnumerationValue;
import com.tesco.crypt.kmip.ttlv.model.values.IntegerValue;
import com.tesco.crypt.kmip.ttlv.model.values.MaskValue;
import com.tesco.crypt.kmip.ttlv.model.values.TextStringValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.checkAndCast;
import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.invalidMessageTag;
import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.*;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

@Data
public class SymmetricKeyTemplate extends KMIPTemplate {

    private CryptographicAlgorithm cryptographicAlgorithm;
    private List<CryptographicUsageMask> cryptographicUsageMask;
    private Integer cryptographicLength;

    public SymmetricKeyTemplate() {
        setObjectType(ObjectType.SYMMETRIC_KEY);
    }

    @Override
    public void decode(List<TTLV> templateItems) {
        for (TTLV templateAttributeItem : templateItems) {
            if (templateAttributeItem.getTag() == ATTRIBUTE) {
                String attributeName = null;
                for (TTLV attributeItems : checkAndCast(templateAttributeItem, Structure.class).getTtlvs()) {
                    switch (attributeItems.getTag()) {
                        case ATTRIBUTE_NAME:
                            attributeName = checkAndCast(attributeItems, TextStringValue.class).getValue();
                            break;
                        case ATTRIBUTE_VALUE:
                            if (isNotBlank(attributeName)) {
                                switch (attributeName) {
                                    case "Cryptographic Algorithm":
                                        ByteEnum<?> value = checkAndCast(attributeItems, EnumerationValue.class).getValue();
                                        if (value instanceof CryptographicAlgorithm) {
                                            cryptographicAlgorithm = (CryptographicAlgorithm) value;
                                        }
                                        break;
                                    case "Cryptographic Usage Mask":
                                        cryptographicUsageMask = new ArrayList<>();
                                        for (MaskEnum<?> maskEnum : checkAndCast(attributeItems, MaskValue.class).getValue()) {
                                            if (maskEnum instanceof CryptographicUsageMask) {
                                                cryptographicUsageMask.add((CryptographicUsageMask) maskEnum);
                                            }
                                        }
                                        break;
                                    case "Cryptographic Length":
                                        cryptographicLength = checkAndCast(attributeItems, IntegerValue.class).getValue();
                                        break;
                                }
                            }
                            break;
                        default:
                            invalidMessageTag(attributeItems, Arrays.asList(ATTRIBUTE_NAME, ATTRIBUTE_INDEX, ATTRIBUTE_VALUE));
                    }
                }
            } else {
                invalidMessageTag(templateAttributeItem, Arrays.asList(ATTRIBUTE_NAME, ATTRIBUTE_INDEX, ATTRIBUTE));
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
        List<TTLV> objectValues = new ArrayList<>();
        if (cryptographicAlgorithm != null) {
            objectValues.add(
                new Structure().setTtlvs(Arrays.asList(
                    new TextStringValue()
                        .setValue("Cryptographic Algorithm")
                        .setTag(ATTRIBUTE_NAME),
                    new EnumerationValue()
                        .setValue(cryptographicAlgorithm)
                        .setTag(ATTRIBUTE_VALUE)
                )).setTag(ATTRIBUTE)
            );
        }
        if (cryptographicLength != null) {
            objectValues.add(
                new Structure().setTtlvs(Arrays.asList(
                    new TextStringValue()
                        .setValue("Cryptographic Usage Mask")
                        .setTag(ATTRIBUTE_NAME),
                    new MaskValue()
                        .setValue(cryptographicUsageMask)
                        .setTag(ATTRIBUTE_VALUE)
                )).setTag(ATTRIBUTE)
            );
        }
        if (cryptographicLength != null) {
            objectValues.add(
                new Structure().setTtlvs(Arrays.asList(
                    new TextStringValue()
                        .setValue("Cryptographic Length")
                        .setTag(ATTRIBUTE_NAME),
                    new IntegerValue()
                        .setValue(cryptographicLength)
                        .setTag(ATTRIBUTE_VALUE)
                )).setTag(ATTRIBUTE)
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
