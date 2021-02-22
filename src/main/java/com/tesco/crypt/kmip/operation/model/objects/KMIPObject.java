package com.tesco.crypt.kmip.operation.model.objects;

import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.enumerations.ObjectType;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.List;

@Data
public abstract class KMIPObject {

    private String objectId;
    @Setter(AccessLevel.PROTECTED)
    private ObjectType objectType;

    public abstract void decode(String objectId, List<TTLV> objectItems);

    public abstract List<TTLV> encode();

}
