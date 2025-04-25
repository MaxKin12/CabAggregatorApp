package com.example.authservice.service;

import com.example.authservice.dto.external.ExternalEntityRequest;
import com.example.authservice.dto.external.PassengerResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.UUID;

public interface ResilientPassengerClientService {

    @CircuitBreaker(name = "passengerFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    @Retry(name = "passengerFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    PassengerResponse createPassenger(ExternalEntityRequest passengerRequest);

    @CircuitBreaker(name = "passengerFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    @Retry(name = "passengerFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    PassengerResponse updatePassenger(ExternalEntityRequest passengerRequest, UUID passengerId);

    @CircuitBreaker(
            name = "passengerFeignClient",
            fallbackMethod = "throwExceptionThatTemporarilyUnavailableReturnVoid"
    )
    @Retry(name = "passengerFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailableReturnVoid")
    void deletePassenger(UUID passengerId);

}
