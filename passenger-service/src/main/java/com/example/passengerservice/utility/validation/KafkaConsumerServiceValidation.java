package com.example.passengerservice.utility.validation;

import com.example.passengerservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.passengerservice.model.Passenger;

public interface KafkaConsumerServiceValidation {

    Passenger findByIdOrThrow(Long id);

    void updateOrThrow(Passenger passenger, RateChangeEventResponse event);

}
