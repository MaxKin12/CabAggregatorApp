package com.example.ridesservice.client.driver;

import com.example.ridesservice.client.driver.decoder.DriverClientDecoder;
import com.example.ridesservice.client.driver.dto.CarResponse;
import com.example.ridesservice.client.driver.dto.DriverResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "driver-client",
        configuration = {DriverClientDecoder.class}
)
public interface DriverClient {

    @GetMapping("/drivers/{id}")
    ResponseEntity<DriverResponse> getDriverById(@PathVariable("id") Long driverId);

    @GetMapping("/cars/{id}")
    ResponseEntity<CarResponse> getCarById(@PathVariable("id") Long carId);

}
