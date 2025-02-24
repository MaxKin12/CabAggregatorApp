package com.example.ratesservice.client;

import com.example.ratesservice.client.decoder.ExternalServiceClientDecoder;
import com.example.ratesservice.client.dto.PassengerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "passenger-client",
        configuration = {ExternalServiceClientDecoder.class}
)
public interface PassengerClient {

    @GetMapping("/{id}")
    ResponseEntity<PassengerResponse> getPassengerById(@PathVariable("id") Long passengerId);

}
