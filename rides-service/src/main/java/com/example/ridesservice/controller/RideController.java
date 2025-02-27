package com.example.ridesservice.controller;

import com.example.ridesservice.dto.ride.request.RideBookingRequest;
import com.example.ridesservice.dto.ride.request.RideDriverSettingRequest;
import com.example.ridesservice.dto.ride.request.RideRequest;
import com.example.ridesservice.dto.ride.request.RideStatusRequest;
import com.example.ridesservice.dto.ride.response.RidePageResponse;
import com.example.ridesservice.dto.ride.response.RideResponse;
import com.example.ridesservice.enums.PersonType;
import com.example.ridesservice.service.RideService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/rides")
@RestController
@RequiredArgsConstructor
public class RideController {

    private final RideService rideService;

    @GetMapping("/{id}")
    public ResponseEntity<RideResponse> getRide(@PathVariable("id") Long rideId) {
        RideResponse rideResponse = rideService.findById(rideId);
        return ResponseEntity.status(HttpStatus.OK).body(rideResponse);
    }

    @GetMapping
    public ResponseEntity<RidePageResponse> getAllRides(
            @RequestParam(name = "offset", defaultValue = "0") Integer offset,
            @RequestParam(name = "limit", defaultValue = "10") Integer limit
    ) {
        RidePageResponse ridePageResponse = rideService.findAll(offset, limit);
        return ResponseEntity.status(HttpStatus.OK).body(ridePageResponse);
    }

    @GetMapping("/passenger/{id}")
    public ResponseEntity<RidePageResponse> getLastPassengerRides(
            @PathVariable("id") Long passengerId,
            @RequestParam(name = "limit", defaultValue = "10") Integer limit
    ) {
        RidePageResponse ridePageResponse = rideService
                .findLastPersonRides(passengerId, limit, PersonType.PASSENGER);
        return ResponseEntity.status(HttpStatus.OK).body(ridePageResponse);
    }

    @GetMapping("/driver/{id}")
    public ResponseEntity<RidePageResponse> getLastDriverRides(
            @PathVariable("id") Long passengerId,
            @RequestParam(name = "limit", defaultValue = "10") Integer limit
    ) {
        RidePageResponse ridePageResponse = rideService
                .findLastPersonRides(passengerId, limit, PersonType.DRIVER);
        return ResponseEntity.status(HttpStatus.OK).body(ridePageResponse);
    }

    @PostMapping
    public ResponseEntity<RideResponse> createRide(@RequestBody RideRequest rideRequest) {
        RideResponse rideResponse = rideService.create(rideRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(rideResponse);
    }

    @PostMapping("/booking")
    public ResponseEntity<RideResponse> bookRide(@RequestBody RideBookingRequest rideRequest) {
        RideResponse rideResponse = rideService.bookRide(rideRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(rideResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RideResponse> updateRide(@RequestBody RideRequest rideRequest,
                                                   @PathVariable("id") Long rideId) {
        RideResponse rideResponse = rideService.update(rideRequest, rideId);
        return ResponseEntity.status(HttpStatus.OK).body(rideResponse);
    }

    @PatchMapping("/accepting")
    public ResponseEntity<RideResponse> updateRidesDriver(@RequestBody RideDriverSettingRequest rideRequest) {
        RideResponse rideResponse = rideService.setDriverToRide(rideRequest);
        return ResponseEntity.status(HttpStatus.OK).body(rideResponse);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<RideResponse> updateStatus(@RequestBody RideStatusRequest rideRequest,
                                                     @PathVariable("id") Long rideId) {
        RideResponse rideResponse = rideService.updateStatus(rideRequest, rideId);
        return ResponseEntity.status(HttpStatus.OK).body(rideResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRide(@PathVariable("id") Long rideId) {
        rideService.delete(rideId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
