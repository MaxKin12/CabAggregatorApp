package com.example.driverservice.unit.utility.validation.impl;

import static com.example.driverservice.configuration.constants.CarTestData.CAR;
import static com.example.driverservice.configuration.constants.CarTestData.CAR_ID;
import static com.example.driverservice.configuration.constants.CarTestData.CAR_REQUEST;
import static com.example.driverservice.configuration.constants.CarTestData.LIMIT;
import static com.example.driverservice.configuration.constants.CarTestData.LIMIT_CUT;
import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.ATTEMPT_CHANGE_CREATE;
import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.ATTEMPT_CHANGE_UPDATE;
import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.EXCEPTION_ARGS_FIELD;
import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.EXCEPTION_MESSAGE;
import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.EXCEPTION_MESSAGE_KEY_FIELD;
import static com.example.driverservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.CAR_NOT_FOUND;
import static com.example.driverservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.INVALID_ATTEMPT_CHANGE_CAR;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.Mockito.times;

import com.example.driverservice.configuration.properties.DriverServiceProperties;
import com.example.driverservice.dto.car.CarRequest;
import com.example.driverservice.exception.custom.DbModificationAttemptException;
import com.example.driverservice.exception.custom.EntityNotFoundException;
import com.example.driverservice.mapper.car.CarMapper;
import com.example.driverservice.model.entity.Car;
import com.example.driverservice.repository.CarRepository;
import com.example.driverservice.utility.validation.impl.CarServiceValidationImpl;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CarServiceValidationImplTest {

    @InjectMocks
    private CarServiceValidationImpl validation;

    @Mock
    private DriverServiceProperties properties;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarMapper carMapper;

    @Test
    void findByIdOrThrowTest_ValidId_ReturnsCar() {
        Long id = CAR_ID;
        Car car = CAR;

        when(carRepository.findById(id)).thenReturn(Optional.of(car));

        Car result = validation.findByIdOrThrow(id);

        assertThat(result).isNotNull().isEqualTo(car);
        verify(carRepository).findById(id);
    }

    @Test
    void findByIdOrThrowTest_InvalidId_ThrowsException() {
        Long id = CAR_ID;
        String[] args = new String[] {id.toString()};

        when(carRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> validation.findByIdOrThrow(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasFieldOrPropertyWithValue(EXCEPTION_MESSAGE_KEY_FIELD, CAR_NOT_FOUND)
                .hasFieldOrPropertyWithValue(EXCEPTION_ARGS_FIELD, args);

        verify(carRepository).findById(id);
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
    void saveOrThrowTest_ValidEntity_SavesCar() {
        Car car = CAR;

        when(carRepository.save(car)).thenReturn(car);

        Car result = validation.saveOrThrow(car);

        assertThat(result).isNotNull().isEqualTo(car);
        verify(carRepository).save(car);
    }

    @Test
    void saveOrThrowTest_InvalidEntity_ThrowsException() {
        Car car = CAR;
        String[] args = new String[] {ATTEMPT_CHANGE_CREATE, EXCEPTION_MESSAGE};

        when(carRepository.save(car)).thenThrow(new RuntimeException(EXCEPTION_MESSAGE));

        assertThatThrownBy(() -> validation.saveOrThrow(car))
                .isInstanceOf(DbModificationAttemptException.class)
                .hasFieldOrPropertyWithValue(EXCEPTION_MESSAGE_KEY_FIELD, INVALID_ATTEMPT_CHANGE_CAR)
                .hasFieldOrPropertyWithValue(EXCEPTION_ARGS_FIELD, args);

        verify(carRepository).save(car);
    }

    @Test
    void updateOrThrowTest_ValidAttemptToUpdateEntity_UpdatesCar() {
        Car car = CAR;
        CarRequest request = CAR_REQUEST;

        doNothing().when(carMapper).updateCarFromDto(request, car);
        doNothing().when(carRepository).flush();

        assertThatCode(() -> validation.updateOrThrow(car, request)).doesNotThrowAnyException();

        verify(carMapper).updateCarFromDto(request, car);
        verify(carRepository).flush();
    }

    @Test
    void updateOrThrowTest_InvalidAttemptToUpdateEntity_ThrowsException() {
        Car car = CAR;
        CarRequest request = CAR_REQUEST;
        String[] args = new String[] {ATTEMPT_CHANGE_UPDATE, EXCEPTION_MESSAGE};

        doThrow(new RuntimeException(EXCEPTION_MESSAGE))
                .when(carMapper).updateCarFromDto(request, car);

        assertThatThrownBy(() -> validation.updateOrThrow(car, request))
                .isInstanceOf(DbModificationAttemptException.class)
                .hasFieldOrPropertyWithValue(EXCEPTION_MESSAGE_KEY_FIELD, INVALID_ATTEMPT_CHANGE_CAR)
                .hasFieldOrPropertyWithValue(EXCEPTION_ARGS_FIELD, args);

        verify(carMapper).updateCarFromDto(request, car);
        verifyNoInteractions(carRepository);
    }

}
