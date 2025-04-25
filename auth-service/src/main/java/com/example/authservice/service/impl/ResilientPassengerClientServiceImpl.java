package com.example.authservice.service.impl;

import static com.example.authservice.utility.constants.InternationalizationExceptionVariablesConstants.PASSENGER_SERVICE_IN_OPENED_STATE;

import com.example.authservice.client.PassengerClient;
import com.example.authservice.dto.external.ExternalEntityRequest;
import com.example.authservice.dto.external.PassengerResponse;
import com.example.authservice.exception.custom.FeignClientTemporarilyUnavailable;
import com.example.authservice.service.ResilientPassengerClientService;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResilientPassengerClientServiceImpl implements ResilientPassengerClientService {

    private final PassengerClient passengerClient;

    @Override
    @CircuitBreaker(name = "passengerFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    @Retry(name = "passengerFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    public PassengerResponse createPassenger(ExternalEntityRequest passengerRequest) {
        return passengerClient.createPassenger(passengerRequest);
    }

    @Override
    @CircuitBreaker(name = "passengerFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    @Retry(name = "passengerFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    public PassengerResponse updatePassenger(ExternalEntityRequest passengerRequest, UUID passengerId) {
        return passengerClient.updatePassenger(passengerRequest, passengerId);
    }

    @Override
    @CircuitBreaker(
            name = "passengerFeignClient",
            fallbackMethod = "throwExceptionThatTemporarilyUnavailableReturnVoid"
    )
    @Retry(name = "passengerFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailableReturnVoid")
    public void deletePassenger(UUID passengerId) {
        passengerClient.deletePassenger(passengerId);
    }

    private PassengerResponse throwExceptionThatTemporarilyUnavailable(CallNotPermittedException e) {
        throw new FeignClientTemporarilyUnavailable(PASSENGER_SERVICE_IN_OPENED_STATE);
    }

    private PassengerResponse throwExceptionThatTemporarilyUnavailableReturnVoid(CallNotPermittedException e) {
        throw new FeignClientTemporarilyUnavailable(PASSENGER_SERVICE_IN_OPENED_STATE);
    }

}
