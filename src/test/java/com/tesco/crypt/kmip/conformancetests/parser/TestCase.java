package com.tesco.crypt.kmip.conformancetests.parser;

import com.tesco.crypt.kmip.ttlv.model.TTLV;
import lombok.Data;

@Data
public class TestCase {

    public String name;
    public String hex;
    public TTLV ttlv;

}

