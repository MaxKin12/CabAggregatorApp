package com.example.ridesservice.utility.validation.impl;

import static com.example.ridesservice.constants.RideTestData.LIMIT;
import static com.example.ridesservice.constants.RideTestData.OFFSET;
import static com.example.ridesservice.constants.RideTestData.RIDE;
import static com.example.ridesservice.constants.RideTestData.RIDE_ID;
import static com.example.ridesservice.constants.RideTestData.RIDE_REQUEST;
import static com.example.ridesservice.constants.RideTestData.RIDE_SETTING_REQUEST;
import static com.example.ridesservice.constants.RideTestData.RIDE_STATUS_REQUEST;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.ridesservice.client.DriverClient;
import com.example.ridesservice.client.PassengerClient;
import com.example.ridesservice.client.dto.DriverResponse;
import com.example.ridesservice.client.exception.DriverNotContainsCarException;
import com.example.ridesservice.configuration.properties.RideServiceProperties;
import com.example.ridesservice.dto.ride.request.RideDriverSettingRequest;
import com.example.ridesservice.dto.ride.request.RideRequest;
import com.example.ridesservice.dto.ride.request.RideStatusRequest;
import com.example.ridesservice.enums.PersonType;
import com.example.ridesservice.enums.RideStatus;
import com.example.ridesservice.exception.custom.DbModificationAttemptException;
import com.example.ridesservice.exception.custom.RideNotFoundException;
import com.example.ridesservice.mapper.RideDriverSettingMapper;
import com.example.ridesservice.mapper.RideMapper;
import com.example.ridesservice.mapper.RideStatusMapper;
import com.example.ridesservice.model.Ride;
import com.example.ridesservice.repository.RideRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class RideServiceValidationImplTest {

    @Mock
    private RideServiceProperties properties;

    @Mock
    private RideRepository rideRepository;

    @Mock
    private RideMapper rideMapper;

    @Mock
    private RideDriverSettingMapper rideDriverSettingMapper;

    @Mock
    private RideStatusMapper rideStatusMapper;

    @Mock
    private PassengerClient passengerClient;

    @Mock
    private DriverClient driverClient;

    @InjectMocks
    private RideServiceValidationImpl rideServiceValidation;

    @Test
    void cutDownLimitTest_LimitExceedsMax_ReturnsMaxLimit() {
        int limit = 1000;
        int maxLimit = 100;
        when(properties.maxPageLimit()).thenReturn(maxLimit);

        int result = rideServiceValidation.cutDownLimit(limit);

        assertEquals(maxLimit, result);
        verify(properties).maxPageLimit();
    }

    @Test
    void cutDownLimitTest_LimitWithinRange_ReturnsSameLimit() {
        int limit = 50;
        int maxLimit = 100;
        when(properties.maxPageLimit()).thenReturn(maxLimit);

        int result = rideServiceValidation.cutDownLimit(limit);

        assertEquals(limit, result);
        verify(properties).maxPageLimit();
    }

    @Test
    void findByIdOrThrowTest_ValidId_ReturnsRide() {
        Long id = 1L;
        Ride ride = new Ride();
        when(rideRepository.findById(id)).thenReturn(Optional.of(ride));

        Ride result = rideServiceValidation.findByIdOrThrow(id);

        assertNotNull(result);
        assertEquals(ride, result);
        verify(rideRepository).findById(id);
    }

    @Test
    void findByIdOrThrowTest_InvalidId_ThrowsException() {
        Long id = 1L;
        when(rideRepository.findById(id)).thenReturn(Optional.empty());

        RideNotFoundException exception = assertThrows(RideNotFoundException.class,
                () -> rideServiceValidation.findByIdOrThrow(id));

        assertEquals(RIDE_NOT_FOUND, exception.getMessageKey());
        assertEquals(id.toString(), exception.getArgs()[0]);
        verify(rideRepository).findById(id);
    }

    @Test
    void findLastRidesPageTest_ValidPassengerId_ReturnsPage() {
        Long personId = 1L;
        PersonType personType = PersonType.PASSENGER;
        int offset = OFFSET;
        int limit = LIMIT;
        Page<Ride> ridePage = new PageImpl<>(Collections.singletonList(new Ride()));

        when(rideRepository.findByPassengerId(PageRequest.of(offset, limit, Sort.by(Sort.Order.desc("id"))), personId))
                .thenReturn(ridePage);

        Page<Ride> result = rideServiceValidation.findLastRidesPage(personId, personType, limit);

        assertNotNull(result);
        assertEquals(ridePage, result);
        verify(rideRepository).findByPassengerId(PageRequest.of(offset, limit, Sort.by(Sort.Order.desc("id"))), personId);
    }

    @Test
    void findLastRidesPageTest_ValidDriverId_ReturnsPage() {
        Long personId = 1L;
        PersonType personType = PersonType.DRIVER;
        int limit = 10;
        Page<Ride> ridePage = new PageImpl<>(Collections.singletonList(new Ride()));

        when(rideRepository.findByDriverId(PageRequest.of(0, limit, Sort.by(Sort.Order.desc("id"))), personId))
                .thenReturn(ridePage);

        Page<Ride> result = rideServiceValidation.findLastRidesPage(personId, personType, limit);

        assertNotNull(result);
        assertEquals(ridePage, result);
        verify(rideRepository).findByDriverId(PageRequest.of(0, limit, Sort.by(Sort.Order.desc("id"))), personId);
    }

    @Test
    void saveOrThrowTest_ValidRide_ReturnsSavedRide() {
        Ride ride = new Ride();
        when(rideRepository.save(ride)).thenReturn(ride);

        Ride result = rideServiceValidation.saveOrThrow(ride);

        assertNotNull(result);
        assertEquals(ride, result);
        verify(rideRepository).save(ride);
    }

    @Test
    void saveOrThrowTest_InvalidRide_ThrowsException() {
        Ride ride = new Ride();
        when(rideRepository.save(ride)).thenThrow(new RuntimeException("Database error"));

        DbModificationAttemptException exception = assertThrows(DbModificationAttemptException.class,
                () -> rideServiceValidation.saveOrThrow(ride));

        assertEquals(INVALID_ATTEMPT_CHANGE_RIDE, exception.getMessageKey());
        assertEquals("create", exception.getArgs()[0]);
        verify(rideRepository).save(ride);
    }

    @Test
    void updateOrThrowTest_ValidRideAndRequest_UpdatesRide() {
        Ride ride = RIDE;
        RideRequest rideRequest = RIDE_REQUEST;

        assertDoesNotThrow(() -> rideServiceValidation.updateOrThrow(ride, rideRequest));
        verify(rideMapper).updateRideFromDto(rideRequest, ride);
        verify(rideRepository).flush();
    }

    @Test
    void updateOrThrowTest_InvalidRide_ThrowsException() {
        Ride ride = RIDE;
        RideRequest rideRequest = RIDE_REQUEST;
        doThrow(new RuntimeException("Database error")).when(rideMapper).updateRideFromDto(rideRequest, ride);

        DbModificationAttemptException exception = assertThrows(DbModificationAttemptException.class,
                () -> rideServiceValidation.updateOrThrow(ride, rideRequest));

        assertEquals(INVALID_ATTEMPT_CHANGE_RIDE, exception.getMessageKey());
        assertEquals("update", exception.getArgs()[0]);
        verify(rideMapper).updateRideFromDto(rideRequest, ride);
    }

    @Test
    void updateDriverOrThrowTest_ValidRideAndRequest_UpdatesDriver() {
        Ride ride = RIDE;
        RideDriverSettingRequest rideRequest = RIDE_SETTING_REQUEST;

        assertDoesNotThrow(() -> rideServiceValidation.updateDriverOrThrow(ride, rideRequest));
        verify(rideDriverSettingMapper).updateRideFromDto(rideRequest, ride, RideStatus.ACCEPTED);
        verify(rideRepository).flush();
    }

    @Test
    void updateDriverOrThrowTest_InvalidRide_ThrowsException() {
        Ride ride = RIDE;
        RideDriverSettingRequest rideRequest = RIDE_SETTING_REQUEST;

        doThrow(new RuntimeException("Database error")).when(rideDriverSettingMapper)
                .updateRideFromDto(rideRequest, ride, RideStatus.ACCEPTED);

        DbModificationAttemptException exception = assertThrows(DbModificationAttemptException.class,
                () -> rideServiceValidation.updateDriverOrThrow(ride, rideRequest));

        assertEquals(INVALID_ATTEMPT_CHANGE_RIDE, exception.getMessageKey());
        assertEquals("update", exception.getArgs()[0]);
        verify(rideDriverSettingMapper).updateRideFromDto(rideRequest, ride, RideStatus.ACCEPTED);
    }

    @Test
    void updateStatusOrThrowTest_ValidRideAndRequest_UpdatesStatus() {
        Ride ride = RIDE;
        RideStatusRequest rideRequest = RIDE_STATUS_REQUEST;

        assertDoesNotThrow(() -> rideServiceValidation.updateStatusOrThrow(ride, rideRequest));
        verify(rideStatusMapper).updateRideFromDto(rideRequest, ride);
        verify(rideRepository).flush();
    }

    @Test
    void updateStatusOrThrowTest_InvalidRide_ThrowsException() {
        Ride ride = RIDE;
        RideStatusRequest rideRequest = RIDE_STATUS_REQUEST;

        doThrow(new RuntimeException("Database error")).when(rideStatusMapper).updateRideFromDto(rideRequest, ride);

        DbModificationAttemptException exception = assertThrows(DbModificationAttemptException.class,
                () -> rideServiceValidation.updateStatusOrThrow(ride, rideRequest));

        assertEquals(INVALID_ATTEMPT_CHANGE_RIDE, exception.getMessageKey());
        assertEquals("update", exception.getArgs()[0]);
        verify(rideStatusMapper).updateRideFromDto(rideRequest, ride);
    }

    @Test
    void checkPassengerExistenceTest_ValidPassengerId_DoesNotThrowException() {
        Long passengerId = RIDE_ID;

        doNothing().when(passengerClient).getPassengerById(passengerId);

        assertDoesNotThrow(() -> rideServiceValidation.checkPassengerExistence(passengerId));
        verify(passengerClient).getPassengerById(passengerId);
    }

    @Test
    void checkPassengerExistenceTest_InvalidPassengerId_ThrowsException() {
        Long passengerId = RIDE_ID;

        doThrow(new RuntimeException("Passenger not found")).when(passengerClient).getPassengerById(passengerId);

        assertThrows(RuntimeException.class, () -> rideServiceValidation.checkPassengerExistence(passengerId));
        verify(passengerClient).getPassengerById(passengerId);
    }

    @Test
    void checkCarExistenceTest_ValidCarId_DoesNotThrowException() {
        Long carId = 1L;

        doNothing().when(driverClient).getCarById(carId);

        assertDoesNotThrow(() -> rideServiceValidation.checkCarExistence(carId));
        verify(driverClient).getCarById(carId);
    }

    @Test
    void checkCarExistenceTest_InvalidCarId_ThrowsException() {
        Long carId = 1L;

        doThrow(new RuntimeException("Car not found")).when(driverClient).getCarById(carId);

        assertThrows(RuntimeException.class, () -> rideServiceValidation.checkCarExistence(carId));
        verify(driverClient).getCarById(carId);
    }

    @Test
    void checkDriverExistenceAndCarOwningTest_ValidDriverAndCar_DoesNotThrowException() {
        Long driverId = 1L;
        Long carId = 1L;
        DriverResponse driverResponse = DriverResponse
                .builder()
                .id(driverId)
                .name("Driver Name")
                .carIds(List.of(carId))
                .build();

        when(driverClient.getDriverById(driverId)).thenReturn(driverResponse);

        assertDoesNotThrow(() -> rideServiceValidation.checkDriverExistenceAndCarOwning(driverId, carId));
        verify(driverClient).getDriverById(driverId);
    }

    @Test
    void checkDriverExistenceAndCarOwningTest_InvalidDriver_ThrowsException() {
        Long driverId = 1L;
        Long carId = 1L;
        when(driverClient.getDriverById(driverId)).thenThrow(new RuntimeException("Driver not found"));

        assertThrows(RuntimeException.class, () -> rideServiceValidation.checkDriverExistenceAndCarOwning(driverId, carId));
        verify(driverClient).getDriverById(driverId);
    }

    @Test
    void checkDriverExistenceAndCarOwningTest_InvalidCarOwnership_ThrowsException() {
        Long driverId = 1L;
        Long carId = 1L;
        DriverResponse driverResponse = DriverResponse
                .builder()
                .id(driverId)
                .name("Driver Name")
                .carIds(List.of(2L))
                .build();

        when(driverClient.getDriverById(driverId)).thenReturn(driverResponse);

        DriverNotContainsCarException exception = assertThrows(DriverNotContainsCarException.class,
                () -> rideServiceValidation.checkDriverExistenceAndCarOwning(driverId, carId));

        assertEquals(DRIVER_NOT_CONTAINS_CAR, exception.getMessageKey());
        assertEquals(driverId.toString(), exception.getArgs()[0]);
        assertEquals(carId.toString(), exception.getArgs()[1]);
        verify(driverClient).getDriverById(driverId);
    }

    @Test
    void checkStatusTransitionAllowedTest_ValidTransition_DoesNotThrowException() {
        Ride ride = Ride
                .builder()
                .status(RideStatus.CREATED)
                .build();

        assertDoesNotThrow(() -> rideServiceValidation.checkStatusTransitionAllowed(ride, RIDE_STATUS_REQUEST));
    }

    @Test
    void checkStatusTransitionAllowedTest_InvalidTransition_ThrowsException() {
        Ride ride = Ride
                .builder()
                .status(RideStatus.COMPLETED)
                .build();

        assertThrows(IllegalStateException.class,
                () -> rideServiceValidation.checkStatusTransitionAllowed(ride, RIDE_STATUS_REQUEST));
    }

}
