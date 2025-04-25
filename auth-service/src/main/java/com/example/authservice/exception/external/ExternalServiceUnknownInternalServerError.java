package com.example.authservice.exception.external;

import com.example.authservice.exception.MessageSourceException;

public class ExternalServiceUnknownInternalServerError extends MessageSourceException {

    public ExternalServiceUnknownInternalServerError(String messageKey, String... args) {
        super(messageKey, args);
    }

}
