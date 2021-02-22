package com.tesco.crypt.kmip.ttlv.model.values;

import com.tesco.crypt.kmip.operation.model.attributes.AttributeValue;
import com.tesco.crypt.kmip.operation.model.attributes.MaskAttributeValue;
import com.tesco.crypt.kmip.ttlv.model.TTLV;
import com.tesco.crypt.kmip.ttlv.model.enums.ItemType;
import com.tesco.crypt.kmip.ttlv.model.enums.MaskEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.OutputStream;
import java.math.BigInteger;
import java.util.List;
import java.util.function.Function;

import static com.tesco.crypt.kmip.ttlv.Hex.hexToBytes;

@Data
public class MaskValue extends Value<List<? extends MaskEnum<?>>> {

    public MaskValue() {
        super(ItemType.INTEGER);
        setLength(4);
        setPadding(4);
    }

    List<? extends MaskEnum<?>> value;

    @Override
    public void encode(OutputStream outputStream, Function<TTLV, byte[]> encode) {
        BigInteger maskValue = BigInteger.ZERO;
        for (MaskEnum<?> maskEnum : getValue()) {
            maskValue = maskValue.add(maskEnum.getValue());
        }
        String hexString = StringUtils.leftPad(maskValue.toString(16), 8, "0") + StringUtils.repeat("0", 8);
        writeBytes(outputStream, hexToBytes(hexString));
    }

    @Override
    public AttributeValue<?> decode(String name) {
        return new MaskAttributeValue().setName(name).setValue(getValue());
    }
}
