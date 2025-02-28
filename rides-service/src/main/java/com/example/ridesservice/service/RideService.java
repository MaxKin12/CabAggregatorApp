package com.example.ridesservice.service;

import static com.example.ridesservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.ID_NEGATIVE;

import com.example.ridesservice.dto.ride.request.RideBookingRequest;
import com.example.ridesservice.dto.ride.request.RideDriverSettingRequest;
import com.example.ridesservice.dto.ride.request.RideRequest;
import com.example.ridesservice.dto.ride.request.RideStatusRequest;
import com.example.ridesservice.dto.ride.response.RidePageResponse;
import com.example.ridesservice.dto.ride.response.RideResponse;
import com.example.ridesservice.enums.PersonType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public interface RideService {

    RideResponse findById(@Positive(message = ID_NEGATIVE) Long id);

    RidePageResponse findAll(@Min(0) Integer offset, @Min(1) Integer limit);

    RidePageResponse findLastPersonRides(@Positive(message = ID_NEGATIVE) Long passengerId, @Min(1) Integer limit,
                                         PersonType personType);

    RideResponse create(@Valid RideRequest rideRequest);

    RideResponse bookRide(@Valid RideBookingRequest rideRequest);

    RideResponse update(@Valid RideRequest rideRequest, @Positive(message = ID_NEGATIVE) Long id);

    RideResponse setDriverToRide(@Valid RideDriverSettingRequest rideRequest);

    RideResponse updateStatus(@Valid RideStatusRequest rideRequest, @Positive(message = ID_NEGATIVE) Long id);

    void delete(@Positive(message = ID_NEGATIVE) Long id);

}
