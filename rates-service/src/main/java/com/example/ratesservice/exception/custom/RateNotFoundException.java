package com.example.ratesservice.exception.custom;

public class RateNotFoundException extends RuntimeException {

    public RateNotFoundException(String message) {
        super(message);
    }

}
