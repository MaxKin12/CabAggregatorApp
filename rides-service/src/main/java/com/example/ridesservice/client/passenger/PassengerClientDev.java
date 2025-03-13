package com.example.ridesservice.client.passenger;

import com.example.ridesservice.client.decoder.ExternalServiceClientDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

@FeignClient(
        name = "passenger-client",
        configuration = {ExternalServiceClientDecoder.class}
)
@Profile("dev")
public interface PassengerClientDev extends PassengerClient {
}
