package com.example.ratesservice.unit.kafka.impl;

import static com.example.ratesservice.configuration.constants.GeneralUtilityConstants.KAFKA_TEST_TOPIC;
import static com.example.ratesservice.configuration.constants.RateTestData.RATE_CHANGE_EVENT;
import static com.example.ratesservice.configuration.constants.RateTestData.RATE_CHANGE_EVENT_RESPONSE;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.ratesservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.ratesservice.kafka.impl.KafkaProducerServiceImpl;
import com.example.ratesservice.mapper.event.RateChangeEventMapper;
import com.example.ratesservice.model.RateChangeEvent;
import com.example.ratesservice.repository.RateEventsRepository;
import com.example.ratesservice.utility.validation.KafkaProducerServiceValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KafkaProducerServiceImplTest {

    @Mock
    private KafkaProducerServiceValidation validation;

    @Mock
    private RateEventsRepository rateEventsRepository;

    @Mock
    private RateChangeEventMapper rateChangeEventMapper;

    @InjectMocks
    private KafkaProducerServiceImpl kafkaProducerService;

    @Test
    void sendAndDeleteMessage_SuccessfulSendAndDelete() {
        String topic = KAFKA_TEST_TOPIC;
        RateChangeEvent event = RATE_CHANGE_EVENT;
        RateChangeEventResponse eventResponse = RATE_CHANGE_EVENT_RESPONSE;

        when(rateChangeEventMapper.toResponse(event)).thenReturn(eventResponse);

        assertThatNoException().isThrownBy(() -> kafkaProducerService.sendAndDeleteMessage(topic, event));

        verify(validation).sendOrThrow(topic, eventResponse);
        verify(rateEventsRepository).delete(event);
    }

}
