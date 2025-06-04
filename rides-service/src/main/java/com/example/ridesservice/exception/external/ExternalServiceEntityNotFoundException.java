package com.example.ridesservice.exception.external;

import com.example.ridesservice.exception.MessageSourceException;

public class ExternalServiceEntityNotFoundException extends MessageSourceException {

    public ExternalServiceEntityNotFoundException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
