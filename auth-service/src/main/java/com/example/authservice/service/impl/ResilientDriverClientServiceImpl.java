package com.example.authservice.service.impl;

import static com.example.authservice.utility.constants.InternationalizationExceptionVariablesConstants.DRIVER_SERVICE_IN_OPENED_STATE;

import com.example.authservice.client.DriverClient;
import com.example.authservice.dto.external.DriverResponse;
import com.example.authservice.dto.external.ExternalEntityRequest;
import com.example.authservice.exception.custom.FeignClientTemporarilyUnavailable;
import com.example.authservice.service.ResilientDriverClientService;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResilientDriverClientServiceImpl implements ResilientDriverClientService {

    private final DriverClient driverClient;

    @Override
    @CircuitBreaker(name = "driverFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    @Retry(name = "driverFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    public DriverResponse createDriver(ExternalEntityRequest driverRequest) {
        return driverClient.createDriver(driverRequest);
    }

    @Override
    @CircuitBreaker(name = "driverFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    @Retry(name = "driverFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    public DriverResponse updateDriver(ExternalEntityRequest driverRequest, UUID driverId) {
        return driverClient.updateDriver(driverRequest, driverId);
    }

    @Override
    @CircuitBreaker(name = "driverFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailableReturnVoid")
    @Retry(name = "driverFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailableReturnVoid")
    public void deleteDriver(UUID driverId) {
        driverClient.deleteDriver(driverId);
    }

    private DriverResponse throwExceptionThatTemporarilyUnavailable(CallNotPermittedException e) {
        throw new FeignClientTemporarilyUnavailable(DRIVER_SERVICE_IN_OPENED_STATE);
    }

    private void throwExceptionThatTemporarilyUnavailableReturnVoid(CallNotPermittedException e) {
        throw new FeignClientTemporarilyUnavailable(DRIVER_SERVICE_IN_OPENED_STATE);
    }

}
