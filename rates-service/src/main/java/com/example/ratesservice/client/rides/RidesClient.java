package com.example.ratesservice.client.rides;

import com.example.ratesservice.client.dto.RidesResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface RidesClient {

    @GetMapping("/{id}")
    RidesResponse getRideById(@PathVariable("id") Long rideId);

}
