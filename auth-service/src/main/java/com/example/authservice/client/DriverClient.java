package com.example.authservice.client;

import static com.example.authservice.utility.constants.InternationalizationExceptionVariablesConstants.DRIVER_SERVICE_IN_OPENED_STATE;

import com.example.authservice.client.decoder.ExternalServiceClientDecoder;
import com.example.authservice.dto.external.DriverResponse;
import com.example.authservice.dto.external.ExternalEntityRequest;
import com.example.authservice.dto.external.PassengerResponse;
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
        name = "driver-service",
        path = "/api/v1/drivers",
        configuration = {ExternalServiceClientDecoder.class}
)
public interface DriverClient {

    @PostMapping
    @CircuitBreaker(name = "driverFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    @Retry(name = "driverFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    DriverResponse createDriver(@RequestBody ExternalEntityRequest driverRequest);

    @PatchMapping("/{id}")
    @CircuitBreaker(name = "driverFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    @Retry(name = "driverFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailable")
    DriverResponse updateDriver(@RequestBody ExternalEntityRequest driverRequest, @PathVariable("id") UUID driverId);

    @DeleteMapping("/{id}")
    @CircuitBreaker(name = "driverFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailableReturnVoid")
    @Retry(name = "driverFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailableReturnVoid")
    void deleteDriver(@PathVariable("id") UUID driverId);

    default DriverResponse throwExceptionThatTemporarilyUnavailable(CallNotPermittedException e) {
        throw new FeignClientTemporarilyUnavailable(DRIVER_SERVICE_IN_OPENED_STATE);
    }

    default void throwExceptionThatTemporarilyUnavailableReturnVoid(CallNotPermittedException e) {
        throw new FeignClientTemporarilyUnavailable(DRIVER_SERVICE_IN_OPENED_STATE);
    }

}
