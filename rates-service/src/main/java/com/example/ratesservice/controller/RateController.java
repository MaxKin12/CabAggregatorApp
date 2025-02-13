package com.example.ratesservice.controller;

import com.example.ratesservice.dto.RateAverageResponse;
import com.example.ratesservice.dto.RatePageResponse;
import com.example.ratesservice.dto.RateRequest;
import com.example.ratesservice.dto.RateResponse;
import com.example.ratesservice.enums.AuthorType;
import com.example.ratesservice.service.RateService;
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

@RequestMapping("/api/v1/rates")
@RestController
@RequiredArgsConstructor
public class RateController {

    private final RateService rateService;

    @GetMapping("/{id}")
    public ResponseEntity<RateResponse> getRate(@PathVariable("id") Long rateId) {
        RateResponse rateResponse = rateService.findById(rateId);
        return ResponseEntity.status(HttpStatus.OK).body(rateResponse);
    }

    @GetMapping("/passengers")
    public ResponseEntity<RatePageResponse> getAllPassengersRates(
            @RequestParam(name = "offset", defaultValue = "0") Integer offset,
            @RequestParam(name = "limit", defaultValue = "10") Integer limit
    ) {
        RatePageResponse ratePageResponse = rateService.findAllByAuthor(offset, limit, AuthorType.DRIVER);
        return ResponseEntity.status(HttpStatus.OK).body(ratePageResponse);
    }

    @GetMapping("/drivers")
    public ResponseEntity<RatePageResponse> getAllDriversRates(
            @RequestParam(name = "offset", defaultValue = "0") Integer offset,
            @RequestParam(name = "limit", defaultValue = "10") Integer limit
    ) {
        RatePageResponse ratePageResponse = rateService.findAllByAuthor(offset, limit, AuthorType.PASSENGER);
        return ResponseEntity.status(HttpStatus.OK).body(ratePageResponse);
    }

    @GetMapping("/passengers/{id}")
    public ResponseEntity<RateAverageResponse> getAveragePassengerRate(@PathVariable("id") Long passengerId) {
        RateAverageResponse rateAverageResponse = rateService.findAveragePassengerRate(passengerId);
        return ResponseEntity.status(HttpStatus.OK).body(rateAverageResponse);
    }

    @GetMapping("/drivers/{id}")
    public ResponseEntity<RateAverageResponse> getAverageDriverRate(@PathVariable("id") Long driverId) {
        RateAverageResponse rateAverageResponse = rateService.findAverageDriverRate(driverId);
        return ResponseEntity.status(HttpStatus.OK).body(rateAverageResponse);
    }

    @PostMapping
    public ResponseEntity<RateResponse> rateRide(@RequestBody RateRequest rateRequest) {
        RateResponse rateResponse = rateService.create(rateRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(rateResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<RateResponse> updateRate(@RequestBody RateRequest rateRequest,
                                                   @PathVariable("id") Long rateId) {
        RateResponse rateResponse = rateService.update(rateRequest, rateId);
        return ResponseEntity.status(HttpStatus.OK).body(rateResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRate(@PathVariable("id") Long rateId) {
        rateService.delete(rateId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
