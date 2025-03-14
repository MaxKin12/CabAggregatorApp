package com.example.ratesservice.client.driver;

import com.example.ratesservice.client.dto.DriverResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface DriverClient {

    @GetMapping("/{id}")
    DriverResponse getDriverById(@PathVariable("id") Long driverId);

}
