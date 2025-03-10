package com.example.ridesservice.service.impl;

import static com.example.ridesservice.constants.RideTestData.INVALID_RIDE_ID;
import static com.example.ridesservice.constants.RideTestData.LIMIT;
import static com.example.ridesservice.constants.RideTestData.LIMIT_CUT;
import static com.example.ridesservice.constants.RideTestData.OFFSET;
import static com.example.ridesservice.constants.RideTestData.RIDE;
import static com.example.ridesservice.constants.RideTestData.RIDE_BOOKING_REQUEST;
import static com.example.ridesservice.constants.RideTestData.RIDE_ID;
import static com.example.ridesservice.constants.RideTestData.RIDE_PAGE;
import static com.example.ridesservice.constants.RideTestData.RIDE_PAGE_RESPONSE;
import static com.example.ridesservice.constants.RideTestData.RIDE_REQUEST;
import static com.example.ridesservice.constants.RideTestData.RIDE_RESPONSE;
import static com.example.ridesservice.constants.RideTestData.RIDE_SETTING_REQUEST;
import static com.example.ridesservice.constants.RideTestData.RIDE_STATUS_REQUEST;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.RIDE_NOT_FOUND;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.WRONG_STATUS_TRANSITION;
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

import com.example.ridesservice.dto.ride.request.RideBookingRequest;
import com.example.ridesservice.dto.ride.request.RideDriverSettingRequest;
import com.example.ridesservice.dto.ride.request.RideRequest;
import com.example.ridesservice.dto.ride.request.RideStatusRequest;
import com.example.ridesservice.dto.ride.response.RidePageResponse;
import com.example.ridesservice.dto.ride.response.RideResponse;
import com.example.ridesservice.enums.PersonType;
import com.example.ridesservice.exception.custom.RideNotFoundException;
import com.example.ridesservice.exception.custom.WrongStatusTransitionException;
import com.example.ridesservice.mapper.RideBookingMapper;
import com.example.ridesservice.mapper.RideMapper;
import com.example.ridesservice.mapper.RidePageMapper;
import com.example.ridesservice.model.QueueRide;
import com.example.ridesservice.model.Ride;
import com.example.ridesservice.repository.RideRepository;
import com.example.ridesservice.service.QueueRideService;
import com.example.ridesservice.utility.pricecounter.PriceCounter;
import com.example.ridesservice.utility.validation.RideServiceValidation;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class RideServiceImplTest {

    @InjectMocks
    private RideServiceImpl rideService;

    @Mock
    private RideServiceValidation validation;

    @Mock
    private RideRepository rideRepository;

    @Mock
    private RideMapper rideMapper;

    @Mock
    private RideBookingMapper rideBookingMapper;

    @Mock
    private RidePageMapper ridePageMapper;

    @Mock
    private PriceCounter priceCounter;

    @Mock
    private QueueRideService queueRideService;

    @Test
    void findByIdTest_ValidId_ReturnsValidResponseEntity() {
        Long id = RIDE_ID;
        Ride ride = RIDE;
        RideResponse rideResponse = RIDE_RESPONSE;

        when(validation.findByIdOrThrow(id)).thenReturn(ride);
        when(rideMapper.toResponse(ride)).thenReturn(rideResponse);

        RideResponse result = rideService.findById(id);

        assertNotNull(result);
        assertEquals(rideResponse, result);
        verify(validation).findByIdOrThrow(id);
        verify(rideMapper).toResponse(ride);
    }

    @Test
    void findByIdTest_InvalidId_ThrowsException() {
        Long id = INVALID_RIDE_ID;
        String[] args = new String[]{id.toString()};

        when(validation.findByIdOrThrow(id))
                .thenThrow(new RideNotFoundException(RIDE_NOT_FOUND, args));

        RideNotFoundException exception = assertThrows(RideNotFoundException.class,
                () -> rideService.findById(id));

        assertEquals(RIDE_NOT_FOUND, exception.getMessageKey());
        assertArrayEquals(args, exception.getArgs());
        verify(validation).findByIdOrThrow(id);
        verifyNoInteractions(rideMapper);
    }

    @Test
    void findAllTest_UncutLimit_ReturnsValidResponseEntity() {
        int offset = OFFSET;
        int limit = LIMIT;
        Page<Ride> ridePage = RIDE_PAGE;
        RidePageResponse ridePageResponse = RIDE_PAGE_RESPONSE;

        when(validation.cutDownLimit(limit)).thenReturn(limit);
        when(rideRepository.findAll(PageRequest.of(offset, limit))).thenReturn(ridePage);
        when(ridePageMapper.toResponsePage(ridePage, offset, limit)).thenReturn(ridePageResponse);

        RidePageResponse result = rideService.findAll(offset, limit);

        assertNotNull(result);
        assertEquals(ridePageResponse, result);
        verify(validation).cutDownLimit(limit);
        verify(rideRepository).findAll(PageRequest.of(offset, limit));
        verify(ridePageMapper).toResponsePage(ridePage, offset, limit);
    }

    @Test
    void findLastPersonRidesTest_ValidIdAndPersonType_ReturnsValidResponseEntity() {
        int offset = OFFSET;
        int limit = LIMIT;
        int limitCut = LIMIT_CUT;
        Long personId = RIDE_ID;
        PersonType personType = PersonType.PASSENGER;
        Page<Ride> ridePage = RIDE_PAGE;
        RidePageResponse ridePageResponse = RIDE_PAGE_RESPONSE;

        when(validation.cutDownLimit(limit)).thenReturn(limitCut);
        when(validation.findLastRidesPage(personId, personType, limitCut)).thenReturn(ridePage);
        when(ridePageMapper.toResponsePage(ridePage, offset, limitCut)).thenReturn(ridePageResponse);

        RidePageResponse result = rideService.findLastPersonRides(personId, limit, personType);

        assertNotNull(result);
        assertEquals(ridePageResponse, result);
        verify(validation).cutDownLimit(limit);
        verify(validation).findLastRidesPage(personId, personType, limitCut);
        verify(ridePageMapper).toResponsePage(ridePage, offset, limitCut);
    }

    @Test
    void createTest_ValidRequestEntity_ReturnsValidResponseEntity() {
        RideRequest rideRequest = RIDE_REQUEST;
        Ride ride = RIDE;
        RideResponse rideResponse = RIDE_RESPONSE;
        BigDecimal price = BigDecimal.valueOf(17.5);

        doNothing().when(validation).checkPassengerExistence(rideRequest.passengerId());
        doNothing().when(validation).checkDriverExistenceAndCarOwning(rideRequest.driverId(), rideRequest.carId());
        when(priceCounter.count(rideRequest.pickUpAddress(), rideRequest.destinationAddress())).thenReturn(price);
        when(rideMapper.toRide(rideRequest, price)).thenReturn(ride);
        when(validation.saveOrThrow(ride)).thenReturn(ride);
        when(rideMapper.toResponse(ride)).thenReturn(rideResponse);

        RideResponse result = rideService.create(rideRequest);

        assertNotNull(result);
        assertEquals(rideResponse, result);
        verify(validation).checkPassengerExistence(rideRequest.passengerId());
        verify(validation).checkDriverExistenceAndCarOwning(rideRequest.driverId(), rideRequest.carId());
        verify(priceCounter).count(rideRequest.pickUpAddress(), rideRequest.destinationAddress());
        verify(rideMapper).toRide(rideRequest, price);
        verify(validation).saveOrThrow(ride);
        verify(rideMapper).toResponse(ride);
    }

    @Test
    void createTest_InvalidPassengerId_ThrowsException() {
        RideRequest rideRequest = RIDE_REQUEST;

        doThrow(new RuntimeException())
                .when(validation).checkPassengerExistence(rideRequest.passengerId());

        assertThrows(RuntimeException.class, () -> rideService.create(rideRequest));

        verify(validation).checkPassengerExistence(rideRequest.passengerId());
        verifyNoInteractions(priceCounter, rideMapper, rideRepository);
    }

    @Test
    void createTest_InvalidDriverId_ThrowsException() {
        RideRequest rideRequest = RIDE_REQUEST;

        doNothing().when(validation).checkPassengerExistence(rideRequest.passengerId());
        doThrow(new RuntimeException())
                .when(validation).checkDriverExistenceAndCarOwning(rideRequest.driverId(), rideRequest.carId());

        assertThrows(RuntimeException.class, () -> rideService.create(rideRequest));

        verify(validation).checkPassengerExistence(rideRequest.passengerId());
        verify(validation).checkDriverExistenceAndCarOwning(rideRequest.driverId(), rideRequest.carId());
        verifyNoInteractions(priceCounter, rideMapper, rideRepository);
    }

    @Test
    void createTest_InvalidCarId_ThrowsException() {
        RideRequest rideRequest = RIDE_REQUEST;

        doNothing().when(validation).checkPassengerExistence(rideRequest.passengerId());
        doThrow(new RuntimeException())
                .when(validation).checkDriverExistenceAndCarOwning(rideRequest.driverId(), rideRequest.carId());

        assertThrows(RuntimeException.class, () -> rideService.create(rideRequest));

        verify(validation).checkPassengerExistence(rideRequest.passengerId());
        verify(validation).checkDriverExistenceAndCarOwning(rideRequest.driverId(), rideRequest.carId());
        verifyNoInteractions(priceCounter, rideMapper, rideRepository);
    }

    @Test
    void bookRideTest_ValidRequestEntity_ReturnsValidResponseEntity() {
        RideBookingRequest rideRequest = RIDE_BOOKING_REQUEST;
        Ride ride = RIDE;
        RideResponse rideResponse = RIDE_RESPONSE;
        BigDecimal price = BigDecimal.valueOf(17.5);

        doNothing().when(validation).checkPassengerExistence(rideRequest.passengerId());
        when(priceCounter.count(rideRequest.pickUpAddress(), rideRequest.destinationAddress())).thenReturn(price);
        when(rideBookingMapper.toRide(rideRequest, price)).thenReturn(ride);
        when(validation.saveOrThrow(ride)).thenReturn(ride);
        doNothing().when(queueRideService).append(ride);
        when(rideMapper.toResponse(ride)).thenReturn(rideResponse);

        RideResponse result = rideService.bookRide(rideRequest);

        assertNotNull(result);
        assertEquals(rideResponse, result);
        verify(validation).checkPassengerExistence(rideRequest.passengerId());
        verify(priceCounter).count(rideRequest.pickUpAddress(), rideRequest.destinationAddress());
        verify(rideBookingMapper).toRide(rideRequest, price);
        verify(validation).saveOrThrow(ride);
        verify(queueRideService).append(ride);
        verify(rideMapper).toResponse(ride);
    }

    @Test
    void bookRideTest_InvalidPassengerId_ThrowsException() {
        RideBookingRequest rideRequest = RIDE_BOOKING_REQUEST;

        doThrow(new RuntimeException())
                .when(validation).checkPassengerExistence(rideRequest.passengerId());

        assertThrows(RuntimeException.class, () -> rideService.bookRide(rideRequest));

        verify(validation).checkPassengerExistence(rideRequest.passengerId());
        verifyNoInteractions(priceCounter, rideBookingMapper, rideRepository, queueRideService);
    }

    @Test
    void updateTest_ValidIdAndRequestEntity_ReturnsValidResponseEntity() {
        Long id = RIDE_ID;
        RideRequest rideRequest = RIDE_REQUEST;
        Ride ride = RIDE;
        RideResponse rideResponse = RIDE_RESPONSE;

        when(validation.findByIdOrThrow(id)).thenReturn(ride);
        doNothing().when(validation).checkPassengerExistence(rideRequest.passengerId());
        doNothing().when(validation).checkCarExistence(rideRequest.carId());
        doNothing().when(validation).checkDriverExistenceAndCarOwning(rideRequest.driverId(), rideRequest.carId());
        doNothing().when(validation).updateOrThrow(ride, rideRequest);
        when(rideMapper.toResponse(ride)).thenReturn(rideResponse);

        RideResponse result = rideService.update(rideRequest, id);

        assertNotNull(result);
        assertEquals(rideResponse, result);
        verify(validation).findByIdOrThrow(id);
        verify(validation).checkPassengerExistence(rideRequest.passengerId());
        verify(validation).checkCarExistence(rideRequest.carId());
        verify(validation).checkDriverExistenceAndCarOwning(rideRequest.driverId(), rideRequest.carId());
        verify(validation).updateOrThrow(ride, rideRequest);
        verify(rideMapper).toResponse(ride);
    }

    @Test
    void updateTest_InvalidRideId_ThrowsException() {
        Long id = INVALID_RIDE_ID;
        String[] args = new String[]{id.toString()};

        when(validation.findByIdOrThrow(id))
                .thenThrow(new RideNotFoundException(RIDE_NOT_FOUND, args));

        RideNotFoundException exception = assertThrows(RideNotFoundException.class,
                () -> rideService.update(RIDE_REQUEST, id));

        assertEquals(RIDE_NOT_FOUND, exception.getMessageKey());
        assertArrayEquals(args, exception.getArgs());
        verify(validation).findByIdOrThrow(id);
        verifyNoInteractions(rideMapper);
    }

    @Test
    void updateTest_InvalidPassengerId_ThrowsException() {
        Long id = RIDE_ID;
        RideRequest rideRequest = RIDE_REQUEST;

        when(validation.findByIdOrThrow(id)).thenReturn(RIDE);
        doThrow(new RuntimeException())
                .when(validation).checkPassengerExistence(rideRequest.passengerId());

        assertThrows(RuntimeException.class, () -> rideService.update(rideRequest, id));

        verify(validation).findByIdOrThrow(id);
        verify(validation).checkPassengerExistence(rideRequest.passengerId());
        verifyNoInteractions(rideMapper);
    }

    @Test
    void updateTest_InvalidDriverId_ThrowsException() {
        Long id = RIDE_ID;
        RideRequest rideRequest = RIDE_REQUEST;

        when(validation.findByIdOrThrow(id)).thenReturn(RIDE);
        doNothing().when(validation).checkPassengerExistence(rideRequest.passengerId());
        doThrow(new RuntimeException())
                .when(validation).checkDriverExistenceAndCarOwning(rideRequest.driverId(), rideRequest.carId());

        assertThrows(RuntimeException.class, () -> rideService.update(rideRequest, id));

        verify(validation).findByIdOrThrow(id);
        verify(validation).checkPassengerExistence(rideRequest.passengerId());
        verify(validation).checkDriverExistenceAndCarOwning(rideRequest.driverId(), rideRequest.carId());
        verifyNoInteractions(rideMapper);
    }

    @Test
    void updateTest_InvalidCarId_ThrowsException() {
        Long id = RIDE_ID;
        RideRequest rideRequest = RIDE_REQUEST;

        when(validation.findByIdOrThrow(id)).thenReturn(RIDE);
        doNothing().when(validation).checkPassengerExistence(rideRequest.passengerId());
        doThrow(new RuntimeException())
                .when(validation).checkDriverExistenceAndCarOwning(rideRequest.driverId(), rideRequest.carId());

        assertThrows(RuntimeException.class, () -> rideService.update(rideRequest, id));

        verify(validation).findByIdOrThrow(id);
        verify(validation).checkPassengerExistence(rideRequest.passengerId());
        verify(validation).checkDriverExistenceAndCarOwning(rideRequest.driverId(), rideRequest.carId());
        verifyNoInteractions(rideMapper);
    }

    @Test
    void setDriverToRideTest_ValidRequestEntity_ReturnsValidResponseEntity() {
        RideDriverSettingRequest rideRequest = RIDE_SETTING_REQUEST;
        Ride ride = RIDE;
        RideResponse rideResponse = RIDE_RESPONSE;

        doNothing().when(validation).checkDriverExistenceAndCarOwning(rideRequest.driverId(), rideRequest.carId());
        when(queueRideService.popRide()).thenReturn(QueueRide.builder()
                .id(1L)
                .rideId(ride.getId())
                .changedAt(LocalDateTime.now())
                .build());
        when(validation.findByIdOrThrow(RIDE_ID)).thenReturn(ride);
        doNothing().when(validation).updateDriverOrThrow(ride, rideRequest);
        when(rideMapper.toResponse(ride)).thenReturn(rideResponse);

        RideResponse result = rideService.setDriverToRide(rideRequest);

        assertNotNull(result);
        assertEquals(rideResponse, result);
        verify(validation).checkDriverExistenceAndCarOwning(rideRequest.driverId(), rideRequest.carId());
        verify(queueRideService).popRide();
        verify(validation).findByIdOrThrow(RIDE_ID);
        verify(validation).updateDriverOrThrow(ride, rideRequest);
        verify(rideMapper).toResponse(ride);
    }

    @Test
    void updateStatusTest_ValidIdAndRequestEntity_ReturnsValidResponseEntity() {
        Long id = RIDE_ID;
        RideStatusRequest rideRequest = RIDE_STATUS_REQUEST;
        Ride ride = RIDE;
        RideResponse rideResponse = RIDE_RESPONSE;

        when(validation.findByIdOrThrow(id)).thenReturn(ride);
        doNothing().when(validation).checkStatusTransitionAllowed(ride, rideRequest);
        doNothing().when(validation).updateStatusOrThrow(ride, rideRequest);
        when(rideMapper.toResponse(ride)).thenReturn(rideResponse);

        RideResponse result = rideService.updateStatus(rideRequest, id);

        assertNotNull(result);
        assertEquals(rideResponse, result);
        verify(validation).findByIdOrThrow(id);
        verify(validation).checkStatusTransitionAllowed(ride, rideRequest);
        verify(validation).updateStatusOrThrow(ride, rideRequest);
        verify(rideMapper).toResponse(ride);
    }

    @Test
    void updateStatusTest_InvalidStatusTransition_ThrowsException() {
        Long id = RIDE_ID;
        RideStatusRequest rideRequest = RIDE_STATUS_REQUEST;
        String[] args = new String[]{rideRequest.status()};

        when(validation.findByIdOrThrow(id)).thenReturn(RIDE);
        doThrow(new WrongStatusTransitionException(WRONG_STATUS_TRANSITION, args))
                .when(validation).checkStatusTransitionAllowed(RIDE, rideRequest);

        WrongStatusTransitionException exception = assertThrows(WrongStatusTransitionException.class,
                () -> rideService.updateStatus(rideRequest, id));

        assertEquals(WRONG_STATUS_TRANSITION, exception.getMessageKey());
        assertArrayEquals(args, exception.getArgs());
        verify(validation).findByIdOrThrow(id);
        verify(validation).checkStatusTransitionAllowed(RIDE, rideRequest);
        verifyNoInteractions(rideMapper);
    }

    @Test
    void deleteTest_ValidId_DeletesRide() {
        Long id = RIDE_ID;

        when(validation.findByIdOrThrow(id)).thenReturn(RIDE);
        doNothing().when(rideRepository).deleteById(id);

        assertDoesNotThrow(() -> rideService.delete(id));

        verify(validation).findByIdOrThrow(id);
        verify(rideRepository).deleteById(id);
    }

    @Test
    void deleteTest_InvalidId_ThrowsException() {
        Long id = INVALID_RIDE_ID;
        String[] args = new String[]{id.toString()};

        when(validation.findByIdOrThrow(id))
                .thenThrow(new RideNotFoundException(RIDE_NOT_FOUND, args));

        RideNotFoundException exception = assertThrows(RideNotFoundException.class,
                () -> rideService.delete(id));

        assertEquals(RIDE_NOT_FOUND, exception.getMessageKey());
        assertArrayEquals(args, exception.getArgs());
        verify(validation).findByIdOrThrow(id);
        verifyNoInteractions(rideRepository);
    }
    
}
