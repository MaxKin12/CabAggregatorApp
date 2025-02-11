package com.example.driverservice.service;

import com.example.driverservice.dto.driver.DriverPageResponse;
import com.example.driverservice.dto.driver.DriverRequest;
import com.example.driverservice.dto.driver.DriverResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public interface DriverService {
    DriverResponse findById(@Positive(message = "{validate.method.parameter.id.negative}") Long id);

    DriverPageResponse findAll(@Min(0) Integer offset, @Min(1) Integer limit);

    DriverResponse create(@Valid DriverRequest driverRequest);

    DriverResponse update(@Valid DriverRequest driverRequest,
                          @Positive(message = "{validate.method.parameter.id.negative}") Long id);

    void delete(@Positive(message = "{validate.method.parameter.id.negative}") Long id);
}
