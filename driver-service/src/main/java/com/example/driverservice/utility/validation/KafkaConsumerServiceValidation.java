package com.example.driverservice.utility.validation;

import com.example.driverservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.driverservice.model.entity.Driver;
import java.util.UUID;

public interface KafkaConsumerServiceValidation {

    Driver findByIdOrThrow(UUID id);

    void updateOrThrow(Driver driver, RateChangeEventResponse event);

}
