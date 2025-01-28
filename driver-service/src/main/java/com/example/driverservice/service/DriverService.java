package com.example.driverservice.service;

import com.example.driverservice.dto.driver.DriverResponseList;
import com.example.driverservice.dto.driver.DriverRequest;
import com.example.driverservice.dto.driver.DriverResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import static com.example.driverservice.constant.ExceptionMessagesConstants.NEGATIVE_ID_MESSAGE;

public interface DriverService {
    DriverResponse findById(@Positive(message = NEGATIVE_ID_MESSAGE) Long id);
    DriverResponseList findAll();
    DriverResponse create(@Valid DriverRequest passengerRequest);
    DriverResponse update(@Valid DriverRequest passengerRequest,
                             @Positive(message = NEGATIVE_ID_MESSAGE) Long id);
    void delete(@Positive(message = NEGATIVE_ID_MESSAGE) Long id);
}
