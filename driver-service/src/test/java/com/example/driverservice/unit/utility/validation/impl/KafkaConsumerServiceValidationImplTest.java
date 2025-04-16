package com.example.driverservice.unit.utility.validation.impl;

import static com.example.driverservice.configuration.constants.DriverTestData.DRIVER;
import static com.example.driverservice.configuration.constants.DriverTestData.DRIVER_ID;
import static com.example.driverservice.configuration.constants.DriverTestData.RATE_CHANGE_EVENT_RESPONSE;
import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.ATTEMPT_CHANGE_UPDATE;
import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.EXCEPTION_MESSAGE;
import static com.example.driverservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.DRIVER_NOT_FOUND;
import static com.example.driverservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.INVALID_ATTEMPT_CHANGE_DRIVER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.driverservice.exception.custom.DbModificationAttemptException;
import com.example.driverservice.exception.custom.EntityNotFoundException;
import com.example.driverservice.model.entity.Driver;
import com.example.driverservice.repository.DriverRepository;
import com.example.driverservice.utility.validation.impl.KafkaConsumerServiceValidationImpl;
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
    private DriverRepository driverRepository;

    @Test
    void findByIdOrThrowTest_ValidId_ReturnsDriver() {
        UUID id = DRIVER_ID;
        Driver driver = DRIVER;

        when(driverRepository.findById(id)).thenReturn(Optional.of(driver));

        Driver result = validation.findByIdOrThrow(id);

        assertThat(result).isNotNull().isEqualTo(driver);
        verify(driverRepository).findById(id);
    }

    @Test
    void findByIdOrThrowTest_InvalidId_ThrowsException() {
        UUID id = DRIVER_ID;
        String[] args = new String[] {id.toString()};

        when(driverRepository.findById(id)).thenReturn(Optional.empty());

        assertThatExceptionOfType(EntityNotFoundException.class)
                .isThrownBy(() -> validation.findByIdOrThrow(id))
                .withMessage(DRIVER_NOT_FOUND)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(driverRepository).findById(id);
    }

    @Test
    void updateOrThrowTest_ValidAttemptToUpdateEntity_UpdatesDriver() {
        doNothing().when(driverRepository).flush();

        assertThatCode(() -> validation.updateOrThrow(DRIVER, RATE_CHANGE_EVENT_RESPONSE)).doesNotThrowAnyException();

        verify(driverRepository).flush();
    }

    @Test
    void updateOrThrowTest_InvalidAttemptToUpdateEntity_ThrowsException() {
        String[] args = new String[] {ATTEMPT_CHANGE_UPDATE, EXCEPTION_MESSAGE};

        doThrow(new RuntimeException(EXCEPTION_MESSAGE)).when(driverRepository).flush();

        assertThatExceptionOfType(DbModificationAttemptException.class)
                .isThrownBy(() -> validation.updateOrThrow(DRIVER, RATE_CHANGE_EVENT_RESPONSE))
                .withMessage(INVALID_ATTEMPT_CHANGE_DRIVER)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(driverRepository).flush();
    }

}
