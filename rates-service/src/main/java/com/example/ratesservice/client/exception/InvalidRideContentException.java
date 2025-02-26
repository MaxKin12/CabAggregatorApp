package com.example.ratesservice.client.exception;

import com.example.ratesservice.exception.MessageSourceException;

public class InvalidRideContentException extends MessageSourceException {

    public InvalidRideContentException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
