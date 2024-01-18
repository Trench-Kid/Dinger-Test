package com.trenchkid.demo.common.exceptions;

public class HousingNotFoundException extends RuntimeException {

    public HousingNotFoundException(String message) {
        super(message);
    }

    public HousingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

