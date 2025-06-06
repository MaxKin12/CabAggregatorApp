package com.example.driverservice.controller;

import com.example.driverservice.dto.car.CarRequest;
import com.example.driverservice.dto.car.CarResponse;
import com.example.driverservice.dto.page.PageResponse;
import com.example.driverservice.service.CarService;
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

@RequestMapping("/api/v1/cars")
@RestController
@RequiredArgsConstructor
public class CarController {

    private final CarService carService;

    @GetMapping("/{id}")
    public ResponseEntity<CarResponse> getCar(@PathVariable("id") UUID carId) {
        CarResponse carResponse = carService.findById(carId);
        return ResponseEntity.status(HttpStatus.OK).body(carResponse);
    }

    @GetMapping
    public ResponseEntity<PageResponse> getAllCars(
            @RequestParam(name = "offset", defaultValue = "0") Integer offset,
            @RequestParam(name = "limit", defaultValue = "10") Integer limit
    ) {
        PageResponse carPageResponse = carService.findAll(offset, limit);
        return ResponseEntity.status(HttpStatus.OK).body(carPageResponse);
    }

    @PostMapping
    public ResponseEntity<CarResponse> createCar(@RequestBody CarRequest carRequest) {
        CarResponse carResponse = carService.create(carRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(carResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CarResponse> updateCar(@RequestBody CarRequest carRequest,
                                                 @PathVariable("id") UUID carId) {
        CarResponse carResponse = carService.update(carRequest, carId);
        return ResponseEntity.status(HttpStatus.OK).body(carResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable("id") UUID carId) {
        carService.delete(carId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
