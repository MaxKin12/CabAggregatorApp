package com.example.ridesservice.client;

import com.example.ridesservice.client.decoder.ExternalServiceClientDecoder;
import com.example.ridesservice.client.dto.CarResponse;
import com.example.ridesservice.client.dto.DriverResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "driver-client",
        configuration = {ExternalServiceClientDecoder.class}
)
public interface DriverClient {

    @GetMapping("/drivers/{id}")
    DriverResponse getDriverById(@PathVariable("id") Long driverId);

    @GetMapping("/cars/{id}")
    CarResponse getCarById(@PathVariable("id") Long carId);

}
