package com.example.ratesservice.utility.kafka.impl;

import com.example.ratesservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.ratesservice.exception.custom.KafkaSendException;
import com.example.ratesservice.mapper.event.RateChangeEventMapper;
import com.example.ratesservice.model.RateChangeEvent;
import com.example.ratesservice.repository.RateEventsRepository;
import com.example.ratesservice.utility.kafka.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaTemplate<String, RateChangeEventResponse> kafkaTemplate;

    private final RateEventsRepository rateEventsRepository;

    private final RateChangeEventMapper rateChangeEventMapper;

    @Override
    @Transactional
    public void sendAndDeleteMessage(String topic, RateChangeEvent event) {
        RateChangeEventResponse eventResponse = rateChangeEventMapper.toResponse(event);
        sendOrThrow(topic, eventResponse);
        rateEventsRepository.delete(event);
    }

    private void sendOrThrow(String topic, RateChangeEventResponse event) {
        try {
            kafkaTemplate.send(topic, event);
        }
        catch (Exception e) {
            throw new KafkaSendException(e.getMessage());
        }
    }

}
