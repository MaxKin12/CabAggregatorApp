package com.example.ridesservice.exception.external;

import com.example.ridesservice.exception.MessageSourceException;

public class ExternalServiceClientBadRequest extends MessageSourceException {

    public ExternalServiceClientBadRequest(String messageKey, String... args) {
        super(messageKey, args);
    }

}
