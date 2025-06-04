package com.example.ratesservice.client.rides;

import static com.example.ratesservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.RIDES_SERVICE_IN_OPENED_STATE;

import com.example.ratesservice.dto.external.RidesResponse;
import com.example.ratesservice.exception.custom.FeignClientTemporarilyUnavailable;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface RidesClient {

    @GetMapping("/{id}")
    @CircuitBreaker(name = "ridesFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    @Retry(name = "ridesFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    RidesResponse getRideById(@PathVariable("id") UUID rideId);

    default RidesResponse throwExceptionThatTemporarilyUnavailable(CallNotPermittedException e) {
        throw new FeignClientTemporarilyUnavailable(RIDES_SERVICE_IN_OPENED_STATE);
    }

}
