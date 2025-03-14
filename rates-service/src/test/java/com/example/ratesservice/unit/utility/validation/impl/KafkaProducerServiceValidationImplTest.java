package com.example.ratesservice.unit.utility.validation.impl;

import static com.example.ratesservice.configuration.constants.GeneralUtilityConstants.EXCEPTION_MESSAGE;
import static com.example.ratesservice.configuration.constants.GeneralUtilityConstants.KAFKA_TEST_TOPIC;
import static com.example.ratesservice.configuration.constants.RateTestData.RATE_CHANGE_EVENT_RESPONSE;
import static com.example.ratesservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.KAFKA_INVALID_SEND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.ratesservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.ratesservice.exception.custom.KafkaSendException;
import com.example.ratesservice.utility.validation.impl.KafkaProducerServiceValidationImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

@ExtendWith(MockitoExtension.class)
class KafkaProducerServiceValidationImplTest {

    @InjectMocks
    private KafkaProducerServiceValidationImpl kafkaProducerServiceValidation;

    @Mock
    private KafkaTemplate<String, RateChangeEventResponse> kafkaTemplate;

    @Test
    void sendOrThrow_SuccessfulSend_NoExceptionThrown() {
        String topic = KAFKA_TEST_TOPIC;
        RateChangeEventResponse event = RATE_CHANGE_EVENT_RESPONSE;

        assertThatNoException().isThrownBy(() -> kafkaProducerServiceValidation.sendOrThrow(topic, event));

        verify(kafkaTemplate).send(topic, event);
    }

    @Test
    void sendOrThrow_FailedSend_ThrowsKafkaSendException() {
        String topic = KAFKA_TEST_TOPIC;
        RateChangeEventResponse event = RATE_CHANGE_EVENT_RESPONSE;
        String[] args = new String[]{EXCEPTION_MESSAGE};

        doThrow(new RuntimeException(EXCEPTION_MESSAGE)).when(kafkaTemplate).send(any(), any());

        assertThatExceptionOfType(KafkaSendException.class)
                .isThrownBy(() -> kafkaProducerServiceValidation.sendOrThrow(topic, event))
                .withMessage(KAFKA_INVALID_SEND)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        assertThrows(KafkaSendException.class, () -> kafkaProducerServiceValidation.sendOrThrow(topic, event));
    }

}
