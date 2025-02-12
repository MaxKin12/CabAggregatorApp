package com.example.ratesservice.exception.custom;

public class RateListIsEmptyException extends RuntimeException {

    public RateListIsEmptyException(String message) {
        super(message);
    }

}
