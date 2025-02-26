package com.example.driverservice.kafka;

import static com.example.driverservice.utility.constants.KafkaConsumerPropertyVariablesConstants.CONSUMER_GROUP_ID;
import static com.example.driverservice.utility.constants.KafkaConsumerPropertyVariablesConstants.CONSUMER_TOPIC_PASSENGER_RATE;

import com.example.driverservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.driverservice.model.entity.Driver;
import com.example.driverservice.utility.validation.KafkaConsumerServiceValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final KafkaConsumerServiceValidation validation;

    @KafkaListener(
            topics = CONSUMER_TOPIC_PASSENGER_RATE,
            groupId = CONSUMER_GROUP_ID
    )
    public void listen(RateChangeEventResponse event) {
        updateRate(event);
    }

    @Transactional
    protected void updateRate(RateChangeEventResponse event) {
        Driver driver = validation.findByIdOrThrow(event.recipientId());
        validation.updateOrThrow(driver, event);
    }

}
