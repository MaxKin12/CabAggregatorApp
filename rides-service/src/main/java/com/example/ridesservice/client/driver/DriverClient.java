package com.example.ridesservice.client.driver;

import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.DRIVER_SERVICE_IN_OPENED_STATE;

import com.example.ridesservice.dto.external.CarResponse;
import com.example.ridesservice.dto.external.DriverResponse;
import com.example.ridesservice.exception.custom.FeignClientTemporarilyUnavailable;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.UUID;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface DriverClient {

    @GetMapping("/drivers/{id}")
    @CircuitBreaker(name = "driverFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailableDriver")
    @Retry(name = "driverFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailableDriver")
    DriverResponse getDriverById(@PathVariable("id") UUID driverId);

    @GetMapping("/cars/{id}")
    @CircuitBreaker(name = "driverFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailableCar")
    @Retry(name = "driverFeignClient", fallbackMethod = "throwExceptionThatTemporarilyUnavailableCar")
    CarResponse getCarById(@PathVariable("id") UUID carId);

    default DriverResponse throwExceptionThatTemporarilyUnavailableDriver(CallNotPermittedException e) {
        throw new FeignClientTemporarilyUnavailable(DRIVER_SERVICE_IN_OPENED_STATE);
    }

    default CarResponse throwExceptionThatTemporarilyUnavailableCar(CallNotPermittedException e) {
        throw new FeignClientTemporarilyUnavailable(DRIVER_SERVICE_IN_OPENED_STATE);
    }

}
