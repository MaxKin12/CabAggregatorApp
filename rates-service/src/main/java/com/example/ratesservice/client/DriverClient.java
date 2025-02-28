package com.example.ratesservice.client;

import com.example.ratesservice.client.decoder.ExternalServiceClientDecoder;
import com.example.ratesservice.client.dto.DriverResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "driver-client",
        configuration = {ExternalServiceClientDecoder.class}
)
public interface DriverClient {

    @GetMapping("/{id}")
    DriverResponse getDriverById(@PathVariable("id") Long driverId);

}
