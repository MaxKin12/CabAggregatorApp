package com.example.authservice.service;

import com.example.authservice.dto.external.DriverResponse;
import com.example.authservice.dto.external.ExternalEntityRequest;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.UUID;

public interface ResilientDriverClientService {

    @CircuitBreaker(name = "driverFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    @Retry(name = "driverFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    DriverResponse createDriver(ExternalEntityRequest driverRequest);

    @CircuitBreaker(name = "driverFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    @Retry(name = "driverFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    DriverResponse updateDriver(ExternalEntityRequest driverRequest, UUID driverId);

    @CircuitBreaker(name = "driverFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailableReturnVoid")
    @Retry(name = "driverFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailableReturnVoid")
    void deleteDriver(UUID driverId);

}
