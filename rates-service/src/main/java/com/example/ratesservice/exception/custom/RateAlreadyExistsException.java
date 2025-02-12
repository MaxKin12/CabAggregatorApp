package com.example.ratesservice.exception.custom;

public class RateAlreadyExistsException extends RuntimeException {

    public RateAlreadyExistsException(String message) {
        super(message);
    }

}
