package com.example.passengerservice.controller;

import com.example.passengerservice.dto.PassengerListDto;
import com.example.passengerservice.dto.PassengerRequestDto;
import com.example.passengerservice.dto.PassengerResponseDto;
import com.example.passengerservice.service.PassengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/passengers")
@RestController
@RequiredArgsConstructor
public class PassengerServiceController {
    private final PassengerService passengerService;

    @GetMapping("/{id}")
    public ResponseEntity<PassengerResponseDto> getPassenger(@PathVariable("id") Long passengerId) {
        PassengerResponseDto passengerResponseDto = passengerService.findById(passengerId);
        return ResponseEntity.status(HttpStatus.OK).body(passengerResponseDto);
    }

    @GetMapping("")
    public ResponseEntity<PassengerListDto> getAllPassengers() {
        PassengerListDto passengerListDto = passengerService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(passengerListDto);
    }

    @PostMapping("")
    public ResponseEntity<PassengerResponseDto> createPassenger(@RequestBody PassengerRequestDto passengerRequestDto) {
        PassengerResponseDto passengerResponseDto = passengerService.create(passengerRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(passengerResponseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PassengerResponseDto> updatePassenger(@RequestBody PassengerRequestDto passengerRequestDto,
                                                          @PathVariable("id") Long passengerId) {
        PassengerResponseDto passengerResponseDto = passengerService.update(passengerRequestDto, passengerId);
        return ResponseEntity.status(HttpStatus.OK).body(passengerResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable("id") Long passengerId) {
        passengerService.delete(passengerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
