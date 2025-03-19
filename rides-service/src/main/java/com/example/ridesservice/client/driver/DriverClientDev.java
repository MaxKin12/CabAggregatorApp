package com.example.ridesservice.client.driver;

import com.example.ridesservice.client.decoder.ExternalServiceClientDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

@FeignClient(
        name = "driver-client",
        configuration = {ExternalServiceClientDecoder.class}
)
@Profile("dev")
public interface DriverClientDev extends DriverClient {
}
