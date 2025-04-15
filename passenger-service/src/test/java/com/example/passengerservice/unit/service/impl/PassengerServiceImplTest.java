package com.example.passengerservice.unit.service.impl;

import static com.example.passengerservice.configuration.constants.GeneralUtilityConstants.ATTEMPT_CHANGE_CREATE;
import static com.example.passengerservice.configuration.constants.GeneralUtilityConstants.ATTEMPT_CHANGE_UPDATE;
import static com.example.passengerservice.configuration.constants.GeneralUtilityConstants.EXCEPTION_MESSAGE;
import static com.example.passengerservice.configuration.constants.PassengerTestData.LIMIT;
import static com.example.passengerservice.configuration.constants.PassengerTestData.LIMIT_CUT;
import static com.example.passengerservice.configuration.constants.PassengerTestData.OFFSET;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER_ID;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER_PAGE;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER_PAGE_RESPONSE;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER_REQUEST;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER_RESPONSE;
import static com.example.passengerservice.configuration.constants.PassengerTestData.RATE_CHANGE_EVENT_RESPONSE;
import static com.example.passengerservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.INVALID_ATTEMPT_CHANGE_PASSENGER;
import static com.example.passengerservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.PASSENGER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.example.passengerservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.passengerservice.dto.passenger.PassengerPageResponse;
import com.example.passengerservice.dto.passenger.PassengerRequest;
import com.example.passengerservice.dto.passenger.PassengerResponse;
import com.example.passengerservice.exception.custom.DbModificationAttemptException;
import com.example.passengerservice.exception.custom.PassengerNotFoundException;
import com.example.passengerservice.mapper.PassengerMapper;
import com.example.passengerservice.mapper.PassengerPageMapper;
import com.example.passengerservice.model.Passenger;
import com.example.passengerservice.repository.PassengerRepository;
import com.example.passengerservice.service.impl.PassengerServiceImpl;
import com.example.passengerservice.utility.validation.PassengerServiceValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class PassengerServiceImplTest {

    @InjectMocks
    private PassengerServiceImpl passengerService;

    @Mock
    private PassengerServiceValidation validation;

    @Mock
    private PassengerRepository passengerRepository;

    @Mock
    private PassengerMapper passengerMapper;

    @Mock
    private PassengerPageMapper passengerPageMapper;

    @Test
    void findByIdTest_ValidId_ReturnsValidResponseEntity() {
        UUID id = PASSENGER_ID;
        Passenger passenger = PASSENGER;
        PassengerResponse passengerResponse = PASSENGER_RESPONSE;

        when(validation.findByIdOrThrow(id)).thenReturn(passenger);
        when(passengerMapper.toResponse(passenger)).thenReturn(passengerResponse);

        PassengerResponse result = passengerService.findById(id);

        assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(passengerResponse);
        verify(validation).findByIdOrThrow(id);
        verify(passengerMapper).toResponse(passenger);
    }

    @Test
    void findByIdTest_InvalidId_ThrowsException() {
        UUID id = PASSENGER_ID;
        String[] args = new String[] {id.toString()};

        when(validation.findByIdOrThrow(id))
                .thenThrow(new PassengerNotFoundException(PASSENGER_NOT_FOUND, args));

        assertThatExceptionOfType(PassengerNotFoundException.class)
                .isThrownBy(() -> passengerService.findById(id))
                .withMessage(PASSENGER_NOT_FOUND)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(validation).findByIdOrThrow(id);
        verifyNoInteractions(passengerMapper);
    }

    @Test
    void findAllTset_UncutLimit_ReturnsValidResponseEntity() {
        int offset = OFFSET;
        int limit = LIMIT;
        PageRequest pageRequest = PageRequest.of(offset, limit);
        Page<Passenger> passengerPage = PASSENGER_PAGE;
        PassengerPageResponse passengerPageResponse = PASSENGER_PAGE_RESPONSE;

        when(validation.cutDownLimit(limit)).thenReturn(limit);
        when(passengerRepository.findAll(pageRequest)).thenReturn(passengerPage);
        when(passengerPageMapper.toResponsePage(passengerPage, offset, limit)).thenReturn(passengerPageResponse);

        PassengerPageResponse result = passengerService.findAll(offset, limit);

        assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(passengerPageResponse);
        verify(validation).cutDownLimit(limit);
        verify(passengerRepository).findAll(pageRequest);
        verify(passengerPageMapper).toResponsePage(passengerPage, offset, limit);
    }

    @Test
    void findAllTest_CutLimit_ReturnsValidResponseEntity() {
        int offset = OFFSET;
        int limit = LIMIT;
        int limitCut = LIMIT_CUT;
        PageRequest pageRequest = PageRequest.of(offset, limitCut);
        Page<Passenger> passengerPage = PASSENGER_PAGE;
        PassengerPageResponse passengerPageResponse = PASSENGER_PAGE_RESPONSE;

        when(validation.cutDownLimit(limit)).thenReturn(limitCut);
        when(passengerRepository.findAll(pageRequest)).thenReturn(passengerPage);
        when(passengerPageMapper.toResponsePage(passengerPage, offset, limitCut)).thenReturn(passengerPageResponse);

        PassengerPageResponse result = passengerService.findAll(offset, limit);

        assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(passengerPageResponse);
        verify(validation).cutDownLimit(limit);
        verify(passengerRepository).findAll(pageRequest);
        verify(passengerPageMapper).toResponsePage(passengerPage, offset, limitCut);
    }

    @Test
    void createTest_ValidRequestEntity_ReturnsValidResponseEntity() {
        PassengerRequest passengerRequest = PASSENGER_REQUEST;
        Passenger passenger = PASSENGER;
        PassengerResponse passengerResponse = PASSENGER_RESPONSE;

        when(passengerMapper.toPassenger(passengerRequest)).thenReturn(passenger);
        when(validation.saveOrThrow(passenger)).thenReturn(passenger);
        when(passengerMapper.toResponse(passenger)).thenReturn(passengerResponse);

        PassengerResponse result = passengerService.create(passengerRequest);

        assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(passengerResponse);
        verify(passengerMapper).toPassenger(passengerRequest);
        verify(validation).saveOrThrow(passenger);
        verify(passengerMapper).toResponse(passenger);
    }

    @Test
    void createTest_InvalidAttemptToSaveEntity_ThrowsException() {
        PassengerRequest passengerRequest = PASSENGER_REQUEST;
        Passenger passenger = PASSENGER;
        String[] args = new String[] {ATTEMPT_CHANGE_CREATE, EXCEPTION_MESSAGE};

        when(passengerMapper.toPassenger(passengerRequest)).thenReturn(passenger);
        when(validation.saveOrThrow(passenger))
                .thenThrow(new DbModificationAttemptException(INVALID_ATTEMPT_CHANGE_PASSENGER, args));

        assertThatExceptionOfType(DbModificationAttemptException.class)
                .isThrownBy(() -> passengerService.create(passengerRequest))
                .withMessage(INVALID_ATTEMPT_CHANGE_PASSENGER)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(passengerMapper).toPassenger(passengerRequest);
        verify(validation).saveOrThrow(passenger);
    }

    @Test
    void updatePassengerTest_ValidIdAndRequestEntity_ReturnsValidResponseEntity() {
        UUID id = PASSENGER_ID;
        PassengerRequest passengerRequest = PASSENGER_REQUEST;
        Passenger passenger = PASSENGER;
        PassengerResponse passengerResponseUpdated = PASSENGER_RESPONSE;

        when(validation.findByIdOrThrow(id)).thenReturn(passenger);
        doNothing().when(validation).updateOrThrow(passenger, passengerRequest);
        when(passengerMapper.toResponse(passenger)).thenReturn(passengerResponseUpdated);

        PassengerResponse result = passengerService.updatePassenger(passengerRequest, id);

        assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(passengerResponseUpdated);
        verify(validation).findByIdOrThrow(id);
        verify(validation).updateOrThrow(passenger, passengerRequest);
        verify(passengerMapper).toResponse(passenger);
    }

    @Test
    void updatePassengerTest_InvalidId_ThrowsEntityNotFoundException() {
        UUID id = PASSENGER_ID;
        String[] args = new String[] {id.toString()};

        when(validation.findByIdOrThrow(id))
                .thenThrow(new PassengerNotFoundException(PASSENGER_NOT_FOUND, args));

        assertThatExceptionOfType(PassengerNotFoundException.class)
                .isThrownBy(() -> passengerService.updatePassenger(PASSENGER_REQUEST, id))
                .withMessage(PASSENGER_NOT_FOUND)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(validation).findByIdOrThrow(id);
        verifyNoInteractions(passengerMapper);
    }

    @Test
    void updatePassengerTest_ValidIdButInvalidAttemptToUpdateEntity_ThrowsDbModificationAttemptException() {
        UUID id = PASSENGER_ID;
        PassengerRequest passengerRequest = PASSENGER_REQUEST;
        Passenger passenger = PASSENGER;
        String[] args = new String[] {ATTEMPT_CHANGE_UPDATE, EXCEPTION_MESSAGE};

        when(validation.findByIdOrThrow(id)).thenReturn(passenger);
        doThrow(new DbModificationAttemptException(INVALID_ATTEMPT_CHANGE_PASSENGER, args))
                .when(validation)
                .updateOrThrow(passenger, passengerRequest);

        assertThatExceptionOfType(DbModificationAttemptException.class)
                .isThrownBy(() -> passengerService.updatePassenger(passengerRequest, id))
                .withMessage(INVALID_ATTEMPT_CHANGE_PASSENGER)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(validation).findByIdOrThrow(id);
        verify(validation).updateOrThrow(passenger, passengerRequest);
        verifyNoInteractions(passengerMapper);
    }

    @Test
    void updateRateTest_ValidChangeEvent_EntityIsUpdated() {
        RateChangeEventResponse event = RATE_CHANGE_EVENT_RESPONSE;

        when(validation.findByIdOrThrow(event.recipientId())).thenReturn(PASSENGER);

        assertThatCode(() -> passengerService.updateRate(event))
                .doesNotThrowAnyException();

        verify(validation).findByIdOrThrow(event.recipientId());
    }

    @Test
    void updateRateTest_IdInChangeEventInvalid_ThrowsEntityNotFoundException() {
        RateChangeEventResponse event = RATE_CHANGE_EVENT_RESPONSE;
        String[] args = new String[] {event.recipientId().toString()};

        when(validation.findByIdOrThrow(event.recipientId()))
                .thenThrow(new PassengerNotFoundException(PASSENGER_NOT_FOUND, args));

        assertThatExceptionOfType(PassengerNotFoundException.class)
                .isThrownBy(() -> passengerService.updateRate(event))
                .withMessage(PASSENGER_NOT_FOUND)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(validation).findByIdOrThrow(event.recipientId());
    }

    @Test
    void deleteTest_ValidId_EntityIsDeleted() {
        UUID id = PASSENGER_ID;

        when(validation.findByIdOrThrow(id)).thenReturn(PASSENGER);
        doNothing().when(passengerRepository).deleteById(id);

        assertThatCode(() -> passengerService.delete(id))
                .doesNotThrowAnyException();

        verify(validation).findByIdOrThrow(id);
        verify(passengerRepository).deleteById(id);
    }

    @Test
    void deleteTest_InvalidId_ThrowsException() {
        UUID id = PASSENGER_ID;
        String[] args = new String[] {id.toString()};

        when(validation.findByIdOrThrow(id)).thenThrow(new PassengerNotFoundException(PASSENGER_NOT_FOUND, args));

        assertThatExceptionOfType(PassengerNotFoundException.class)
                .isThrownBy(() -> passengerService.delete(id))
                .withMessage(PASSENGER_NOT_FOUND)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(validation).findByIdOrThrow(id);
        verifyNoInteractions(passengerRepository);
    }

}
