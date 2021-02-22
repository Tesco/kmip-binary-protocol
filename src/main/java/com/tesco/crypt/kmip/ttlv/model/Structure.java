package com.tesco.crypt.kmip.ttlv.model;

import com.tesco.crypt.kmip.Decoder;
import com.tesco.crypt.kmip.operation.model.attributes.AttributeValue;
import com.tesco.crypt.kmip.operation.model.attributes.NameAttributeValue;
import com.tesco.crypt.kmip.operation.model.attributes.NamespacedAttributeValue;
import com.tesco.crypt.kmip.ttlv.model.enums.ItemType;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.NameType;
import com.tesco.crypt.kmip.ttlv.model.values.TextStringValue;
import lombok.Data;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.invalidMessageTag;
import static com.tesco.crypt.kmip.ttlv.model.enums.ByteEnum.parseEnumeration;
import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.*;

@Data
@SuppressWarnings("UnusedReturnValue")
public class Structure extends TTLV {

    List<TTLV> ttlvs = new ArrayList<>();

    public Structure() {
        super(ItemType.STRUCTURE);
    }

    public String getDescription() {
        return super.getDescription() + "(" + ttlvs.size() + ")";
    }

    private static String readStringValue(TTLV message) {
        if (message instanceof TextStringValue) {
            return ((TextStringValue) message).getValue();
        } else {
            throw new IllegalArgumentException("Invalid value class expected " + TextStringValue.class + " found " + message.getClass());
        }
    }

    @Override
    public void encode(OutputStream outputStream, Function<TTLV, byte[]> encode) {
        getTtlvs().stream().map(encode).forEach(ttlvBytes -> writeBytes(outputStream, ttlvBytes));
    }

    @Override
    public AttributeValue<?> decode(String name) {
        AttributeValue<?> attributeValue = null;
        if (getTag() == ATTRIBUTE_VALUE) {
            String applicationNamespace = null;
            String applicationData = null;
            String nameValue = null;
            NameType nameType = null;
            for (TTLV attributePropertyItem : getTtlvs()) {
                switch (attributePropertyItem.getTag()) {
                    case APPLICATION_NAMESPACE:
                        applicationNamespace = readStringValue(attributePropertyItem);
                        break;
                    case APPLICATION_DATA:
                        applicationData = readStringValue(attributePropertyItem);
                        break;
                    case NAME_VALUE:
                        nameValue = readStringValue(attributePropertyItem);
                        break;
                    case NAME_TYPE:
                        nameType = parseEnumeration(attributePropertyItem, NameType.class);
                        break;
                    default:
                        invalidMessageTag(attributePropertyItem, Arrays.asList(APPLICATION_NAMESPACE, APPLICATION_DATA, NAME_VALUE, NAME_TYPE));
                }
            }
            if (applicationNamespace != null) {
                attributeValue = new NamespacedAttributeValue().setName(name).setNamespace(applicationNamespace).setValue(applicationData);
            }
            if (nameValue != null) {
                attributeValue = new NameAttributeValue().setName(name).setType(nameType).setValue(nameValue);
            }
        } else {
            invalidMessageTag(this, Collections.singletonList(ATTRIBUTE_VALUE));
        }
        return attributeValue;
    }

    public Structure addTtlvs(TTLV... messages) {
        return addTtlvs(Arrays.asList(messages));
    }

    public Structure addTtlvs(List<TTLV> messages) {
        for (TTLV message : messages) {
            if (message != null) {
                message.setParent(this);
                this.ttlvs.add(message);
            }
        }
        return this;
    }

    public Integer getLength() {
        int length = 0;
        for (TTLV ttlv : ttlvs) {
            length += ttlv.getByteLength();
        }
        return length;
    }

    public Structure setTtlvs(List<TTLV> ttlvs) {
        return addTtlvs(ttlvs);
    }

    public Structure addTtlvs(InputStream inputStream, Decoder<InputStream, TTLV> decoder) {
        decoder.decode(inputStream, this::addTtlvs);
        return this;
    }
}
