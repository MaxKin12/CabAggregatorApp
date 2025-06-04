package com.example.ratesservice.exception.external;

import com.example.ratesservice.exception.MessageSourceException;

public class ExternalServiceUnknownInternalServerError extends MessageSourceException {

    public ExternalServiceUnknownInternalServerError(String messageKey, String... args) {
        super(messageKey, args);
    }

}
