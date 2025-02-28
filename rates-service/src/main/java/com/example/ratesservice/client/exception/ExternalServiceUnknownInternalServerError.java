package com.example.ratesservice.client.exception;

import com.example.ratesservice.exception.MessageSourceException;

public class ExternalServiceUnknownInternalServerError extends MessageSourceException {

    public ExternalServiceUnknownInternalServerError(String messageKey, String... args) {
        super(messageKey, args);
    }

}
