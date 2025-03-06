package com.example.driverservice.service.impl;

import static com.example.driverservice.utility.constants.DriverTestData.DRIVER;
import static com.example.driverservice.utility.constants.DriverTestData.DRIVER_ID;
import static com.example.driverservice.utility.constants.DriverTestData.DRIVER_PAGE;
import static com.example.driverservice.utility.constants.DriverTestData.DRIVER_PAGE_RESPONSE;
import static com.example.driverservice.utility.constants.DriverTestData.DRIVER_REQUEST;
import static com.example.driverservice.utility.constants.DriverTestData.DRIVER_RESPONSE;
import static com.example.driverservice.utility.constants.DriverTestData.LIMIT;
import static com.example.driverservice.utility.constants.DriverTestData.LIMIT_CUT;
import static com.example.driverservice.utility.constants.DriverTestData.OFFSET;
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
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.example.driverservice.dto.driver.DriverRequest;
import com.example.driverservice.dto.driver.DriverResponse;
import com.example.driverservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.driverservice.dto.page.PageResponse;
import com.example.driverservice.exception.custom.DbModificationAttemptException;
import com.example.driverservice.exception.custom.EntityNotFoundException;
import com.example.driverservice.mapper.driver.DriverMapper;
import com.example.driverservice.mapper.driver.DriverPageMapper;
import com.example.driverservice.model.entity.Driver;
import com.example.driverservice.repository.DriverRepository;
import com.example.driverservice.utility.validation.DriverServiceValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class DriverServiceImplTest {

    @InjectMocks
    private DriverServiceImpl driverService;

    @Mock
    private DriverServiceValidation validation;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private DriverMapper driverMapper;

    @Mock
    private DriverPageMapper driverPageMapper;

    @Test
    void findByIdTest_ValidId_ReturnsDriverResponse() {
        Long id = DRIVER_ID;
        Driver driver = DRIVER;
        driver.setId(id);
        DriverResponse driverResponse = DRIVER_RESPONSE;

        when(validation.findByIdOrThrow(id)).thenReturn(driver);
        when(driverMapper.toResponse(driver)).thenReturn(driverResponse);

        DriverResponse result = driverService.findById(id);

        assertNotNull(result);
        assertEquals(driverResponse, result);
        verify(validation).findByIdOrThrow(id);
        verify(driverMapper).toResponse(driver);
    }

    @Test
    void findByIdTest_InvalidId_ThrowsException() {
        Long id = DRIVER_ID;
        String[] args = new String[] {id.toString()};

        when(validation.findByIdOrThrow(id))
                .thenThrow(new EntityNotFoundException(DRIVER_NOT_FOUND, args));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> driverService.findById(id));

        assertEquals(DRIVER_NOT_FOUND, exception.getMessageKey());
        assertArrayEquals(args, exception.getArgs());
        verify(validation).findByIdOrThrow(id);
        verifyNoInteractions(driverMapper);
    }

    @Test
    void findAllTest_UncutLimit_ReturnsValidResponseEntity() {
        int offset = OFFSET;
        int limit = LIMIT;
        PageRequest pageRequest = PageRequest.of(offset, limit);
        Page<Driver> driverPage = DRIVER_PAGE;
        PageResponse pageResponse = DRIVER_PAGE_RESPONSE;

        when(validation.cutDownLimit(limit)).thenReturn(limit);
        when(driverRepository.findAll(pageRequest)).thenReturn(driverPage);
        when(driverPageMapper.toResponsePage(driverPage, offset, limit)).thenReturn(pageResponse);

        PageResponse result = driverService.findAll(offset, limit);

        assertNotNull(result);
        assertEquals(pageResponse, result);
        verify(validation).cutDownLimit(limit);
        verify(driverRepository).findAll(pageRequest);
        verify(driverPageMapper).toResponsePage(driverPage, offset, limit);
    }

    @Test
    void findAllTest_CutLimit_ReturnsValidResponseEntity() {
        int offset = OFFSET;
        int limit = LIMIT;
        int limitCut = LIMIT_CUT;
        PageRequest pageRequest = PageRequest.of(offset, limitCut);
        Page<Driver> driverPage = DRIVER_PAGE;
        PageResponse pageResponse = DRIVER_PAGE_RESPONSE;

        when(validation.cutDownLimit(limit)).thenReturn(limitCut);
        when(driverRepository.findAll(pageRequest)).thenReturn(driverPage);
        when(driverPageMapper.toResponsePage(driverPage, offset, limitCut)).thenReturn(pageResponse);

        PageResponse result = driverService.findAll(offset, limit);

        assertNotNull(result);
        assertEquals(pageResponse, result);
        verify(validation).cutDownLimit(limit);
        verify(driverRepository).findAll(pageRequest);
        verify(driverPageMapper).toResponsePage(driverPage, offset, limitCut);
    }

    @Test
    void createTest_ValidRequestEntity_ReturnsValidResponseResponse() {
        DriverRequest driverRequest = DRIVER_REQUEST;
        Driver driver = DRIVER;
        DriverResponse driverResponse = DRIVER_RESPONSE;

        when(driverMapper.toDriver(driverRequest)).thenReturn(driver);
        when(validation.saveOrThrow(driver)).thenReturn(driver);
        when(driverMapper.toResponse(driver)).thenReturn(driverResponse);

        DriverResponse result = driverService.create(driverRequest);

        assertNotNull(result);
        assertEquals(driverResponse, result);
        verify(driverMapper).toDriver(driverRequest);
        verify(validation).saveOrThrow(driver);
        verify(driverMapper).toResponse(driver);
    }

    @Test
    void createTest_InvalidAttemptToSaveEntity_ThrowsException() {
        DriverRequest driverRequest = DRIVER_REQUEST;
        Driver driver = DRIVER;
        String[] args = new String[] {ATTEMPT_CHANGE_UPDATE, EXCEPTION_MESSAGE};

        when(driverMapper.toDriver(driverRequest)).thenReturn(driver);
        when(validation.saveOrThrow(driver))
                .thenThrow(new DbModificationAttemptException(INVALID_ATTEMPT_CHANGE_DRIVER, args));

        DbModificationAttemptException exception = assertThrows(DbModificationAttemptException.class,
                () -> driverService.create(driverRequest));

        assertEquals(INVALID_ATTEMPT_CHANGE_DRIVER, exception.getMessageKey());
        assertArrayEquals(args, exception.getArgs());
        verify(driverMapper).toDriver(driverRequest);
        verify(validation).saveOrThrow(driver);
    }

    @Test
    void updateDriverTest_ValidIdAndRequestEntity_ReturnsValidResponse() {
        Long id = DRIVER_ID;
        DriverRequest driverRequest = DRIVER_REQUEST;
        Driver driver = DRIVER;
        DriverResponse updatedDriverResponse = DRIVER_RESPONSE;

        when(validation.findByIdOrThrow(id)).thenReturn(driver);
        doNothing().when(validation).updateOrThrow(driver, driverRequest);
        when(driverMapper.toResponse(driver)).thenReturn(updatedDriverResponse);

        DriverResponse result = driverService.updateDriver(driverRequest, id);

        assertNotNull(result);
        assertEquals(updatedDriverResponse, result);
        verify(validation).findByIdOrThrow(id);
        verify(validation).updateOrThrow(driver, driverRequest);
        verify(driverMapper).toResponse(driver);
    }

    @Test
    void updateDriverTest_InvalidId_ThrowsEntityNotFoundException() {
        Long id = DRIVER_ID;
        String[] args = new String[] {id.toString()};

        when(validation.findByIdOrThrow(id))
                .thenThrow(new EntityNotFoundException(DRIVER_NOT_FOUND, args));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> driverService.updateDriver(DRIVER_REQUEST, id));

        assertEquals(DRIVER_NOT_FOUND, exception.getMessageKey());
        assertArrayEquals(args, exception.getArgs());
        verify(validation).findByIdOrThrow(id);
        verifyNoInteractions(driverMapper);
    }

    @Test
    void updatePassengerTest_ValidIdButInvalidAttemptToUpdateEntity_ThrowsDbModificationAttemptException() {
        Long id = DRIVER_ID;
        DriverRequest driverRequest = DRIVER_REQUEST;
        Driver driver = DRIVER;
        String[] args = new String[] {ATTEMPT_CHANGE_UPDATE, EXCEPTION_MESSAGE};

        when(validation.findByIdOrThrow(id)).thenReturn(driver);
        doThrow(new DbModificationAttemptException(INVALID_ATTEMPT_CHANGE_DRIVER, args))
                .when(validation)
                .updateOrThrow(driver, driverRequest);

        DbModificationAttemptException exception = assertThrows(DbModificationAttemptException.class,
                () -> driverService.updateDriver(driverRequest, id));

        assertEquals(INVALID_ATTEMPT_CHANGE_DRIVER, exception.getMessageKey());
        assertArrayEquals(args, exception.getArgs());
        verify(validation).findByIdOrThrow(id);
        verify(validation).updateOrThrow(driver, driverRequest);
        verifyNoInteractions(driverMapper);
    }

    @Test
    void updateRateTest_ValidChangeEvent_UpdatesDriverRate() {
        RateChangeEventResponse event = RATE_CHANGE_EVENT_RESPONSE;

        when(validation.findByIdOrThrow(event.recipientId())).thenReturn(DRIVER);

        assertDoesNotThrow(() -> driverService.updateRate(event));

        assertEquals(event.rate(), DRIVER.getRate());
        verify(validation).findByIdOrThrow(event.recipientId());
    }

    @Test
    void updateRateTest_IdInChangeEventInvalid_ThrowsEntityNotFoundException() {
        RateChangeEventResponse event = RATE_CHANGE_EVENT_RESPONSE;
        String[] args = new String[] {event.recipientId().toString()};

        when(validation.findByIdOrThrow(event.recipientId()))
                .thenThrow(new EntityNotFoundException(DRIVER_NOT_FOUND, args));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> driverService.updateRate(event));

        assertEquals(DRIVER_NOT_FOUND, exception.getMessageKey());
        assertArrayEquals(args, exception.getArgs());
        verify(validation).findByIdOrThrow(event.recipientId());
    }

    @Test
    void deleteTest_ValidId_DeletesDriver() {
        Long id = DRIVER_ID;

        when(validation.findByIdOrThrow(id)).thenReturn(DRIVER);
        doNothing().when(driverRepository).deleteById(id);

        assertDoesNotThrow(() -> driverService.delete(id));

        verify(validation).findByIdOrThrow(id);
        verify(driverRepository).deleteById(id);
    }

    @Test
    void deleteTest_InvalidId_ThrowsException() {
        Long id = DRIVER_ID;
        String[] args = new String[] {id.toString()};

        when(validation.findByIdOrThrow(id)).thenThrow(new EntityNotFoundException(DRIVER_NOT_FOUND, args));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> driverService.delete(id));

        assertEquals(DRIVER_NOT_FOUND, exception.getMessage());
        assertArrayEquals(args, exception.getArgs());
        verify(validation).findByIdOrThrow(id);
        verifyNoInteractions(driverRepository);
    }

}
