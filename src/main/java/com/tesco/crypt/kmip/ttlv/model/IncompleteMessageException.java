package com.tesco.crypt.kmip.ttlv.model;

public class IncompleteMessageException extends RuntimeException {

    public IncompleteMessageException(String message) {
        super(message);
    }

    public IncompleteMessageException(String message, Throwable cause) {
        super(message, cause);
    }

}
