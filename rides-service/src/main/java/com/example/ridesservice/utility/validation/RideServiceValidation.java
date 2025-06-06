package com.example.ridesservice.utility.validation;

import com.example.ridesservice.dto.ride.request.RideDriverSettingRequest;
import com.example.ridesservice.dto.ride.request.RideRequest;
import com.example.ridesservice.dto.ride.request.RideStatusRequest;
import com.example.ridesservice.enums.PersonType;
import com.example.ridesservice.model.Ride;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface RideServiceValidation {

    int cutDownLimit(int limit);

    Ride findByIdOrThrow(UUID id);

    Page<Ride> findLastRidesPage(UUID personId, PersonType personType, Integer limit);

    Ride saveOrThrow(Ride ride);

    void updateOrThrow(Ride ride, RideRequest rideRequest);

    void updateDriverOrThrow(Ride ride, RideDriverSettingRequest rideRequest);

    void updateStatusOrThrow(Ride ride, RideStatusRequest rideRequest);

    void checkPassengerExistence(UUID id);

    void checkCarExistence(UUID id);

    void checkDriverExistenceAndCarOwning(UUID driverId, UUID carId);

    void checkStatusTransitionAllowed(Ride ride, RideStatusRequest rideRequest);

}
