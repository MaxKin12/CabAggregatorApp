package com.example.ratesservice.kafka.impl;

import com.example.ratesservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.ratesservice.kafka.KafkaProducerService;
import com.example.ratesservice.mapper.event.RateChangeEventMapper;
import com.example.ratesservice.model.RateChangeEvent;
import com.example.ratesservice.repository.RateEventsRepository;
import com.example.ratesservice.utility.validation.KafkaProducerServiceValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private final KafkaProducerServiceValidation validation;
    private final RateEventsRepository rateEventsRepository;
    private final RateChangeEventMapper rateChangeEventMapper;

    @Override
    @Transactional
    public void sendAndDeleteMessage(String topic, RateChangeEvent event) {
        RateChangeEventResponse eventResponse = rateChangeEventMapper.toResponse(event);
        validation.sendOrThrow(topic, eventResponse);
        rateEventsRepository.delete(event);
    }

}
