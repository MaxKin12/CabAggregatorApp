package com.example.ridesservice.service;

import com.example.ridesservice.dto.request.RideBookingRequest;
import com.example.ridesservice.dto.request.RideDriverSettingRequest;
import com.example.ridesservice.dto.request.RideRequest;
import com.example.ridesservice.dto.request.RideStatusRequest;
import com.example.ridesservice.dto.response.RidePageResponse;
import com.example.ridesservice.dto.response.RideResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public interface RideService {

    RideResponse findById(@Positive(message = "{validate.method.parameter.id.negative}") Long id);

    RidePageResponse findAll(@Min(0) Integer offset, @Min(1) Integer limit);

    RidePageResponse findLastPassengerRides(
            @Positive(message = "{validate.method.parameter.id.negative}") Long passengerId,
            @Min(1) Integer limit
    );

    RidePageResponse findLastDriverRides(
            @Positive(message = "{validate.method.parameter.id.negative}") Long driverId,
            @Min(1) Integer limit
    );

    RideResponse create(@Valid RideRequest rideRequest);

    RideResponse book(@Valid RideBookingRequest rideRequest);

    RideResponse update(@Valid RideRequest rideRequest,
                        @Positive(message = "{validate.method.parameter.id.negative}") Long id);

    RideResponse setDriverToRide(@Valid RideDriverSettingRequest rideRequest,
                                 @Positive(message = "{validate.method.parameter.id.negative}") Long id);

    RideResponse updateStatus(@Valid RideStatusRequest rideRequest,
                              @Positive(message = "{validate.method.parameter.id.negative}") Long id);

    void delete(@Positive(message = "{validate.method.parameter.id.negative}") Long id);

}
