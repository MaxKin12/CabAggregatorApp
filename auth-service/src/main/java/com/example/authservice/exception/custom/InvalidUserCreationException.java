package com.example.authservice.exception.custom;

import com.example.authservice.exception.MessageSourceException;

public class InvalidUserCreationException extends MessageSourceException {

    public InvalidUserCreationException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
