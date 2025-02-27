package com.example.driverservice.kafka;

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
            topics = "${spring.kafka.consumer.topic-driver-rate}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listen(RateChangeEventResponse event) {
        driverService.updateRate(event);
    }

}
