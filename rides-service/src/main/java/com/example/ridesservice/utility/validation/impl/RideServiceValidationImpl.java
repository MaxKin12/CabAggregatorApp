package com.example.ridesservice.utility.validation.impl;

import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.DRIVER_NOT_CONTAINS_CAR;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.INVALID_ATTEMPT_CHANGE_RIDE;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.RIDE_NOT_FOUND;

import com.example.ridesservice.client.driver.DriverClient;
import com.example.ridesservice.dto.external.DriverResponse;
import com.example.ridesservice.exception.external.DriverNotContainsCarException;
import com.example.ridesservice.client.passenger.PassengerClient;
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
import com.example.ridesservice.utility.validation.RideServiceValidation;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RideServiceValidationImpl implements RideServiceValidation {

    private final RideServiceProperties properties;
    private final RideRepository rideRepository;
    private final RideMapper rideMapper;
    private final RideDriverSettingMapper rideDriverSettingMapper;
    private final RideStatusMapper rideStatusMapper;
    private final PassengerClient passengerClient;
    private final DriverClient driverClient;

    @Override
    public int cutDownLimit(int limit) {
        return limit < properties.maxPageLimit() ? limit : properties.maxPageLimit();
    }

    @Override
    public Ride findByIdOrThrow(UUID id) {
        return rideRepository.findById(id)
                .orElseThrow(() -> new RideNotFoundException(RIDE_NOT_FOUND, id.toString()));
    }

    @Override
    public Page<Ride> findLastRidesPage(UUID personId, PersonType personType, Integer limit) {
        return personType.equals(PersonType.PASSENGER)
                ? rideRepository.findByPassengerId(
                        PageRequest.of(0, limit, Sort.by(Sort.Order.desc("id"))),
                        personId) :
                rideRepository.findByDriverId(
                        PageRequest.of(0, limit, Sort.by(Sort.Order.desc("id"))),
                        personId);
    }

    @Override
    public Ride saveOrThrow(Ride ride) {
        try {
            return rideRepository.save(ride);
        } catch (Exception e) {
            throw new DbModificationAttemptException(INVALID_ATTEMPT_CHANGE_RIDE, "create", e.getMessage());
        }
    }

    @Override
    public void updateOrThrow(Ride ride, RideRequest rideRequest) {
        try {
            rideMapper.updateRideFromDto(rideRequest, ride);
            rideRepository.flush();
        } catch (Exception e) {
            throw new DbModificationAttemptException(INVALID_ATTEMPT_CHANGE_RIDE, "update", e.getMessage());
        }
    }

    @Override
    public void updateDriverOrThrow(Ride ride, RideDriverSettingRequest rideRequest) {
        try {
            rideDriverSettingMapper.updateRideFromDto(rideRequest, ride, RideStatus.ACCEPTED);
            rideRepository.flush();
        } catch (Exception e) {
            throw new DbModificationAttemptException(INVALID_ATTEMPT_CHANGE_RIDE, "update", e.getMessage());
        }
    }

    @Override
    public void updateStatusOrThrow(Ride ride, RideStatusRequest rideRequest) {
        try {
            rideStatusMapper.updateRideFromDto(rideRequest, ride);
            rideRepository.flush();
        } catch (Exception e) {
            throw new DbModificationAttemptException(INVALID_ATTEMPT_CHANGE_RIDE, "update", e.getMessage());
        }
    }

    @Override
    public void checkPassengerExistence(UUID id) {
        passengerClient.getPassengerById(id);
    }

    @Override
    public void checkCarExistence(UUID id) {
        driverClient.getCarById(id);
    }

    @Override
    public void checkDriverExistenceAndCarOwning(UUID driverId, UUID carId) {
        DriverResponse driverResponse = driverClient.getDriverById(driverId);
        if (!driverResponse.carIds().contains(carId)) {
            throw new DriverNotContainsCarException(
                    DRIVER_NOT_CONTAINS_CAR, driverResponse.id().toString(), carId.toString());
        }
    }

    @Override
    public void checkStatusTransitionAllowed(Ride ride, RideStatusRequest rideRequest) {
        RideStatus oldRideStatus = ride.getStatus();
        RideStatus newRideStatus = RideStatus.valueOf(rideRequest.status().toUpperCase());
        RideStatus.throwIfWrongStatusTransition(oldRideStatus, newRideStatus);
    }

}
