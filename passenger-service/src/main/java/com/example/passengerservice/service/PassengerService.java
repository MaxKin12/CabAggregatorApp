package com.example.passengerservice.service;

import com.example.passengerservice.dto.PassengerList;
import com.example.passengerservice.dto.PassengerRequest;
import com.example.passengerservice.dto.PassengerResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import static com.example.passengerservice.constant.ExceptionMessagesConstants.NEGATIVE_ID_MESSAGE;

public interface PassengerService {
    PassengerResponse findById(@Positive(message = NEGATIVE_ID_MESSAGE) Long id);
    PassengerList findAll();
    PassengerResponse create(@Valid PassengerRequest passengerRequest);
    PassengerResponse update(@Valid PassengerRequest passengerRequest,
                             @Positive(message = NEGATIVE_ID_MESSAGE) Long id);
    void delete(@Positive(message = NEGATIVE_ID_MESSAGE) Long id);
}
