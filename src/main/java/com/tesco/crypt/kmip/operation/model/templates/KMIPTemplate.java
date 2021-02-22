package com.tesco.crypt.kmip.operation.model.templates;

import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.ObjectType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
public abstract class KMIPTemplate {

    @Setter(AccessLevel.PROTECTED)
    private ObjectType objectType;

    public abstract void decode(List<TTLV> templateItems);

    public abstract List<TTLV> encode();

}
