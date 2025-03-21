package com.example.ratesservice.exception.custom;

import com.example.ratesservice.exception.MessageSourceException;

public class FeignClientTemporarilyUnavailable extends MessageSourceException {

    public FeignClientTemporarilyUnavailable(String messageKey, String... args) {
        super(messageKey, args);
    }

}
