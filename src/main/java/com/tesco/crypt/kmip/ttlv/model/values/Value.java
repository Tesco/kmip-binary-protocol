package com.tesco.crypt.kmip.ttlv.model.values;

import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.ItemType;
import lombok.Data;

@Data
public abstract class Value<T> extends TTLV {

    public Value(ItemType itemType) {
        super(itemType);
    }

    public abstract T getValue();

    public String getDescription() {
        return super.getDescription() + "(" + ((Value<?>) this).getValue() + ")";
    }

    protected int nextMultipleOfEight(int size) {
        return (size + 7) & ~7;
    }

}
