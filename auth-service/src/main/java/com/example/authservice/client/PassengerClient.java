package com.example.authservice.client;

import com.example.authservice.client.decoder.ExternalServiceClientDecoder;
import com.example.authservice.dto.external.PassengerResponse;
import com.example.authservice.dto.external.ExternalEntityRequest;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "passenger-service",
        path = "/api/v1/passengers",
        configuration = {ExternalServiceClientDecoder.class}
)
public interface PassengerClient {

    @PostMapping
    PassengerResponse createPassenger(@RequestBody ExternalEntityRequest passengerRequest);

    @PatchMapping("/{id}")
    PassengerResponse updatePassenger(@RequestBody ExternalEntityRequest passengerRequest,
                                      @PathVariable("id") UUID passengerId);

    @DeleteMapping("/{id}")
    void deletePassenger(@PathVariable("id") UUID passengerId);

}
