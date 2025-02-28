package com.example.ratesservice.kafka;

import com.example.ratesservice.model.RateChangeEvent;

public interface KafkaProducerService {

    void sendAndDeleteMessage(String topic, RateChangeEvent event);

}
