package com.example.ridesservice.client.passenger;

import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.PASSENGER_SERVICE_IN_OPENED_STATE;

import com.example.ridesservice.client.dto.PassengerResponse;
import com.example.ridesservice.exception.custom.FeignClientTemporarilyUnavailable;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface PassengerClient {

    @GetMapping("/{id}")
    @CircuitBreaker(name = "passengerFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    @Retry(name = "passengerFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    PassengerResponse getPassengerById(@PathVariable("id") UUID passengerId);

    default PassengerResponse throwExceptionThatTemporarilyUnavailable(CallNotPermittedException e) {
        throw new FeignClientTemporarilyUnavailable(PASSENGER_SERVICE_IN_OPENED_STATE);
    }

}
