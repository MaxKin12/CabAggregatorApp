package com.example.driverservice.service;

import com.example.driverservice.dto.car.CarRequest;
import com.example.driverservice.dto.car.CarResponse;
import com.example.driverservice.dto.car.CarResponseList;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

public interface CarService {
    CarResponse findById(@Positive(message = "{validate.method.parameter.id.negative}") Long id);
    CarResponseList findAll();
    CarResponse create(@Valid CarRequest carRequest);
    CarResponse update(@Valid CarRequest carRequest,
                       @Positive(message = "{validate.method.parameter.id.negative}") Long id);
    void delete(@Positive(message = "{validate.method.parameter.id.negative}") Long id);
}
