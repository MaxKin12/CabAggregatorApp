package com.example.driverservice.unit.utility.validation.impl;

import static com.example.driverservice.configuration.constants.DriverTestData.DRIVER;
import static com.example.driverservice.configuration.constants.DriverTestData.DRIVER_ID;
import static com.example.driverservice.configuration.constants.DriverTestData.DRIVER_REQUEST;
import static com.example.driverservice.configuration.constants.DriverTestData.LIMIT;
import static com.example.driverservice.configuration.constants.DriverTestData.LIMIT_CUT;
import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.ATTEMPT_CHANGE_CREATE;
import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.ATTEMPT_CHANGE_UPDATE;
import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.EXCEPTION_MESSAGE;
import static com.example.driverservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.DRIVER_NOT_FOUND;
import static com.example.driverservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.INVALID_ATTEMPT_CHANGE_DRIVER;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.Mockito.times;

import com.example.driverservice.configuration.properties.DriverServiceProperties;
import com.example.driverservice.dto.driver.DriverRequest;
import com.example.driverservice.exception.custom.DbModificationAttemptException;
import com.example.driverservice.exception.custom.EntityNotFoundException;
import com.example.driverservice.mapper.driver.DriverMapper;
import com.example.driverservice.model.entity.Driver;
import com.example.driverservice.repository.DriverRepository;
import com.example.driverservice.utility.validation.impl.DriverServiceValidationImpl;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class DriverServiceValidationImplTest {

    @InjectMocks
    private DriverServiceValidationImpl validation;

    @Mock
    private DriverServiceProperties properties;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private DriverMapper driverMapper;

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
    void saveOrThrowTest_ValidEntity_SavesDriver() {
        Driver driver = DRIVER;

        when(driverRepository.save(driver)).thenReturn(driver);

        Driver result = validation.saveOrThrow(driver);

        assertThat(result).isNotNull().isEqualTo(driver);
        verify(driverRepository).save(driver);
    }

    @Test
    void saveOrThrowTest_InvalidEntity_ThrowsException() {
        Driver driver = DRIVER;
        String[] args = new String[] {ATTEMPT_CHANGE_CREATE, EXCEPTION_MESSAGE};

        when(driverRepository.save(driver)).thenThrow(new RuntimeException(EXCEPTION_MESSAGE));

        assertThatExceptionOfType(DbModificationAttemptException.class)
                .isThrownBy(() -> validation.saveOrThrow(driver))
                .withMessage(INVALID_ATTEMPT_CHANGE_DRIVER)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(driverRepository).save(driver);
    }

    @Test
    void updateOrThrowTest_ValidAttemptToUpdateEntity_UpdatesDriver() {
        Driver driver = DRIVER;
        DriverRequest request = DRIVER_REQUEST;

        doNothing().when(driverMapper).updateDriverFromDto(request, driver);
        doNothing().when(driverRepository).flush();

        assertThatCode(() -> validation.updateOrThrow(driver, request)).doesNotThrowAnyException();

        verify(driverMapper).updateDriverFromDto(request, driver);
        verify(driverRepository).flush();
    }

    @Test
    void updateOrThrowTest_InvalidAttemptToUpdateEntity_ThrowsException() {
        Driver driver = DRIVER;
        DriverRequest request = DRIVER_REQUEST;
        String[] args = new String[] {ATTEMPT_CHANGE_UPDATE, EXCEPTION_MESSAGE};

        doThrow(new RuntimeException(EXCEPTION_MESSAGE))
                .when(driverMapper).updateDriverFromDto(request, driver);

        assertThatExceptionOfType(DbModificationAttemptException.class)
                .isThrownBy(() -> validation.updateOrThrow(driver, request))
                .withMessage(INVALID_ATTEMPT_CHANGE_DRIVER)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(driverMapper).updateDriverFromDto(request, driver);
        verifyNoInteractions(driverRepository);
    }

}
