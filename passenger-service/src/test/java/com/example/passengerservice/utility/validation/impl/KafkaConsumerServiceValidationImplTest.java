package com.example.passengerservice.utility.validation.impl;

import static com.example.passengerservice.utility.constants.GeneralUtilityConstants.ATTEMPT_CHANGE_UPDATE;
import static com.example.passengerservice.utility.constants.GeneralUtilityConstants.EXCEPTION_MESSAGE;
import static com.example.passengerservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.INVALID_ATTEMPT_CHANGE_PASSENGER;
import static com.example.passengerservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.PASSENGER_NOT_FOUND;
import static com.example.passengerservice.utility.constants.PassengerTestData.PASSENGER;
import static com.example.passengerservice.utility.constants.PassengerTestData.PASSENGER_ID;
import static com.example.passengerservice.utility.constants.PassengerTestData.RATE_CHANGE_EVENT_RESPONSE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.passengerservice.exception.custom.DbModificationAttemptException;
import com.example.passengerservice.exception.custom.PassengerNotFoundException;
import com.example.passengerservice.model.Passenger;
import com.example.passengerservice.repository.PassengerRepository;
import java.util.Optional;
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
        Long id = PASSENGER_ID;
        Passenger passenger = PASSENGER;

        when(passengerRepository.findById(id)).thenReturn(Optional.of(passenger));

        Passenger result = validation.findByIdOrThrow(id);

        assertNotNull(result);
        assertEquals(passenger, result);
        verify(passengerRepository).findById(id);
    }

    @Test
    void findByIdOrThrowTest_InvalidId_ThrowsException() {
        Long id = PASSENGER_ID;
        String[] args = new String[] {id.toString()};

        when(passengerRepository.findById(id)).thenReturn(Optional.empty());

        PassengerNotFoundException exception = assertThrows(PassengerNotFoundException.class,
                () -> validation.findByIdOrThrow(id));

        assertEquals(PASSENGER_NOT_FOUND, exception.getMessageKey());
        assertArrayEquals(args, exception.getArgs());
        verify(passengerRepository).findById(id);
    }

    @Test
    void updateOrThrowTest_ValidAttemptToUpdateEntity_UpdatesPassenger() {
        doNothing().when(passengerRepository).flush();

        assertDoesNotThrow(() -> validation.updateOrThrow(PASSENGER, RATE_CHANGE_EVENT_RESPONSE));

        verify(passengerRepository).flush();
    }

    @Test
    void updateOrThrowTest_InvalidAttemptToUpdateEntity_ThrowsException() {
        String[] args = new String[] {ATTEMPT_CHANGE_UPDATE, EXCEPTION_MESSAGE};

        doThrow(new RuntimeException(EXCEPTION_MESSAGE)).when(passengerRepository).flush();

        DbModificationAttemptException exception = assertThrows(DbModificationAttemptException.class,
                () -> validation.updateOrThrow(PASSENGER, RATE_CHANGE_EVENT_RESPONSE));

        assertEquals(INVALID_ATTEMPT_CHANGE_PASSENGER, exception.getMessageKey());
        assertArrayEquals(args, exception.getArgs());
        verify(passengerRepository).flush();
    }

}
