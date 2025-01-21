package com.example.passengerservice.controller;

import com.example.passengerservice.dto.PassengerDto;
import com.example.passengerservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/passengers")
@RestController
@RequiredArgsConstructor
public class PassengerServiceController {
    private final PassengerService passengerService;

    @GetMapping("/{id}")
    public ResponseEntity<PassengerDto> getPassenger(@PathVariable("id") Long passengerId) {
        PassengerDto passengerDto = passengerService.findById(passengerId);
        return ResponseEntity.status(HttpStatus.OK).body(passengerDto);
    }
}
