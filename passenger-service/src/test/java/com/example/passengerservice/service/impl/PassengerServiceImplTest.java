package com.example.passengerservice.service.impl;

import static com.example.passengerservice.constants.PassengerServiceTestData.EXCEPTION_MESSAGE;
import static com.example.passengerservice.constants.PassengerServiceTestData.LIMIT;
import static com.example.passengerservice.constants.PassengerServiceTestData.LIMIT_CUT;
import static com.example.passengerservice.constants.PassengerServiceTestData.OFFSET;
import static com.example.passengerservice.constants.PassengerServiceTestData.PASSENGER;
import static com.example.passengerservice.constants.PassengerServiceTestData.PASSENGER1_ID;
import static com.example.passengerservice.constants.PassengerServiceTestData.PASSENGER_PAGE;
import static com.example.passengerservice.constants.PassengerServiceTestData.PASSENGER_PAGE_RESPONSE;
import static com.example.passengerservice.constants.PassengerServiceTestData.PASSENGER_REQUEST;
import static com.example.passengerservice.constants.PassengerServiceTestData.PASSENGER_RESPONSE;
import static com.example.passengerservice.constants.PassengerServiceTestData.RATE_CHANGE_EVENT_RESPONSE;
import static com.example.passengerservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.INVALID_ATTEMPT_CHANGE_PASSENGER;
import static com.example.passengerservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.PASSENGER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
import com.example.passengerservice.utility.validation.PassengerServiceValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

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
    void findByIdTest_IdIsValid_ReturnsValidResponseEntity() {
        Long id = PASSENGER1_ID;
        Passenger passenger = PASSENGER;
        PassengerResponse passengerResponse = PASSENGER_RESPONSE;

        when(validation.findByIdOrThrow(id)).thenReturn(passenger);
        when(passengerMapper.toResponse(passenger)).thenReturn(passengerResponse);

        PassengerResponse result = passengerService.findById(id);

        assertNotNull(result);
        assertEquals(passengerResponse, result);
        verify(validation).findByIdOrThrow(id);
        verify(passengerMapper).toResponse(passenger);
    }

    @Test
    void findByIdTset_PassengerIsNotFound_ThrowsException() {
        Long id = PASSENGER1_ID;
        String messageKey = PASSENGER_NOT_FOUND;
        String[] args = new String[] {id.toString()};

        when(validation.findByIdOrThrow(id))
                .thenThrow(new PassengerNotFoundException(messageKey, args));

        PassengerNotFoundException exception = assertThrows(PassengerNotFoundException.class,
                () -> passengerService.findById(id));

        assertEquals(messageKey, exception.getMessageKey());
        assertEquals(args, exception.getArgs());
        verify(validation).findByIdOrThrow(id);
        verifyNoInteractions(passengerMapper);
    }

    @Test
    void findAllTset_LimitIsNotCut_ReturnsValidResponseEntity() {
        int offset = OFFSET;
        int limit = LIMIT;
        PageRequest pageRequest = PageRequest.of(offset, limit);
        Page<Passenger> passengerPage = PASSENGER_PAGE;
        PassengerPageResponse passengerPageResponse = PASSENGER_PAGE_RESPONSE;

        when(validation.cutDownLimit(limit)).thenReturn(limit);
        when(passengerRepository.findAll(pageRequest)).thenReturn(passengerPage);
        when(passengerPageMapper.toResponsePage(passengerPage, offset, limit)).thenReturn(passengerPageResponse);

        PassengerPageResponse result = passengerService.findAll(offset, limit);

        assertNotNull(result);
        assertEquals(passengerPageResponse, result);
        verify(validation).cutDownLimit(limit);
        verify(passengerRepository).findAll(pageRequest);
        verify(passengerPageMapper).toResponsePage(passengerPage, offset, limit);
    }

    @Test
    void findAll_LimitIsCut_ReturnsValidResponseEntity() {
        int offset = OFFSET;
        int limit = LIMIT;
        int limit_cut = LIMIT_CUT;
        PageRequest pageRequest = PageRequest.of(offset, limit_cut);
        Page<Passenger> passengerPage = PASSENGER_PAGE;
        PassengerPageResponse passengerPageResponse = PASSENGER_PAGE_RESPONSE;

        when(validation.cutDownLimit(limit)).thenReturn(limit_cut);
        when(passengerRepository.findAll(pageRequest)).thenReturn(passengerPage);
        when(passengerPageMapper.toResponsePage(passengerPage, offset, limit_cut)).thenReturn(passengerPageResponse);

        PassengerPageResponse result = passengerService.findAll(offset, limit);

        assertNotNull(result);
        assertEquals(passengerPageResponse, result);
        verify(validation).cutDownLimit(limit);
        verify(passengerRepository).findAll(pageRequest);
        verify(passengerPageMapper).toResponsePage(passengerPage, offset, limit_cut);
    }

    @Test
    void createTset_RequestEntityIsValid_ReturnsValidResponseEntity() {
        PassengerRequest passengerRequest = PASSENGER_REQUEST;
        Passenger passenger = PASSENGER;
        PassengerResponse passengerResponse = PASSENGER_RESPONSE;

        when(passengerMapper.toPassenger(passengerRequest)).thenReturn(passenger);
        when(validation.saveOrThrow(passenger)).thenReturn(passenger);
        when(passengerMapper.toResponse(passenger)).thenReturn(passengerResponse);

        PassengerResponse result = passengerService.create(passengerRequest);

        assertNotNull(result);
        assertEquals(passengerResponse, result);
        verify(passengerMapper).toPassenger(passengerRequest);
        verify(validation).saveOrThrow(passenger);
        verify(passengerMapper).toResponse(passenger);
    }

    @Test
    void createTset_AttemptToSaveEntityIsInvalid_ThrowsException() {
        PassengerRequest passengerRequest = PASSENGER_REQUEST;
        Passenger passenger = PASSENGER;
        String messageKey = INVALID_ATTEMPT_CHANGE_PASSENGER;
        String[] args = new String[] {"create", EXCEPTION_MESSAGE};

        when(passengerMapper.toPassenger(passengerRequest)).thenReturn(passenger);
        when(validation.saveOrThrow(passenger))
                .thenThrow(new DbModificationAttemptException(messageKey, args));

        DbModificationAttemptException exception = assertThrows(DbModificationAttemptException.class,
                () -> passengerService.create(passengerRequest));

        assertEquals(messageKey, exception.getMessageKey());
        assertEquals(args, exception.getArgs());
        verify(passengerMapper).toPassenger(passengerRequest);
        verify(validation).saveOrThrow(passenger);
    }

    @Test
    void updatePassengerTest_RequestEntityAndIdAreValid_ReturnsValidResponseEntity() {
        Long id = PASSENGER1_ID;
        PassengerRequest passengerRequest = PASSENGER_REQUEST;
        Passenger passenger = PASSENGER;
        PassengerResponse passengerResponseUpdated = PASSENGER_RESPONSE;

        when(validation.findByIdOrThrow(id)).thenReturn(passenger);
        doNothing().when(validation).updateOrThrow(passenger, passengerRequest);
        when(passengerMapper.toResponse(passenger)).thenReturn(passengerResponseUpdated);

        PassengerResponse result = passengerService.updatePassenger(passengerRequest, id);

        assertNotNull(result);
        assertEquals(passengerResponseUpdated, result);
        verify(validation).findByIdOrThrow(id);
        verify(validation).updateOrThrow(passenger, passengerRequest);
        verify(passengerMapper).toResponse(passenger);
    }

    @Test
    void updatePassengerTest_IdIsInvalid_ThrowsEntityNotFoundException() {
        Long id = PASSENGER1_ID;
        String messageKey = PASSENGER_NOT_FOUND;
        String[] args = new String[] {id.toString()};

        when(validation.findByIdOrThrow(id))
                .thenThrow(new PassengerNotFoundException(messageKey, args));

        PassengerNotFoundException exception = assertThrows(PassengerNotFoundException.class,
                () -> passengerService.updatePassenger(PASSENGER_REQUEST, id));

        assertEquals(messageKey, exception.getMessageKey());
        assertEquals(args, exception.getArgs());
        verify(validation).findByIdOrThrow(id);
        verifyNoInteractions(passengerMapper);
    }

    @Test
    void updatePassengerTest_ValidIdButInvalidAttemptToUpdateEntity_ThrowsDbModificationAttemptException() {
        Long id = PASSENGER1_ID;
        PassengerRequest passengerRequest = PASSENGER_REQUEST;
        Passenger passenger = PASSENGER;
        String messageKey = PASSENGER_NOT_FOUND;
        String[] args = new String[] {id.toString()};

        when(validation.findByIdOrThrow(id)).thenReturn(passenger);
        doThrow(new DbModificationAttemptException(messageKey, args))
                .when(validation)
                .updateOrThrow(passenger, passengerRequest);

        DbModificationAttemptException exception = assertThrows(DbModificationAttemptException.class,
                () -> passengerService.updatePassenger(passengerRequest, id));

        assertEquals(messageKey, exception.getMessageKey());
        assertEquals(args, exception.getArgs());
        verify(validation).findByIdOrThrow(id);
        verify(validation).updateOrThrow(passenger, passengerRequest);
        verifyNoInteractions(passengerMapper);
    }

    @Test
    void updateRateTest_ChangeEventIsValid_EntityIsUpdated() {
        RateChangeEventResponse event = RATE_CHANGE_EVENT_RESPONSE;

        when(validation.findByIdOrThrow(event.recipientId())).thenReturn(PASSENGER);

        assertDoesNotThrow(() -> passengerService.updateRate(event));

        verify(validation).findByIdOrThrow(event.recipientId());
    }

    @Test
    void updateRateTest_IdInChangeEventIsNotValid_ThrowsEntityNotFoundException() {
        RateChangeEventResponse event = RATE_CHANGE_EVENT_RESPONSE;
        String messageKey = PASSENGER_NOT_FOUND;
        String[] args = new String[] {event.recipientId().toString()};

        when(validation.findByIdOrThrow(event.recipientId()))
                .thenThrow(new PassengerNotFoundException(messageKey, args));

        PassengerNotFoundException exception = assertThrows(PassengerNotFoundException.class,
                () -> passengerService.updateRate(event));

        assertEquals(messageKey, exception.getMessageKey());
        assertEquals(args, exception.getArgs());
        verify(validation).findByIdOrThrow(event.recipientId());
    }

    @Test
    void deleteTest_IdIsValid_EntityIsDeleted() {
        Long id = PASSENGER1_ID;

        when(validation.findByIdOrThrow(id)).thenReturn(PASSENGER);
        doNothing().when(passengerRepository).deleteById(id);

        assertDoesNotThrow(() -> passengerService.delete(id));

        verify(validation).findByIdOrThrow(id);
        verify(passengerRepository).deleteById(id);
    }

    @Test
    void deleteTest_IdIsNotValid_ThrowsException() {
        Long id = PASSENGER1_ID;
        String[] args = new String[] {id.toString()};

        when(validation.findByIdOrThrow(id)).thenThrow(new PassengerNotFoundException(PASSENGER_NOT_FOUND, args));

        PassengerNotFoundException exception = assertThrows(PassengerNotFoundException.class,
                () -> passengerService.delete(id));

        assertEquals(PASSENGER_NOT_FOUND, exception.getMessageKey());
        assertEquals(args, exception.getArgs());
        verify(validation).findByIdOrThrow(id);
        verifyNoInteractions(passengerRepository);
    }

}
