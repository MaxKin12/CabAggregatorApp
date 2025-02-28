package com.example.ridesservice.exception.custom;

import com.example.ridesservice.exception.MessageSourceException;

public class RideNotFoundException extends MessageSourceException {

    public RideNotFoundException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
