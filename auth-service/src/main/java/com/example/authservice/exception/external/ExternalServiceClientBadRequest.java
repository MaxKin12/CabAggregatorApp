package com.example.authservice.exception.external;

import com.example.authservice.exception.MessageSourceException;

public class ExternalServiceClientBadRequest extends MessageSourceException {

    public ExternalServiceClientBadRequest(String messageKey, String... args) {
        super(messageKey, args);
    }

}
