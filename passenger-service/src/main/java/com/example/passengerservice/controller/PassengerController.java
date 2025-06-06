package com.example.passengerservice.controller;

import com.example.passengerservice.dto.passenger.PassengerPageResponse;
import com.example.passengerservice.dto.passenger.PassengerRequest;
import com.example.passengerservice.dto.passenger.PassengerResponse;
import com.example.passengerservice.service.PassengerService;
import java.util.UUID;
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

@RequestMapping("/api/v1/passengers")
@RestController
@RequiredArgsConstructor
public class PassengerController {

    private final PassengerService passengerService;

    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponse> getPassenger(@PathVariable("id") UUID passengerId) {
        PassengerResponse passengerResponse = passengerService.findById(passengerId);
        return ResponseEntity.status(HttpStatus.OK).body(passengerResponse);
    }

    @GetMapping
    public ResponseEntity<PassengerPageResponse> getAllPassengers(
            @RequestParam(name = "offset", defaultValue = "0") Integer offset,
            @RequestParam(name = "limit", defaultValue = "10") Integer limit
    ) {
        PassengerPageResponse passengerPageResponse = passengerService.findAll(offset, limit);
        return ResponseEntity.status(HttpStatus.OK).body(passengerPageResponse);
    }

    @PostMapping
    public ResponseEntity<PassengerResponse> createPassenger(@RequestBody PassengerRequest passengerRequest) {
        PassengerResponse passengerResponse = passengerService.create(passengerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(passengerResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PassengerResponse> updatePassenger(@RequestBody PassengerRequest passengerRequest,
                                                             @PathVariable("id") UUID passengerId) {
        PassengerResponse passengerResponse = passengerService.updatePassenger(passengerRequest, passengerId);
        return ResponseEntity.status(HttpStatus.OK).body(passengerResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable("id") UUID passengerId) {
        passengerService.delete(passengerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
