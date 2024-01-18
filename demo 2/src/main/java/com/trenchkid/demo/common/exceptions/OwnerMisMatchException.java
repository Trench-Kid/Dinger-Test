package com.trenchkid.demo.common.exceptions;

public class OwnerMisMatchException extends RuntimeException {
    public OwnerMisMatchException(String message) {
        super(message);
    }

    public OwnerMisMatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
