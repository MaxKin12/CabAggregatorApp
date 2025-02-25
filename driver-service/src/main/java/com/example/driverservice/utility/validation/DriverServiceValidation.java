package com.example.driverservice.utility.validation;

import com.example.driverservice.dto.driver.DriverRequest;
import com.example.driverservice.model.entity.Driver;

public interface DriverServiceValidation {

    Driver findByIdOrThrow(Long id);

    int cutDownLimit(int limit);

    Driver saveOrThrow(Driver passenger);

    void updateOrThrow(Driver passenger, DriverRequest passengerRequest);

}
