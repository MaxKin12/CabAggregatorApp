package com.example.ratesservice.client.exception;

import com.example.ratesservice.exception.MessageSourceException;

public class ExternalServiceEntityNotFoundException extends MessageSourceException {

    public ExternalServiceEntityNotFoundException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
