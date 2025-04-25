package com.example.passengerservice.utility.validation;

import com.example.passengerservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.passengerservice.model.Passenger;

import java.util.UUID;

public interface KafkaConsumerServiceValidation {

    Passenger findByIdOrThrow(UUID id);

    void updateOrThrow(Passenger passenger, RateChangeEventResponse event);

}
