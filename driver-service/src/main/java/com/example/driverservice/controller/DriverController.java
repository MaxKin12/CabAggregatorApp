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

@RequestMapping("/api/v1/passengers")
@RestController
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponse> getDriver(@PathVariable("id") Long passengerId) {
        DriverResponse passengerResponse = driverService.findById(passengerId);
        return ResponseEntity.status(HttpStatus.OK).body(passengerResponse);
    }

    @GetMapping("")
    public ResponseEntity<DriverResponseList> getAllDrivers() {
        DriverResponseList passengerList = driverService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(passengerList);
    }

    @PostMapping("")
    public ResponseEntity<DriverResponse> createDriver(@RequestBody DriverRequest passengerRequest) {
        DriverResponse passengerResponse = driverService.create(passengerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(passengerResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DriverResponse> updateDriver(@RequestBody DriverRequest passengerRequest,
                                                             @PathVariable("id") Long passengerId) {
        DriverResponse passengerResponse = driverService.update(passengerRequest, passengerId);
        return ResponseEntity.status(HttpStatus.OK).body(passengerResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable("id") Long passengerId) {
        driverService.delete(passengerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
