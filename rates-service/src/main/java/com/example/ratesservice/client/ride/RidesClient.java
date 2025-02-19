package com.example.ratesservice.client.ride;

import com.example.ratesservice.client.ride.decoder.RidesClientDecoder;
import com.example.ratesservice.client.ride.dto.RidesResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "rides-client",
        configuration = {RidesClientDecoder.class}
)
public interface RidesClient {

    @GetMapping("/{id}")
    ResponseEntity<RidesResponse> getRideById(@PathVariable("id") Long rideId);

}
