package com.example.ratesservice.client.passenger;

import com.example.ratesservice.client.dto.PassengerResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface PassengerClient {

    @GetMapping("/{id}")
    PassengerResponse getPassengerById(@PathVariable("id") Long passengerId);

}
