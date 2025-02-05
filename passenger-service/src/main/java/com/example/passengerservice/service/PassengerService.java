package com.example.passengerservice.service;

import com.example.passengerservice.dto.PassengerRequest;
import com.example.passengerservice.dto.PassengerResponse;
import com.example.passengerservice.dto.PassengerResponseList;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

public interface PassengerService {
    PassengerResponse findById(@Positive(message = "{validate.method.parameter.id.negative}") Long id);

    PassengerResponseList findAll();

    PassengerResponse create(@Valid PassengerRequest passengerRequest);

    PassengerResponse update(@Valid PassengerRequest passengerRequest,
                             @Positive(message = "{validate.method.parameter.id.negative}") Long id);

    void delete(@Positive(message = "{validate.method.parameter.id.negative}") Long id);
}
