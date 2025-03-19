package com.example.ratesservice.client.rides;

import com.example.ratesservice.client.decoder.ExternalServiceClientDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

@FeignClient(
        name = "rides-client",
        configuration = {ExternalServiceClientDecoder.class}
)
@Profile("dev")
public interface RidesClientDev extends RidesClient {
}
