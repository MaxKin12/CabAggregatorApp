package com.example.ratesservice.exception.custom;

public class KafkaSendException extends RuntimeException {

    public KafkaSendException(String message) {
        super(message);
    }

}
