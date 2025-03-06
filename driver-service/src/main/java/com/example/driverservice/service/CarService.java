package com.example.driverservice.service;

import static com.example.driverservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.ID_NEGATIVE;

import com.example.driverservice.dto.car.CarRequest;
import com.example.driverservice.dto.car.CarResponse;
import com.example.driverservice.dto.page.PageResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public interface CarService {

    CarResponse findById(@Positive(message = ID_NEGATIVE) Long id);

    PageResponse findAll(@Min(0) Integer offset, @Min(1) Integer limit);

    CarResponse create(@Valid CarRequest carRequest);

    CarResponse update(@Valid CarRequest carRequest, @Positive(message = ID_NEGATIVE) Long id);

    void delete(@Positive(message = ID_NEGATIVE) Long id);

}
