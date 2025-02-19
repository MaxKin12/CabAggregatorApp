package com.example.ratesservice.client.passenger;

import com.example.ratesservice.client.passenger.decoder.PassengerClientDecoder;
import com.example.ratesservice.client.passenger.dto.PassengerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "passenger-client",
        configuration = {PassengerClientDecoder.class}
)
public interface PassengerClient {

    @GetMapping("/{id}")
    ResponseEntity<PassengerResponse> getPassengerById(@PathVariable("id") Long passengerId);

}
