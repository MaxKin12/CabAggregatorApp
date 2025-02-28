package com.example.driverservice.exception.custom;

import com.example.driverservice.exception.MessageSourceException;

public class IllegalEnumArgumentException extends MessageSourceException {

    public IllegalEnumArgumentException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
