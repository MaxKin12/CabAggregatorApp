package com.example.driverservice.utility.validation.impl;

import static com.example.driverservice.utility.constants.DriverTestData.DRIVER;
import static com.example.driverservice.utility.constants.DriverTestData.DRIVER_ID;
import static com.example.driverservice.utility.constants.DriverTestData.RATE_CHANGE_EVENT_RESPONSE;
import static com.example.driverservice.utility.constants.GeneralUtilityConstants.ATTEMPT_CHANGE_UPDATE;
import static com.example.driverservice.utility.constants.GeneralUtilityConstants.EXCEPTION_MESSAGE;
import static com.example.driverservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.DRIVER_NOT_FOUND;
import static com.example.driverservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.INVALID_ATTEMPT_CHANGE_DRIVER;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.driverservice.exception.custom.DbModificationAttemptException;
import com.example.driverservice.exception.custom.EntityNotFoundException;
import com.example.driverservice.model.entity.Driver;
import com.example.driverservice.repository.DriverRepository;
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
    private DriverRepository driverRepository;

    @Test
    void findByIdOrThrowTest_ValidId_ReturnsPassenger() {
        Long id = DRIVER_ID;
        Driver driver = DRIVER;

        when(driverRepository.findById(id)).thenReturn(Optional.of(driver));

        Driver result = validation.findByIdOrThrow(id);

        assertNotNull(result);
        assertEquals(driver, result);
        verify(driverRepository).findById(id);
    }

    @Test
    void findByIdOrThrowTest_InvalidId_ThrowsException() {
        Long id = DRIVER_ID;
        String[] args = new String[] {id.toString()};

        when(driverRepository.findById(id)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> validation.findByIdOrThrow(id));

        assertEquals(DRIVER_NOT_FOUND, exception.getMessageKey());
        assertArrayEquals(args, exception.getArgs());
        verify(driverRepository).findById(id);
    }

    @Test
    void updateOrThrowTest_ValidAttemptToUpdateEntity_UpdatesPassenger() {
        doNothing().when(driverRepository).flush();

        assertDoesNotThrow(() -> validation.updateOrThrow(DRIVER, RATE_CHANGE_EVENT_RESPONSE));

        verify(driverRepository).flush();
    }

    @Test
    void updateOrThrowTest_InvalidAttemptToUpdateEntity_ThrowsException() {
        String[] args = new String[] {ATTEMPT_CHANGE_UPDATE, EXCEPTION_MESSAGE};

        doThrow(new RuntimeException(EXCEPTION_MESSAGE)).when(driverRepository).flush();

        DbModificationAttemptException exception = assertThrows(DbModificationAttemptException.class,
                () -> validation.updateOrThrow(DRIVER, RATE_CHANGE_EVENT_RESPONSE));

        assertEquals(INVALID_ATTEMPT_CHANGE_DRIVER, exception.getMessageKey());
        assertArrayEquals(args, exception.getArgs());
        verify(driverRepository).flush();
    }

}
