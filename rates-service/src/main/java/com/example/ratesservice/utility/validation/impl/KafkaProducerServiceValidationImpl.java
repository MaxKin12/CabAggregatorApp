package com.example.ratesservice.utility.validation.impl;

import static com.example.ratesservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.KAFKA_INVALID_SEND;
import static com.example.ratesservice.utility.constants.LogMessagesTemplate.KAFKA_PRODUCER_EVENT_TO_TOPIC_LOG_TEMPLATE;

import com.example.ratesservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.ratesservice.exception.custom.KafkaSendException;
import com.example.ratesservice.utility.validation.KafkaProducerServiceValidation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerServiceValidationImpl implements KafkaProducerServiceValidation {

    private final KafkaTemplate<String, RateChangeEventResponse> kafkaTemplate;

    @Override
    public void sendOrThrow(String topic, RateChangeEventResponse event) {
        try {
            kafkaTemplate.send(topic, event);
            log.info(KAFKA_PRODUCER_EVENT_TO_TOPIC_LOG_TEMPLATE, topic, event);
        } catch (Exception e) {
            throw new KafkaSendException(KAFKA_INVALID_SEND, e.getMessage());
        }
    }

}
