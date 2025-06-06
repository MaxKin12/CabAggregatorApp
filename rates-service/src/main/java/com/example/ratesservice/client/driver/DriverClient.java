package com.example.ratesservice.client.driver;

import static com.example.ratesservice.utility.constants.InternationalizationExceptionPropertyVariablesConstants.DRIVER_SERVICE_IN_OPENED_STATE;

import com.example.ratesservice.dto.external.DriverResponse;
import com.example.ratesservice.exception.custom.FeignClientTemporarilyUnavailable;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface DriverClient {

    @GetMapping("/{id}")
    @CircuitBreaker(name = "driverFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    @Retry(name = "driverFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    DriverResponse getDriverById(@PathVariable("id") UUID driverId);

    default DriverResponse throwExceptionThatTemporarilyUnavailable(CallNotPermittedException e) {
        throw new FeignClientTemporarilyUnavailable(DRIVER_SERVICE_IN_OPENED_STATE);
    }

}
