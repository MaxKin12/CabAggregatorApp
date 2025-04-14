package com.example.authservice.exception.custom;

import com.example.authservice.exception.MessageSourceException;

public class IllegalEnumArgumentException extends MessageSourceException {

    public IllegalEnumArgumentException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
