package com.example.ridesservice.service;

import com.example.ridesservice.dto.RidePageResponse;
import com.example.ridesservice.dto.RideRequest;
import com.example.ridesservice.dto.RideResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public interface RideService {
    RideResponse findById(@Positive(message = "{validate.method.parameter.id.negative}") Long id);
    RidePageResponse findAll(@Min(0) Integer offset, @Min(1) @Max(50) Integer limit);
    RideResponse create(@Valid RideRequest rideRequest);
    RideResponse update(@Valid RideRequest rideRequest,
                       @Positive(message = "{validate.method.parameter.id.negative}") Long id);
    void delete(@Positive(message = "{validate.method.parameter.id.negative}") Long id);
}
