package com.example.driverservice.utility.validation;

import com.example.driverservice.dto.car.CarRequest;
import com.example.driverservice.model.entity.Car;

public interface CarServiceValidation {

    Car findByIdOrThrow(Long id);

    int cutDownLimit(int limit);

    Car saveOrThrow(Car car);

    void updateOrThrow(Car car, CarRequest carRequest);

}
