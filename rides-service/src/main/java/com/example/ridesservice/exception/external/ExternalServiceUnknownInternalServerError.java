package com.example.ridesservice.exception.external;

import com.example.ridesservice.exception.MessageSourceException;

public class ExternalServiceUnknownInternalServerError extends MessageSourceException {

    public ExternalServiceUnknownInternalServerError(String messageKey, String... args) {
        super(messageKey, args);
    }

}
