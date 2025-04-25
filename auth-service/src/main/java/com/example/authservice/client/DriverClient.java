package com.example.authservice.client;

import static com.example.authservice.utility.constants.InternationalizationExceptionVariablesConstants.DRIVER_SERVICE_IN_OPENED_STATE;

import com.example.authservice.client.decoder.ExternalServiceClientDecoder;
import com.example.authservice.dto.external.DriverResponse;
import com.example.authservice.dto.external.ExternalEntityRequest;
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
    DriverResponse createDriver(@RequestBody ExternalEntityRequest driverRequest);

    @PatchMapping("/{id}")
    DriverResponse updateDriver(@RequestBody ExternalEntityRequest driverRequest, @PathVariable("id") UUID driverId);

    @DeleteMapping("/{id}")
    void deleteDriver(@PathVariable("id") UUID driverId);

}
