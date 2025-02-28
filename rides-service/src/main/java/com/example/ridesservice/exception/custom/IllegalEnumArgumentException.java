package com.example.ridesservice.exception.custom;

import com.example.ridesservice.exception.MessageSourceException;

public class IllegalEnumArgumentException extends MessageSourceException {

    public IllegalEnumArgumentException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
