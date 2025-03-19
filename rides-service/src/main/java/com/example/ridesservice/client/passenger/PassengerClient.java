package com.example.ridesservice.client.passenger;

import com.example.ridesservice.client.dto.PassengerResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface PassengerClient {

    @GetMapping("/{id}")
    PassengerResponse getPassengerById(@PathVariable("id") Long passengerId);

}
