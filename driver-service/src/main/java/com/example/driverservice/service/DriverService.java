package com.example.driverservice.service;

import static com.example.driverservice.utility.constants.InternationalizationValidationPropertyVariablesConstants.ID_NEGATIVE;

import com.example.driverservice.dto.driver.DriverRequest;
import com.example.driverservice.dto.driver.DriverResponse;
import com.example.driverservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.driverservice.dto.page.PageResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

public interface DriverService {

    DriverResponse findById(@Positive(message = ID_NEGATIVE) Long id);

    PageResponse findAll(@Min(0) Integer offset, @Min(1) Integer limit);

    DriverResponse create(@Valid DriverRequest driverRequest);

    DriverResponse updateDriver(@Valid DriverRequest driverRequest, @Positive(message = ID_NEGATIVE) Long id);

    void updateRate(RateChangeEventResponse event);

    void delete(@Positive(message = ID_NEGATIVE) Long id);

}
