package com.example.driverservice.service.impl;

import static com.example.driverservice.utility.constants.CarTestData.CAR;
import static com.example.driverservice.utility.constants.CarTestData.CAR_ID;
import static com.example.driverservice.utility.constants.CarTestData.CAR_PAGE;
import static com.example.driverservice.utility.constants.CarTestData.CAR_PAGE_RESPONSE;
import static com.example.driverservice.utility.constants.CarTestData.CAR_REQUEST;
import static com.example.driverservice.utility.constants.CarTestData.CAR_REQUEST_UPDATED;
import static com.example.driverservice.utility.constants.CarTestData.CAR_RESPONSE;
import static com.example.driverservice.utility.constants.CarTestData.CAR_RESPONSE_UPDATED;
import static com.example.driverservice.utility.constants.CarTestData.LIMIT;
import static com.example.driverservice.utility.constants.CarTestData.LIMIT_CUT;
import static com.example.driverservice.utility.constants.CarTestData.OFFSET;
import static com.example.driverservice.utility.constants.GeneralUtilityConstants.ATTEMPT_CHANGE_CREATE;
import static com.example.driverservice.utility.constants.GeneralUtilityConstants.ATTEMPT_CHANGE_UPDATE;
import static com.example.driverservice.utility.constants.GeneralUtilityConstants.EXCEPTION_MESSAGE;
import static com.example.driverservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.CAR_NOT_FOUND;
import static com.example.driverservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.INVALID_ATTEMPT_CHANGE_CAR;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.example.driverservice.dto.car.CarRequest;
import com.example.driverservice.dto.car.CarResponse;
import com.example.driverservice.dto.page.PageResponse;
import com.example.driverservice.exception.custom.DbModificationAttemptException;
import com.example.driverservice.exception.custom.EntityNotFoundException;
import com.example.driverservice.mapper.car.CarMapper;
import com.example.driverservice.mapper.car.CarPageMapper;
import com.example.driverservice.model.entity.Car;
import com.example.driverservice.repository.CarRepository;
import com.example.driverservice.utility.validation.CarServiceValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class CarServiceImplTest {

    @InjectMocks
    private CarServiceImpl carService;

    @Mock
    private CarServiceValidation validation;

    @Mock
    private CarRepository carRepository;

    @Mock
    private CarMapper carMapper;

    @Mock
    private CarPageMapper carPageMapper;

    @Test
    void findByIdTest_ValidId_ReturnsValidResponseEntity() {
        Long id = CAR_ID;
        Car car = CAR;
        CarResponse carResponse = CAR_RESPONSE;

        when(validation.findByIdOrThrow(id)).thenReturn(car);
        when(carMapper.toResponse(car)).thenReturn(carResponse);

        CarResponse result = carService.findById(id);

        assertNotNull(result);
        assertEquals(carResponse, result);
        verify(validation).findByIdOrThrow(id);
        verify(carMapper).toResponse(car);
    }

    @Test
    void findByIdTest_InvalidId_ThrowsException() {
        Long id = CAR_ID;
        String[] args = new String[] {id.toString()};

        when(validation.findByIdOrThrow(id))
                .thenThrow(new EntityNotFoundException(CAR_NOT_FOUND, args));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> carService.findById(id));

        assertEquals(CAR_NOT_FOUND, exception.getMessageKey());
        assertArrayEquals(args, exception.getArgs());
        verify(validation).findByIdOrThrow(id);
        verifyNoInteractions(carMapper);
    }

    @Test
    void findAllTest_UncutLimit_ReturnsValidResponseEntity() {
        int offset = OFFSET;
        int limit = LIMIT;
        PageRequest pageRequest = PageRequest.of(offset, limit);
        Page<Car> carPage = CAR_PAGE;
        PageResponse pageResponse = CAR_PAGE_RESPONSE;

        when(validation.cutDownLimit(limit)).thenReturn(limit);
        when(carRepository.findAll(pageRequest)).thenReturn(carPage);
        when(carPageMapper.toResponsePage(carPage, offset, limit)).thenReturn(pageResponse);

        PageResponse result = carService.findAll(offset, limit);

        assertNotNull(result);
        assertEquals(pageResponse, result);
        verify(validation).cutDownLimit(limit);
        verify(carRepository).findAll(pageRequest);
        verify(carPageMapper).toResponsePage(carPage, offset, limit);
    }

    @Test
    void findAllTest_CutLimit_ReturnsValidResponseEntity() {
        int offset = OFFSET;
        int limit = LIMIT;
        int limitCut = LIMIT_CUT;
        PageRequest pageRequest = PageRequest.of(offset, limitCut);
        Page<Car> carPage = CAR_PAGE;
        PageResponse pageResponse = CAR_PAGE_RESPONSE;

        when(validation.cutDownLimit(limit)).thenReturn(limitCut);
        when(carRepository.findAll(pageRequest)).thenReturn(carPage);
        when(carPageMapper.toResponsePage(carPage, offset, limitCut)).thenReturn(pageResponse);

        PageResponse result = carService.findAll(offset, limit);

        assertNotNull(result);
        assertEquals(pageResponse, result);
        verify(validation).cutDownLimit(limit);
        verify(carRepository).findAll(pageRequest);
        verify(carPageMapper).toResponsePage(carPage, offset, limitCut);
    }

    @Test
    void createTest_ValidRequestEntity_ReturnsValidResponseEntity() {
        CarRequest carRequest = CAR_REQUEST;
        Car car = CAR;
        CarResponse carResponse = CAR_RESPONSE;

        when(carMapper.toCar(carRequest)).thenReturn(car);
        when(validation.saveOrThrow(car)).thenReturn(car);
        when(carMapper.toResponse(car)).thenReturn(carResponse);

        CarResponse result = carService.create(carRequest);

        assertNotNull(result);
        assertEquals(carResponse, result);
        verify(carMapper).toCar(carRequest);
        verify(validation).saveOrThrow(car);
        verify(carMapper).toResponse(car);
    }

    @Test
    void createTest_InvalidAttemptToSaveEntity_ThrowsException() {
        CarRequest carRequest = CAR_REQUEST;
        Car car = CAR;
        String[] args = new String[] {ATTEMPT_CHANGE_CREATE, EXCEPTION_MESSAGE};

        when(carMapper.toCar(carRequest)).thenReturn(car);
        when(validation.saveOrThrow(car))
                .thenThrow(new DbModificationAttemptException(INVALID_ATTEMPT_CHANGE_CAR, args));

        DbModificationAttemptException exception = assertThrows(DbModificationAttemptException.class,
                () -> carService.create(carRequest));

        assertEquals(INVALID_ATTEMPT_CHANGE_CAR, exception.getMessageKey());
        assertArrayEquals(args, exception.getArgs());
        verify(carMapper).toCar(carRequest);
        verify(validation).saveOrThrow(car);
    }

    @Test
    void updateTest_ValidIdAndRequestEntity_ReturnsValidResponseEntity() {
        Long id = CAR_ID;
        CarRequest carRequest = CAR_REQUEST_UPDATED;
        Car car = CAR;
        CarResponse carResponseUpdated = CAR_RESPONSE_UPDATED;

        when(validation.findByIdOrThrow(id)).thenReturn(car);
        doNothing().when(validation).updateOrThrow(car, carRequest);
        when(carMapper.toResponse(car)).thenReturn(carResponseUpdated);

        CarResponse result = carService.update(carRequest, id);

        assertNotNull(result);
        assertEquals(carResponseUpdated, result);
        verify(validation).findByIdOrThrow(id);
        verify(validation).updateOrThrow(car, carRequest);
        verify(carMapper).toResponse(car);
    }

    @Test
    void updateTest_InvalidId_ThrowsEntityNotFoundException() {
        Long id = CAR_ID;
        String[] args = new String[] {id.toString()};

        when(validation.findByIdOrThrow(id))
                .thenThrow(new EntityNotFoundException(CAR_NOT_FOUND, args));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> carService.update(CAR_REQUEST_UPDATED, id));

        assertEquals(CAR_NOT_FOUND, exception.getMessageKey());
        assertArrayEquals(args, exception.getArgs());
        verify(validation).findByIdOrThrow(id);
        verifyNoInteractions(carMapper);
    }

    @Test
    void updateTest_ValidIdButInvalidAttemptToUpdateEntity_ThrowsDbModificationAttemptException() {
        Long id = CAR_ID;
        CarRequest carRequest = CAR_REQUEST_UPDATED;
        Car car = CAR;
        String[] args = new String[] {ATTEMPT_CHANGE_UPDATE, EXCEPTION_MESSAGE};

        when(validation.findByIdOrThrow(id)).thenReturn(car);
        doThrow(new DbModificationAttemptException(INVALID_ATTEMPT_CHANGE_CAR, args))
                .when(validation)
                .updateOrThrow(car, carRequest);

        DbModificationAttemptException exception = assertThrows(DbModificationAttemptException.class,
                () -> carService.update(carRequest, id));

        assertEquals(INVALID_ATTEMPT_CHANGE_CAR, exception.getMessageKey());
        assertArrayEquals(args, exception.getArgs());
        verify(validation).findByIdOrThrow(id);
        verify(validation).updateOrThrow(car, carRequest);
        verifyNoInteractions(carMapper);
    }

    @Test
    void deleteTest_ValidId_DeletesCar() {
        Long id = CAR_ID;

        when(validation.findByIdOrThrow(id)).thenReturn(CAR);
        doNothing().when(carRepository).deleteById(id);

        assertDoesNotThrow(() -> carService.delete(id));

        verify(validation).findByIdOrThrow(id);
        verify(carRepository).deleteById(id);
    }

    @Test
    void deleteTest_InvalidId_ThrowsException() {
        Long id = CAR_ID;
        String[] args = new String[] {id.toString()};

        when(validation.findByIdOrThrow(id)).thenThrow(new EntityNotFoundException(CAR_NOT_FOUND, args));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> carService.delete(id));

        assertEquals(CAR_NOT_FOUND, exception.getMessageKey());
        assertArrayEquals(args, exception.getArgs());
        verify(validation).findByIdOrThrow(id);
        verifyNoInteractions(carRepository);
    }

}
