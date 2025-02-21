package com.example.passengerservice.service;

import com.example.passengerservice.dto.passenger.PassengerPageResponse;
import com.example.passengerservice.dto.passenger.PassengerRequest;
import com.example.passengerservice.dto.passenger.PassengerResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public interface PassengerService {

    PassengerResponse findById(@Positive(message = "{validate.method.parameter.id.negative}") Long id);

    PassengerPageResponse findAll(@Min(0) Integer offset, @Min(1) Integer limit);

    PassengerResponse create(@Valid PassengerRequest passengerRequest);

    PassengerResponse update(@Valid PassengerRequest passengerRequest,
                             @Positive(message = "{validate.method.parameter.id.negative}") Long id);

    void delete(@Positive(message = "{validate.method.parameter.id.negative}") Long id);

}
