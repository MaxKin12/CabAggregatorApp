package com.example.ratesservice.utility.validation;

import com.example.ratesservice.dto.kafkaevent.RateChangeEventResponse;

public interface KafkaProducerServiceValidation {

    void sendOrThrow(String topic, RateChangeEventResponse event);

}
