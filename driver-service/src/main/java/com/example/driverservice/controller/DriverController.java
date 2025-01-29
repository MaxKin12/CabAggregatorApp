package com.example.driverservice.controller;

import com.example.driverservice.dto.driver.DriverResponseList;
import com.example.driverservice.dto.driver.DriverRequest;
import com.example.driverservice.dto.driver.DriverResponse;
import com.example.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/drivers")
@RestController
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponse> getDriver(@PathVariable("id") Long driverId) {
        DriverResponse driverResponse = driverService.findById(driverId);
        return ResponseEntity.status(HttpStatus.OK).body(driverResponse);
    }

    @GetMapping
    public ResponseEntity<DriverResponseList> getAllDrivers() {
        DriverResponseList driverResponseList = driverService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(driverResponseList);
    }

    @PostMapping
    public ResponseEntity<DriverResponse> createDriver(@RequestBody DriverRequest driverRequest) {
        DriverResponse driverResponse = driverService.create(driverRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(driverResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DriverResponse> updateDriver(@RequestBody DriverRequest driverRequest,
                                                       @PathVariable("id") Long driverId) {
        DriverResponse driverResponse = driverService.update(driverRequest, driverId);
        return ResponseEntity.status(HttpStatus.OK).body(driverResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable("id") Long driverId) {
        driverService.delete(driverId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
