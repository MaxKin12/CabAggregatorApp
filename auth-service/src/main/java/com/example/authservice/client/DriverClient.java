package com.example.authservice.client;

import static com.example.authservice.utility.constants.InternationalizationExceptionVariablesConstants.DRIVER_SERVICE_IN_OPENED_STATE;

import com.example.authservice.client.decoder.ExternalServiceClientDecoder;
import com.example.authservice.dto.external.DriverResponse;
import com.example.authservice.dto.external.ExternalEntityRequest;
import com.example.authservice.exception.custom.FeignClientTemporarilyUnavailable;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "driver-service",
        path = "/api/v1/drivers",
        configuration = {ExternalServiceClientDecoder.class}
)
public interface DriverClient {

    @PostMapping
    @CircuitBreaker(name = "driverFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailableDriver")
    @Retry(name = "driverFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailableDriver")
    DriverResponse createDriver(@RequestBody ExternalEntityRequest driverRequest);

    default DriverResponse throwExceptionThatTemporarilyUnavailableDriver(CallNotPermittedException e) {
        throw new FeignClientTemporarilyUnavailable(DRIVER_SERVICE_IN_OPENED_STATE);
    }

}
