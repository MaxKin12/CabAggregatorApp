package com.example.authservice.exception.custom;

import com.example.authservice.exception.MessageSourceException;

public class LoginAttemptException extends MessageSourceException {

    public LoginAttemptException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
