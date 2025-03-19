package com.example.ratesservice.client.rides;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Profile;

@FeignClient(
        name = "rides-client-test"
)
@Profile("test")
public interface RidesClientTest extends RidesClient {
}
