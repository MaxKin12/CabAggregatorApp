package com.example.ratesservice.client;

import com.example.ratesservice.client.decoder.ExternalServiceClientDecoder;
import com.example.ratesservice.client.dto.RidesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "rides-client",
        configuration = {ExternalServiceClientDecoder.class}
)
public interface RidesClient {

    @GetMapping("/{id}")
    RidesResponse getRideById(@PathVariable("id") Long rideId);

}
