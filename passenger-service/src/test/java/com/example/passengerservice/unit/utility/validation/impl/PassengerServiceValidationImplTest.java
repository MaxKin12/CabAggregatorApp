package com.example.passengerservice.unit.utility.validation.impl;

import static com.example.passengerservice.configuration.constants.GeneralUtilityConstants.ATTEMPT_CHANGE_CREATE;
import static com.example.passengerservice.configuration.constants.GeneralUtilityConstants.ATTEMPT_CHANGE_UPDATE;
import static com.example.passengerservice.configuration.constants.GeneralUtilityConstants.EXCEPTION_MESSAGE;
import static com.example.passengerservice.configuration.constants.PassengerTestData.LIMIT;
import static com.example.passengerservice.configuration.constants.PassengerTestData.LIMIT_CUT;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER_ID;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER_REQUEST;
import static com.example.passengerservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.INVALID_ATTEMPT_CHANGE_PASSENGER;
import static com.example.passengerservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.PASSENGER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
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
import com.example.passengerservice.utility.validation.impl.PassengerServiceValidationImpl;
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

        assertThat(result)
                .isNotNull()
                .isEqualTo(passenger);
        verify(passengerRepository).findById(id);
    }

    @Test
    void findByIdOrThrowTest_InvalidId_ThrowsException() {
        Long id = PASSENGER_ID;
        String[] args = new String[] {id.toString()};

        when(passengerRepository.findById(id)).thenReturn(Optional.empty());

        assertThatExceptionOfType(PassengerNotFoundException.class)
                .isThrownBy(() -> validation.findByIdOrThrow(id))
                .withMessage(PASSENGER_NOT_FOUND)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(passengerRepository).findById(id);
    }

    @Test
    void cutDownLimitTest_LimitLessThanMax_ReturnsSameLimit() {
        int limit = LIMIT_CUT;
        int maxLimit = LIMIT;

        when(properties.maxPageLimit()).thenReturn(maxLimit);

        int result = validation.cutDownLimit(limit);

        assertThat(result).isEqualTo(limit);
        verify(properties).maxPageLimit();
    }

    @Test
    void cutDownLimitTest_LimitGreaterThanMax_ReturnsCutLimit() {
        int limit = LIMIT;
        int maxLimit = LIMIT_CUT;

        when(properties.maxPageLimit()).thenReturn(maxLimit);

        int result = validation.cutDownLimit(limit);

        assertThat(result).isEqualTo(maxLimit);
        verify(properties, times(2)).maxPageLimit();
    }

    @Test
    void saveOrThrowTest_ValidEntity_SavesPassenger() {
        Passenger passenger = PASSENGER;

        when(passengerRepository.save(passenger)).thenReturn(passenger);

        Passenger result = validation.saveOrThrow(passenger);

        assertThat(result)
                .isNotNull()
                .isEqualTo(passenger);
        verify(passengerRepository).save(passenger);
    }

    @Test
    void saveOrThrowTest_InvalidEntity_ThrowsException() {
        Passenger passenger = PASSENGER;
        String[] args = new String[] {ATTEMPT_CHANGE_CREATE, EXCEPTION_MESSAGE};

        when(passengerRepository.save(passenger)).thenThrow(new RuntimeException(EXCEPTION_MESSAGE));

        assertThatExceptionOfType(DbModificationAttemptException.class)
                .isThrownBy(() -> validation.saveOrThrow(passenger))
                .withMessage(INVALID_ATTEMPT_CHANGE_PASSENGER)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(passengerRepository).save(passenger);
    }

    @Test
    void updateOrThrowTest_ValidAttemptToUpdateEntity_UpdatesPassenger() {
        Passenger passenger = PASSENGER;
        PassengerRequest request = PASSENGER_REQUEST;

        doNothing().when(passengerMapper).updatePassengerFromDto(request, passenger);
        doNothing().when(passengerRepository).flush();

        assertThatCode(() -> validation.updateOrThrow(passenger, request))
                .doesNotThrowAnyException();

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

        assertThatExceptionOfType(DbModificationAttemptException.class)
                .isThrownBy(() -> validation.updateOrThrow(passenger, request))
                .withMessage(INVALID_ATTEMPT_CHANGE_PASSENGER)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(passengerMapper).updatePassengerFromDto(request, passenger);
        verifyNoInteractions(passengerRepository);
    }

}
