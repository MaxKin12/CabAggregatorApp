package com.example.passengerservice.kafka;

import static com.example.passengerservice.utility.constants.KafkaListenerPropertyVariablesConstants.CONSUMER_GROUP_ID;
import static com.example.passengerservice.utility.constants.KafkaListenerPropertyVariablesConstants.CONSUMER_TOPIC_PASSENGER_RATE;

import com.example.passengerservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.passengerservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaListenerService {

    private final PassengerService passengerService;

    @KafkaListener(
            topics = CONSUMER_TOPIC_PASSENGER_RATE,
            groupId = CONSUMER_GROUP_ID
    )
    public void listen(RateChangeEventResponse event) {
        passengerService.updateRate(event);
    }

}
