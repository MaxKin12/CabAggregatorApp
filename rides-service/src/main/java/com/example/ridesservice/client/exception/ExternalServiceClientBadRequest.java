package com.example.ridesservice.client.exception;

import com.example.ridesservice.exception.MessageSourceException;

public class ExternalServiceClientBadRequest extends MessageSourceException {

    public ExternalServiceClientBadRequest(String messageKey, String... args) {
        super(messageKey, args);
    }

}
