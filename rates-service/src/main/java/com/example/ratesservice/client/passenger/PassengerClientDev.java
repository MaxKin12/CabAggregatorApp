package com.example.ratesservice.client.passenger;

import com.example.ratesservice.client.decoder.ExternalServiceClientDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

@FeignClient(
        name = "passenger-service",
        path = "/api/v1/passengers",
        configuration = {ExternalServiceClientDecoder.class}
)
@Profile("dev")
public interface PassengerClientDev extends PassengerClient {
}
