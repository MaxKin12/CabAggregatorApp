package com.example.ratesservice.exception.custom;

import com.example.ratesservice.exception.MessageSourceException;

public class IllegalEnumArgumentException extends MessageSourceException {

    public IllegalEnumArgumentException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
