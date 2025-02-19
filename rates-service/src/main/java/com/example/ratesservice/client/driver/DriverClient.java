package com.example.ratesservice.client.driver;

import com.example.ratesservice.client.driver.decoder.DriverClientDecoder;
import com.example.ratesservice.client.driver.dto.DriverResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "driver-client",
        configuration = {DriverClientDecoder.class}
)
public interface DriverClient {

    @GetMapping("/{id}")
    ResponseEntity<DriverResponse> getDriverById(@PathVariable("id") Long driverId);

}
