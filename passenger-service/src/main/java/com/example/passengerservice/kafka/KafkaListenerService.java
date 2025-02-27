package com.example.passengerservice.kafka;

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
            topics = "${spring.kafka.consumer.topic-passenger-rate}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listen(RateChangeEventResponse event) {
        passengerService.updateRate(event);
    }

}
