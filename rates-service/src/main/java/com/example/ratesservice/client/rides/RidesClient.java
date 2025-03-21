package com.example.ratesservice.client.rides;

import com.example.ratesservice.client.dto.RidesResponse;
import com.example.ratesservice.exception.custom.FeignClientTemporarilyUnavailable;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static com.example.ratesservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.PASSENGER_SERVICE_IN_OPENED_STATE;

public interface RidesClient {

    @GetMapping("/{id}")
    @CircuitBreaker(name = "ridesFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    RidesResponse getRideById(@PathVariable("id") Long rideId);

    default RidesResponse throwExceptionThatTemporarilyUnavailable(CallNotPermittedException e) {
        throw new FeignClientTemporarilyUnavailable(PASSENGER_SERVICE_IN_OPENED_STATE);
    }

}
