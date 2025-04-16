package com.example.authservice.exception.custom;

import com.example.authservice.exception.MessageSourceException;

public class RefreshTokenException extends MessageSourceException {

    public RefreshTokenException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
