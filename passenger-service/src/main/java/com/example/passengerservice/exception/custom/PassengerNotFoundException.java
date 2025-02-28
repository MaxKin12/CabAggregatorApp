package com.example.passengerservice.exception.custom;

import com.example.passengerservice.exception.MessageSourceException;

public class PassengerNotFoundException extends MessageSourceException {

    public PassengerNotFoundException(String messageKey, String... args) {
        super(messageKey, args);
    }

}
