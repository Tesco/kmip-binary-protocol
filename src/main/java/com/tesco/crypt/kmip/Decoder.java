package com.tesco.crypt.kmip;

import java.util.function.Consumer;

public interface Decoder<S, T> {

    void decode(S s, Consumer<T> consumer);

}
