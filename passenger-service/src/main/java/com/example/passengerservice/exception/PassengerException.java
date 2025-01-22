package com.example.passengerservice.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PassengerException {
    private int statusCode;
    private String message;
}
