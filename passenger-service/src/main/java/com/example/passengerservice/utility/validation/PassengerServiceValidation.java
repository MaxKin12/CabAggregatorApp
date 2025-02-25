package com.example.passengerservice.utility.validation;

import com.example.passengerservice.dto.passenger.PassengerRequest;
import com.example.passengerservice.model.Passenger;

public interface PassengerServiceValidation {

    Passenger findByIdOrThrow(Long id);

    int cutDownLimit(int limit);

    Passenger saveOrThrow(Passenger passenger);

    void updateOrThrow(Passenger passenger, PassengerRequest passengerRequest);

}
