package com.example.ridesservice.client.driver;

import com.example.ridesservice.client.dto.CarResponse;
import com.example.ridesservice.client.dto.DriverResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface DriverClient {

    @GetMapping("/drivers/{id}")
    DriverResponse getDriverById(@PathVariable("id") Long driverId);

    @GetMapping("/cars/{id}")
    CarResponse getCarById(@PathVariable("id") Long carId);

}
