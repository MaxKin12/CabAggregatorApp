package com.example.ridesservice.utility.pricecounter.impl;

import com.example.ridesservice.utility.pricecounter.PriceCounter;
import com.example.ridesservice.utility.traveltime.TravelTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class PriceCounterImpl implements PriceCounter {
    private final TravelTimeService travelTimeService;

    public BigDecimal count(String departureAddress, String arrivalAddress) {
        Integer distance = travelTimeService.countDistance(departureAddress, arrivalAddress);
        return BigDecimal.valueOf((double)(distance / 1000 + 1) * 3.5);
    }
}
