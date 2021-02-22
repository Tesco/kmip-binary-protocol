package com.tesco.crypt.kmip.ttlv.model.enums;

import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.values.EnumerationValue;

import java.math.BigInteger;

import static com.tesco.crypt.kmip.operation.TTLVToBatchItemsDecoder.checkAndCast;

public interface ByteEnum<T> {

    @SuppressWarnings("unchecked")
    static <T extends Enum<T>> T fromBytes(byte[] bytes, Class<? extends ByteEnum<?>> enumType) {
        if (enumType != null) {
            BigInteger comparator = new BigInteger(bytes);
            for (ByteEnum<?> value : enumType.getEnumConstants()) {
                if (value.getStart().compareTo(comparator) == 0) {
                    return (T) value;
                }
            }
        } else {
            return null;
        }
        throw new IllegalArgumentException("Invalid hex value " + new BigInteger(bytes).toString(16));
    }

    static <T extends ByteEnum<?>> T parseEnumeration(TTLV ttlv, Class<T> enumerationType) {
        ByteEnum<?> value = checkAndCast(ttlv, EnumerationValue.class).getValue();
        if (enumerationType.isAssignableFrom(value.getClass())) {
            return enumerationType.cast(value);
        } else {
            throw new IllegalArgumentException("invalid enumeration " + value.getClass() + ", expected " + enumerationType + " at " + ttlv.getPath());
        }
    }

    BigInteger getStart();

    BigInteger getEnd();

    Class<T> getType();

    String name();
}
