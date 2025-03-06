package com.example.passengerservice.utility.validation.impl;

import static com.example.passengerservice.utility.constants.GeneralUtilityConstants.ATTEMPT_CHANGE_CREATE;
import static com.example.passengerservice.utility.constants.GeneralUtilityConstants.ATTEMPT_CHANGE_UPDATE;
import static com.example.passengerservice.utility.constants.GeneralUtilityConstants.EXCEPTION_MESSAGE;
import static com.example.passengerservice.utility.constants.PassengerTestData.*;
import static com.example.passengerservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.INVALID_ATTEMPT_CHANGE_PASSENGER;
import static com.example.passengerservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.PASSENGER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.example.passengerservice.configuration.properties.PassengerServiceProperties;
import com.example.passengerservice.dto.passenger.PassengerRequest;
import com.example.passengerservice.exception.custom.DbModificationAttemptException;
import com.example.passengerservice.exception.custom.PassengerNotFoundException;
import com.example.passengerservice.mapper.PassengerMapper;
import com.example.passengerservice.model.Passenger;
import com.example.passengerservice.repository.PassengerRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PassengerServiceValidationImplTest {

    @InjectMocks
    private PassengerServiceValidationImpl validation;

    @Mock
    private PassengerServiceProperties properties;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private PassengerMapper passengerMapper;

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
    void cutDownLimitTest_LimitLessThanMax_ReturnsSameLimit() {
        int limit = LIMIT_CUT;
        int maxLimit = LIMIT;

        when(properties.maxPageLimit()).thenReturn(maxLimit);

        int result = validation.cutDownLimit(limit);

        assertEquals(limit, result);
        verify(properties).maxPageLimit();
    }

    @Test
    void cutDownLimitTest_LimitGreaterThanMax_ReturnsCutLimit() {
        int limit = LIMIT;
        int maxLimit = LIMIT_CUT;

        when(properties.maxPageLimit()).thenReturn(maxLimit);

        int result = validation.cutDownLimit(limit);

        assertEquals(maxLimit, result);
        verify(properties, times(2)).maxPageLimit();
    }

    @Test
    void saveOrThrowTest_ValidEntity_SavesPassenger() {
        Passenger passenger = PASSENGER;

        when(passengerRepository.save(passenger)).thenReturn(passenger);

        Passenger result = validation.saveOrThrow(passenger);

        assertNotNull(result);
        assertEquals(passenger, result);
        verify(passengerRepository).save(passenger);
    }

    @Test
    void saveOrThrowTest_InvalidEntity_ThrowsException() {
        Passenger passenger = PASSENGER;
        String[] args = new String[] {ATTEMPT_CHANGE_CREATE, EXCEPTION_MESSAGE};

        when(passengerRepository.save(passenger)).thenThrow(new RuntimeException(EXCEPTION_MESSAGE));

        DbModificationAttemptException exception = assertThrows(DbModificationAttemptException.class,
                () -> validation.saveOrThrow(passenger));

        assertEquals(INVALID_ATTEMPT_CHANGE_PASSENGER, exception.getMessageKey());
        assertArrayEquals(args, exception.getArgs());
        verify(passengerRepository).save(passenger);
    }

    @Test
    void updateOrThrowTest_ValidAttemptToUpdateEntity_UpdatesPassenger() {
        Passenger passenger = PASSENGER;
        PassengerRequest request = PASSENGER_REQUEST;

        doNothing().when(passengerMapper).updatePassengerFromDto(request, passenger);
        doNothing().when(passengerRepository).flush();

        assertDoesNotThrow(() -> validation.updateOrThrow(passenger, request));

        verify(passengerMapper).updatePassengerFromDto(request, passenger);
        verify(passengerRepository).flush();
    }

    @Test
    void updateOrThrowTest_InvalidAttemptToUpdateEntity_ThrowsException() {
        Passenger passenger = PASSENGER;
        PassengerRequest request = PASSENGER_REQUEST;
        String[] args = new String[] {ATTEMPT_CHANGE_UPDATE, EXCEPTION_MESSAGE};

        doThrow(new RuntimeException(EXCEPTION_MESSAGE))
                .when(passengerMapper).updatePassengerFromDto(request, passenger);

        DbModificationAttemptException exception = assertThrows(DbModificationAttemptException.class,
                () -> validation.updateOrThrow(passenger, request));

        assertEquals(INVALID_ATTEMPT_CHANGE_PASSENGER, exception.getMessageKey());
        assertArrayEquals(args, exception.getArgs());
        verify(passengerMapper).updatePassengerFromDto(request, passenger);
        verifyNoInteractions(passengerRepository);
    }

}
