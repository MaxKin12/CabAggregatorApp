package com.example.ridesservice.client;

import com.example.ridesservice.client.driver.ExternalServiceClientDecoder;
import com.example.ridesservice.client.dto.PassengerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "passenger-client",
        configuration = {ExternalServiceClientDecoder.class}
)
public interface PassengerClient {

    @GetMapping("/{id}")
    PassengerResponse getPassengerById(@PathVariable("id") Long passengerId);

}
