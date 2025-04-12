package com.example.passengerservice.kafka;

import static com.example.passengerservice.utility.constants.KafkaListenerPropertyVariablesConstants.CONSUMER_GROUP_ID;
import static com.example.passengerservice.utility.constants.KafkaListenerPropertyVariablesConstants.CONSUMER_TOPIC_PASSENGER_RATE;
import static com.example.passengerservice.utility.constants.LogMessagesTemplate.KAFKA_LISTENER_EVENT_LOG_TEMPLATE;

import com.example.passengerservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.passengerservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaListenerService {

    private final PassengerService passengerService;

    @KafkaListener(
            topics = CONSUMER_TOPIC_PASSENGER_RATE,
            groupId = CONSUMER_GROUP_ID
    )
    public void listen(
            @Payload RateChangeEventResponse event,
            @Header(name = KafkaHeaders.RECEIVED_KEY, required = false) Long key,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition
    ) {
        log.info(KAFKA_LISTENER_EVENT_LOG_TEMPLATE, key, partition, event);
        passengerService.updateRate(event);
    }

}
