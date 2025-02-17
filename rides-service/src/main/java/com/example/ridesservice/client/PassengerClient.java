package com.example.ridesservice.client;

import com.example.ridesservice.client.decoder.PassengerClientDecoder;
import com.example.ridesservice.client.dto.PassengerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "passengerClient",
        url = "http://localhost:8081/",
        path = "/api/v1/passengers",
        configuration = {PassengerClientDecoder.class}
)
public interface PassengerClient {

    @GetMapping("/{id}")
    ResponseEntity<PassengerResponse> getPassengerById(@PathVariable("id") Long passengerId);

}
