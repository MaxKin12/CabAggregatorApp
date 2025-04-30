package com.example.ratesservice.exception.external;

import com.example.ratesservice.exception.MessageSourceException;

public class ExternalServiceClientBadRequest extends MessageSourceException {

    public ExternalServiceClientBadRequest(String messageKey, String... args) {
        super(messageKey, args);
    }

}
