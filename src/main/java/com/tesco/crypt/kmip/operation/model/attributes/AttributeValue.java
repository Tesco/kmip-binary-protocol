package com.tesco.crypt.kmip.operation.model.attributes;

import com.tesco.crypt.kmip.ttlv.model.Structure;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.values.IntegerValue;
import com.tesco.crypt.kmip.ttlv.model.values.TextStringValue;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.checkAndCast;
import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.invalidMessageTag;
import static com.tesco.crypt.kmip.ttlv.model.enums.MessageTag.*;

@Data
public abstract class AttributeValue<T extends AttributeValue<T>> {

    String name;
    Integer index;

    @SuppressWarnings("unchecked")
    public T setName(String name) {
        this.name = name;
        return (T) this;
    }

    public List<TTLV> encode() {
        List<TTLV> attributeTtlvs = new ArrayList<>();
        attributeTtlvs.add(
            new TextStringValue()
                .setValue(getName())
                .setTag(ATTRIBUTE_NAME)
        );
        return attributeTtlvs;
    }
}
