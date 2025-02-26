package com.example.passengerservice.kafka;

import static com.example.passengerservice.utility.constants.KafkaConsumerPropertyVariablesConstants.CONSUMER_GROUP_ID;
import static com.example.passengerservice.utility.constants.KafkaConsumerPropertyVariablesConstants.CONSUMER_TOPIC_PASSENGER_RATE;

import com.example.passengerservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.passengerservice.model.Passenger;
import com.example.passengerservice.utility.validation.KafkaConsumerServiceValidation;
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
        Passenger passenger = validation.findByIdOrThrow(event.recipientId());
        validation.updateOrThrow(passenger, event);
    }

}
