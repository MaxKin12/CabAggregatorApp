package com.example.passengerservice.utility.validation;

import com.example.passengerservice.dto.passenger.PassengerRequest;
import com.example.passengerservice.model.Passenger;

import java.util.UUID;

public interface PassengerServiceValidation {

    Passenger findByIdOrThrow(UUID id);

    int cutDownLimit(int limit);

    Passenger saveOrThrow(Passenger passenger);

    void updateOrThrow(Passenger passenger, PassengerRequest passengerRequest);

}
