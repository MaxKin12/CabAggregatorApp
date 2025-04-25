package com.example.authservice.exception.external;

import com.example.authservice.exception.MessageSourceException;

public class ExternalServiceEntityNotFoundException extends MessageSourceException {

    public ExternalServiceEntityNotFoundException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
