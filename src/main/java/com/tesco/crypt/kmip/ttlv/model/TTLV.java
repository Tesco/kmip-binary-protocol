package com.tesco.crypt.kmip.ttlv.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tesco.crypt.kmip.operation.model.attributes.AttributeValue;
import com.tesco.crypt.kmip.ttlv.TTLVToBytesEncoder;
import com.tesco.crypt.kmip.ttlv.model.enums.ItemType;
import com.tesco.crypt.kmip.ttlv.model.enums.MessageTag;
import lombok.*;

import java.io.OutputStream;
import java.util.function.Function;

@Data
public abstract class TTLV {

    MessageTag tag;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    byte[] tagBytes;
    @Setter(AccessLevel.PRIVATE)
    ItemType itemType;
    @Setter(AccessLevel.PROTECTED)
    Integer length = 0;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    TTLV parent;
    @Setter(AccessLevel.PROTECTED)
    Integer padding = 0;


    public TTLV(ItemType itemType) {
        this.itemType = itemType;
    }

    public int getByteLength() {
        return 3 /* message tag */ + 1 /* item type */ + 4 /* length */ + getLength() + getPadding();
    }

    public String getDescription() {
        return tag != null ? tag.toString() : "";
    }

    public String getPath() {
        if (parent == null) {
            return getDescription();
        }
        return parent.getPath() + "." + getDescription();
    }

    public abstract void encode(OutputStream outputStream, Function<TTLV, byte[]> encode);

    public abstract AttributeValue<?> decode(String name);

    protected void writeBytes(OutputStream outputStream, byte[] bytes) {
        TTLVToBytesEncoder.writeBytes(outputStream, bytes, getDescription());
    }

    protected <T> T checkAndCast(TTLV message, Class<T> valueType) {
        if (valueType.isAssignableFrom(message.getClass())) {
            return valueType.cast(message);
        } else {
            throw new IllegalArgumentException("Invalid value class of " + message.getClass());
        }
    }

}