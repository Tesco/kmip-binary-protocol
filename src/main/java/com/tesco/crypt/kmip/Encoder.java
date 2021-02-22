package com.tesco.crypt.kmip;

public interface Encoder<S, T> {

    T encode(S s);

}
