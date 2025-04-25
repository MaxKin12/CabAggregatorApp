package com.example.authservice.exception.custom;

import com.example.authservice.exception.MessageSourceException;

public class ForbiddenAccessException extends MessageSourceException {

    public ForbiddenAccessException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
