package com.example.driverservice.service;

import com.example.driverservice.dto.driver.DriverRequest;
import com.example.driverservice.dto.driver.DriverResponse;
import com.example.driverservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.driverservice.dto.page.PageResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.util.UUID;

public interface DriverService {

    DriverResponse findById(UUID id);

    PageResponse findAll(@Min(0) Integer offset, @Min(1) Integer limit);

    DriverResponse create(@Valid DriverRequest driverRequest);

    DriverResponse updateDriver(@Valid DriverRequest driverRequest, UUID id);

    void updateRate(RateChangeEventResponse event);

    void delete(UUID id);

}
