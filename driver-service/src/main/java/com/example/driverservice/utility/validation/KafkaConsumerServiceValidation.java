package com.example.driverservice.utility.validation;

import com.example.driverservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.driverservice.model.entity.Driver;

public interface KafkaConsumerServiceValidation {

    Driver findByIdOrThrow(Long id);

    void updateOrThrow(Driver driver, RateChangeEventResponse event);

}
