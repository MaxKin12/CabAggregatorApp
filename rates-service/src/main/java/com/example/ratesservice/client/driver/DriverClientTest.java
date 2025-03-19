package com.example.ratesservice.client.driver;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

@FeignClient(
        name = "driver-client-test"
)
@Profile("test")
public interface DriverClientTest extends DriverClient {
}
