package com.example.ridesservice.client.passenger;

import com.example.ridesservice.client.passenger.decoder.PassengerClientDecoder;
import com.example.ridesservice.client.passenger.dto.PassengerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "passenger-client",
        url = "http://localhost:8081/api/v1/passengers",
        configuration = {PassengerClientDecoder.class}
)
public interface PassengerClient {

    @GetMapping("/{id}")
    ResponseEntity<PassengerResponse> getPassengerById(@PathVariable("id") Long passengerId);

}
