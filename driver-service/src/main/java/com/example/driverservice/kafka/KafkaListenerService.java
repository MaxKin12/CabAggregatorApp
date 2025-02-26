package com.example.driverservice.kafka;

import static com.example.driverservice.utility.constants.KafkaListenerPropertyVariablesConstants.CONSUMER_GROUP_ID;
import static com.example.driverservice.utility.constants.KafkaListenerPropertyVariablesConstants.CONSUMER_TOPIC_PASSENGER_RATE;

import com.example.driverservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaListenerService {

    private final DriverService driverService;

    @KafkaListener(
            topics = CONSUMER_TOPIC_PASSENGER_RATE,
            groupId = CONSUMER_GROUP_ID
    )
    public void listen(RateChangeEventResponse event) {
        driverService.updateRate(event);
    }

}
