package com.example.authservice.exception.custom;

import com.example.authservice.exception.MessageSourceException;

public class FeignClientTemporarilyUnavailable extends MessageSourceException {

    public FeignClientTemporarilyUnavailable(String messageKey, String... args) {
        super(messageKey, args);
    }

}
