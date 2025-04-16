package com.example.authservice.client;

import static com.example.authservice.utility.constants.InternationalizationExceptionVariablesConstants.PASSENGER_SERVICE_IN_OPENED_STATE;

import com.example.authservice.client.decoder.ExternalServiceClientDecoder;
import com.example.authservice.dto.external.PassengerResponse;
import com.example.authservice.dto.external.ExternalEntityRequest;
import com.example.authservice.exception.custom.FeignClientTemporarilyUnavailable;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    PassengerResponse createPassenger(@RequestBody ExternalEntityRequest passengerRequest);

    @PatchMapping("/{id}")
    @CircuitBreaker(name = "passengerFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    @Retry(name = "passengerFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    PassengerResponse updatePassenger(@RequestBody ExternalEntityRequest passengerRequest,
                                      @PathVariable("id") UUID passengerId);

    @DeleteMapping("/{id}")
    @CircuitBreaker(
            name = "passengerFeignClient",
            fallbackMethod = "throwExceptionThatTemporarilyUnavailableReturnVoid"
    )
    @Retry(name = "passengerFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailableReturnVoid")
    void deletePassenger(@PathVariable("id") UUID passengerId);

    default PassengerResponse throwExceptionThatTemporarilyUnavailable(CallNotPermittedException e) {
        throw new FeignClientTemporarilyUnavailable(PASSENGER_SERVICE_IN_OPENED_STATE);
    }

    default PassengerResponse throwExceptionThatTemporarilyUnavailableReturnVoid(CallNotPermittedException e) {
        throw new FeignClientTemporarilyUnavailable(PASSENGER_SERVICE_IN_OPENED_STATE);
    }

}
