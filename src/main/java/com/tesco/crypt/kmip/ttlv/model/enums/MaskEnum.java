package com.tesco.crypt.kmip.ttlv.model.enums;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public interface MaskEnum<T> {

    static <T extends Enum<T>> List<MaskEnum<?>> fromBytes(BigInteger mask, Class<? extends MaskEnum<?>> maskType) {
        List<MaskEnum<?>> masks = new ArrayList<>();
        if (mask != null) {
            for (MaskEnum<?> value : maskType.getEnumConstants()) {
                if (mask.and(value.getValue()).compareTo(value.getValue()) == 0) {
                    masks.add(value);
                }
            }
        }
        return masks;
    }

    BigInteger getValue();

    Class<T> getType();

}
