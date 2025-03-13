package com.example.ridesservice.client.passenger;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

@FeignClient(
        name = "passenger-client-test"
)
@Profile("test")
public interface PassengerClientTest extends PassengerClient {
}
