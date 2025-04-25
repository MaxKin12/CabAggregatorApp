package com.example.authservice.exception.custom;

import com.example.authservice.exception.MessageSourceException;

public class LogoutException extends MessageSourceException {

    public LogoutException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
