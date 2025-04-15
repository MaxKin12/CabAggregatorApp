package com.example.passengerservice.service;

import static com.example.passengerservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.ID_NEGATIVE;

import com.example.passengerservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.passengerservice.dto.passenger.PassengerPageResponse;
import com.example.passengerservice.dto.passenger.PassengerRequest;
import com.example.passengerservice.dto.passenger.PassengerResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public interface PassengerService {

    PassengerResponse findById(UUID id);

    PassengerPageResponse findAll(@Min(0) Integer offset, @Min(1) Integer limit);

    PassengerResponse create(@Valid PassengerRequest passengerRequest);

    PassengerResponse updatePassenger(@Valid PassengerRequest passengerRequest, UUID id);

    void updateRate(RateChangeEventResponse event);

    void delete(@Positive(message = ID_NEGATIVE) UUID id);

}
