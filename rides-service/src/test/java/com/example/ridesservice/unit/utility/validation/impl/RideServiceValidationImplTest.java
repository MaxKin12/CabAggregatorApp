package com.example.ridesservice.unit.utility.validation.impl;

import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.ATTEMPT_CHANGE_CREATE;
import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.ATTEMPT_CHANGE_UPDATE;
import static com.example.ridesservice.configuration.constants.RideTestData.CAR_ID;
import static com.example.ridesservice.configuration.constants.RideTestData.CAR_ID_2;
import static com.example.ridesservice.configuration.constants.RideTestData.DRIVER_ID;
import static com.example.ridesservice.configuration.constants.RideTestData.INVALID_RIDE_ID;
import static com.example.ridesservice.configuration.constants.RideTestData.LIMIT;
import static com.example.ridesservice.configuration.constants.RideTestData.LIMIT_CUT;
import static com.example.ridesservice.configuration.constants.RideTestData.PASSENGER_ID;
import static com.example.ridesservice.configuration.constants.RideTestData.RIDE;
import static com.example.ridesservice.configuration.constants.RideTestData.RIDE_ID;
import static com.example.ridesservice.configuration.constants.RideTestData.RIDE_PAGE;
import static com.example.ridesservice.configuration.constants.RideTestData.RIDE_REQUEST_UPDATED;
import static com.example.ridesservice.configuration.constants.RideTestData.RIDE_STATUS_REQUEST;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.DRIVER_NOT_CONTAINS_CAR;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.INVALID_ATTEMPT_CHANGE_RIDE;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.RIDE_NOT_FOUND;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.WRONG_STATUS_TRANSITION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.example.ridesservice.client.driver.DriverClient;
import com.example.ridesservice.client.passenger.PassengerClient;
import com.example.ridesservice.dto.external.DriverResponse;
import com.example.ridesservice.dto.external.PassengerResponse;
import com.example.ridesservice.exception.external.DriverNotContainsCarException;
import com.example.ridesservice.configuration.properties.RideServiceProperties;
import com.example.ridesservice.dto.ride.request.RideRequest;
import com.example.ridesservice.dto.ride.request.RideStatusRequest;
import com.example.ridesservice.enums.PersonType;
import com.example.ridesservice.enums.RideStatus;
import com.example.ridesservice.exception.custom.DbModificationAttemptException;
import com.example.ridesservice.exception.custom.RideNotFoundException;
import com.example.ridesservice.exception.custom.WrongStatusTransitionException;
import com.example.ridesservice.mapper.RideMapper;
import com.example.ridesservice.model.Ride;
import com.example.ridesservice.repository.RideRepository;
import com.example.ridesservice.utility.validation.impl.RideServiceValidationImpl;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class RideServiceValidationImplTest {

    @InjectMocks
    private RideServiceValidationImpl validation;

    @Mock
    private RideServiceProperties properties;

    @Mock
    private RideRepository rideRepository;

    @Mock
    private RideMapper rideMapper;

    @Mock
    private PassengerClient passengerClient;

    @Mock
    private DriverClient driverClient;

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
    void findByIdOrThrowTest_ValidId_ReturnsRide() {
        Long id = RIDE_ID;
        Ride ride = RIDE;

        when(rideRepository.findById(id)).thenReturn(Optional.of(ride));

        Ride result = validation.findByIdOrThrow(id);

        assertThat(result).isNotNull().isEqualTo(ride);
        verify(rideRepository).findById(id);
    }

    @Test
    void findByIdOrThrowTest_InvalidId_ThrowsException() {
        Long id = INVALID_RIDE_ID;
        String[] args = new String[]{id.toString()};

        when(rideRepository.findById(id)).thenReturn(Optional.empty());

        assertThatExceptionOfType(RideNotFoundException.class)
                .isThrownBy(() -> validation.findByIdOrThrow(id))
                .withMessage(RIDE_NOT_FOUND)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(rideRepository).findById(id);
    }

    @Test
    void findLastRidesPageTest_Passenger_ReturnsPage() {
        UUID passengerId = PASSENGER_ID;
        PersonType personType = PersonType.PASSENGER;
        Page<Ride> ridePage = RIDE_PAGE;

        when(rideRepository.findByPassengerId(any(PageRequest.class), eq(passengerId))).thenReturn(ridePage);

        Page<Ride> result = validation.findLastRidesPage(passengerId, personType, LIMIT);

        assertThat(result).isNotNull().isEqualTo(ridePage);
        verify(rideRepository).findByPassengerId(any(PageRequest.class), eq(passengerId));
    }

    @Test
    void findLastRidesPageTest_Driver_ReturnsPage() {
        UUID driverId = DRIVER_ID;
        PersonType personType = PersonType.DRIVER;
        Page<Ride> ridePage = RIDE_PAGE;

        when(rideRepository.findByDriverId(any(PageRequest.class), eq(driverId))).thenReturn(ridePage);

        Page<Ride> result = validation.findLastRidesPage(driverId, personType, LIMIT);

        assertThat(result).isNotNull().isEqualTo(ridePage);
        verify(rideRepository).findByDriverId(any(PageRequest.class), eq(driverId));
    }

    @Test
    void saveOrThrowTest_ValidRide_SavesRide() {
        Ride ride = RIDE;

        when(rideRepository.save(ride)).thenReturn(ride);

        Ride result = validation.saveOrThrow(ride);

        assertThat(result).isNotNull().isEqualTo(ride);
        verify(rideRepository).save(ride);
    }

    @Test
    void saveOrThrowTest_InvalidRide_ThrowsException() {
        Ride ride = RIDE;
        String[] args = new String[]{ATTEMPT_CHANGE_CREATE, INVALID_ATTEMPT_CHANGE_RIDE};

        doThrow(new DbModificationAttemptException(INVALID_ATTEMPT_CHANGE_RIDE, args))
                .when(rideRepository).save(ride);

        assertThatExceptionOfType(DbModificationAttemptException.class)
                .isThrownBy(() -> validation.saveOrThrow(ride))
                .withMessage(INVALID_ATTEMPT_CHANGE_RIDE)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(rideRepository).save(ride);
    }

    @Test
    void updateOrThrowTest_ValidRide_UpdatesRide() {
        Ride ride = RIDE;
        RideRequest request = RIDE_REQUEST_UPDATED;

        doNothing().when(rideMapper).updateRideFromDto(request, ride);
        doNothing().when(rideRepository).flush();

        assertThatCode(() -> validation.updateOrThrow(ride, request)).doesNotThrowAnyException();

        verify(rideMapper).updateRideFromDto(request, ride);
        verify(rideRepository).flush();
    }

    @Test
    void updateOrThrowTest_InvalidRide_ThrowsException() {
        Ride ride = RIDE;
        RideRequest request = RIDE_REQUEST_UPDATED;
        String[] args = new String[]{ATTEMPT_CHANGE_UPDATE, INVALID_ATTEMPT_CHANGE_RIDE};

        doThrow(new DbModificationAttemptException(INVALID_ATTEMPT_CHANGE_RIDE, args))
                .when(rideMapper).updateRideFromDto(request, ride);

        assertThatExceptionOfType(DbModificationAttemptException.class)
                .isThrownBy(() -> validation.updateOrThrow(ride, request))
                .withMessage(INVALID_ATTEMPT_CHANGE_RIDE)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(rideMapper).updateRideFromDto(request, ride);
        verifyNoInteractions(rideRepository);
    }

    @Test
    void checkPassengerExistenceTest_ValidPassenger_NoException() {
        UUID passengerId = PASSENGER_ID;
        PassengerResponse passengerResponse = PassengerResponse.builder()
                .id(passengerId)
                .build();

        when(passengerClient.getPassengerById(passengerId)).thenReturn(passengerResponse);

        assertThatCode(() -> validation.checkPassengerExistence(passengerId)).doesNotThrowAnyException();

        verify(passengerClient).getPassengerById(passengerId);
    }

    @Test
    void checkDriverExistenceAndCarOwningTest_ValidDriverAndCar_NoException() {
        UUID driverId = DRIVER_ID;
        Long carId = CAR_ID;
        DriverResponse driverResponse = DriverResponse.builder()
                .id(driverId)
                .carIds(List.of(carId))
                .build();

        when(driverClient.getDriverById(driverId)).thenReturn(driverResponse);

        assertThatCode(() -> validation.checkDriverExistenceAndCarOwning(driverId, carId)).doesNotThrowAnyException();

        verify(driverClient).getDriverById(driverId);
    }

    @Test
    void checkDriverExistenceAndCarOwningTest_InvalidCar_ThrowsException() {
        UUID driverId = DRIVER_ID;
        Long carId = INVALID_RIDE_ID;
        DriverResponse driverResponse = DriverResponse.builder()
                .id(driverId)
                .carIds(List.of(CAR_ID_2))
                .build();
        String[] args = new String[]{driverResponse.id().toString(), carId.toString()};

        when(driverClient.getDriverById(driverId)).thenReturn(driverResponse);

        assertThatExceptionOfType(DriverNotContainsCarException.class)
                .isThrownBy(() -> validation.checkDriverExistenceAndCarOwning(driverId, carId))
                .withMessage(DRIVER_NOT_CONTAINS_CAR)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(driverClient).getDriverById(driverId);
    }

    @Test
    void checkStatusTransitionAllowedTest_ValidTransition_NoException() {
        Ride ride = RIDE;
        RideStatusRequest request = RIDE_STATUS_REQUEST;

        ride.setStatus(RideStatus.ACCEPTED);

        assertThatCode(() -> validation.checkStatusTransitionAllowed(ride, request)).doesNotThrowAnyException();
    }

    @Test
    void checkStatusTransitionAllowedTest_InvalidTransition_ThrowsException() {
        Ride ride = RIDE;
        RideStatusRequest request = RIDE_STATUS_REQUEST;
        ride.setStatus(RideStatus.COMPLETED);
        String[] args = new String[]{};

        assertThatExceptionOfType(WrongStatusTransitionException.class)
                .isThrownBy(() -> validation.checkStatusTransitionAllowed(ride, request))
                .withMessage(WRONG_STATUS_TRANSITION)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));
    }

}
