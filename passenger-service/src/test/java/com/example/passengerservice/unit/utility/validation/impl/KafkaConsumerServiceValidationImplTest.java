package com.example.passengerservice.unit.utility.validation.impl;

import static com.example.passengerservice.configuration.constants.GeneralUtilityConstants.ATTEMPT_CHANGE_UPDATE;
import static com.example.passengerservice.configuration.constants.GeneralUtilityConstants.EXCEPTION_MESSAGE;
import static com.example.passengerservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.INVALID_ATTEMPT_CHANGE_PASSENGER;
import static com.example.passengerservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.PASSENGER_NOT_FOUND;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER_ID;
import static com.example.passengerservice.configuration.constants.PassengerTestData.RATE_CHANGE_EVENT_RESPONSE;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.passengerservice.exception.custom.DbModificationAttemptException;
import com.example.passengerservice.exception.custom.PassengerNotFoundException;
import com.example.passengerservice.model.Passenger;
import com.example.passengerservice.repository.PassengerRepository;
import com.example.passengerservice.utility.validation.impl.KafkaConsumerServiceValidationImpl;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerServiceValidationImplTest {

    @InjectMocks
    private KafkaConsumerServiceValidationImpl validation;

    @Mock
    private PassengerRepository passengerRepository;

    @Test
    void findByIdOrThrowTest_ValidId_ReturnsPassenger() {
        UUID id = PASSENGER_ID;

        when(passengerRepository.findById(id)).thenReturn(Optional.of(PASSENGER));

        Passenger result = validation.findByIdOrThrow(id);

        assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(PASSENGER);
        verify(passengerRepository).findById(id);
    }

    @Test
    void findByIdOrThrowTest_InvalidId_ThrowsException() {
        UUID id = PASSENGER_ID;
        String[] args = new String[] {id.toString()};

        when(passengerRepository.findById(id)).thenReturn(Optional.empty());

        assertThatExceptionOfType(PassengerNotFoundException.class)
                .isThrownBy(() -> validation.findByIdOrThrow(id))
                .withMessage(PASSENGER_NOT_FOUND)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(passengerRepository).findById(id);
    }

    @Test
    void updateOrThrowTest_ValidAttemptToUpdateEntity_UpdatesPassenger() {
        doNothing().when(passengerRepository).flush();

        assertThatCode(() -> validation.updateOrThrow(PASSENGER, RATE_CHANGE_EVENT_RESPONSE))
                .doesNotThrowAnyException();

        verify(passengerRepository).flush();
    }

    @Test
    void updateOrThrowTest_InvalidAttemptToUpdateEntity_ThrowsException() {
        String[] args = new String[] {ATTEMPT_CHANGE_UPDATE, EXCEPTION_MESSAGE};

        doThrow(new RuntimeException(EXCEPTION_MESSAGE)).when(passengerRepository).flush();

        assertThatExceptionOfType(DbModificationAttemptException.class)
                .isThrownBy(() -> validation.updateOrThrow(PASSENGER, RATE_CHANGE_EVENT_RESPONSE))
                .withMessage(INVALID_ATTEMPT_CHANGE_PASSENGER)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(passengerRepository).flush();
    }

}
