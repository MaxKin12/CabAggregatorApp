package com.example.authservice.client;

import static com.example.authservice.utility.constants.InternationalizationExceptionVariablesConstants.PASSENGER_SERVICE_IN_OPENED_STATE;

import com.example.authservice.client.decoder.ExternalServiceClientDecoder;
import com.example.authservice.dto.external.PassengerResponse;
import com.example.authservice.dto.external.ExternalEntityResponse;
import com.example.authservice.exception.custom.FeignClientTemporarilyUnavailable;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "passenger-service",
        path = "/api/v1/passengers",
        configuration = {ExternalServiceClientDecoder.class}
)
public interface PassengerClient {

    @PostMapping
    @CircuitBreaker(name = "passengerFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    @Retry(name = "passengerFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    PassengerResponse createPassenger(@RequestBody ExternalEntityResponse passengerRequest);

    default PassengerResponse throwExceptionThatTemporarilyUnavailable(CallNotPermittedException e) {
        throw new FeignClientTemporarilyUnavailable(PASSENGER_SERVICE_IN_OPENED_STATE);
    }

}
