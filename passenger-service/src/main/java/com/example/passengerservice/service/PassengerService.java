package com.example.passengerservice.service;

import com.example.passengerservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.passengerservice.dto.passenger.PassengerPageResponse;
import com.example.passengerservice.dto.passenger.PassengerRequest;
import com.example.passengerservice.dto.passenger.PassengerResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.UUID;

public interface PassengerService {

    PassengerResponse findById(UUID id);

    PassengerPageResponse findAll(@Min(0) Integer offset, @Min(1) Integer limit);

    PassengerResponse create(@Valid PassengerRequest passengerRequest);

    PassengerResponse updatePassenger(@Valid PassengerRequest passengerRequest, UUID passengerId);

    void updateRate(RateChangeEventResponse event);

    void delete(UUID passengerId);

}
