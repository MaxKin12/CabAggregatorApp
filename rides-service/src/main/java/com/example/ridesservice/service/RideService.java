package com.example.ridesservice.service;

import com.example.ridesservice.dto.ride.request.RideBookingRequest;
import com.example.ridesservice.dto.ride.request.RideDriverSettingRequest;
import com.example.ridesservice.dto.ride.request.RideRequest;
import com.example.ridesservice.dto.ride.request.RideStatusRequest;
import com.example.ridesservice.dto.ride.response.RidePageResponse;
import com.example.ridesservice.dto.ride.response.RideResponse;
import com.example.ridesservice.enums.PersonType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.UUID;

public interface RideService {

    RideResponse findById(UUID id);

    RidePageResponse findAll(@Min(0) Integer offset, @Min(1) Integer limit);

    RidePageResponse findLastPersonRides(UUID passengerId, @Min(1) Integer limit, PersonType personType);

    RideResponse create(@Valid RideRequest rideRequest);

    RideResponse bookRide(@Valid RideBookingRequest rideRequest);

    RideResponse update(@Valid RideRequest rideRequest, UUID id);

    RideResponse setDriverToRide(@Valid RideDriverSettingRequest rideRequest);

    RideResponse updateStatus(@Valid RideStatusRequest rideRequest, UUID id);

    void delete(UUID id);

}
