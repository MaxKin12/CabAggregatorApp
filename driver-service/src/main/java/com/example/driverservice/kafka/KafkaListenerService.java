package com.example.driverservice.kafka;

import static com.example.driverservice.utility.constants.KafkaListenerPropertyVariablesConstants.CONSUMER_GROUP_ID;
import static com.example.driverservice.utility.constants.KafkaListenerPropertyVariablesConstants.CONSUMER_TOPIC_PASSENGER_RATE;
import static com.example.driverservice.utility.constants.LogMessagesTemplate.KAFKA_LISTENER_EVENT_LOG_TEMPLATE;

import com.example.driverservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaListenerService {

    private final DriverService driverService;

    @KafkaListener(
            topics = CONSUMER_TOPIC_PASSENGER_RATE,
            groupId = CONSUMER_GROUP_ID
    )
    public void listen(RateChangeEventResponse event) {
        log.info(KAFKA_LISTENER_EVENT_LOG_TEMPLATE, event);
        driverService.updateRate(event);
    }

}
