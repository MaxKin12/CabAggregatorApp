package com.example.ridesservice.utility.pricecounter.impl;

import static com.example.ridesservice.utility.constants.PriceCounterConstants.METERS_IN_KILOMETER;
import static com.example.ridesservice.utility.constants.PriceCounterConstants.PRICE_DECIMAL_SCALE;
import static com.example.ridesservice.utility.constants.PriceCounterConstants.PRICE_FOR_KILOMETER;

import com.example.ridesservice.utility.pricecounter.PriceCounter;
import com.example.ridesservice.utility.traveltime.TravelTimeService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PriceCounterImpl implements PriceCounter {

    private final TravelTimeService travelTimeService;

    public BigDecimal count(String departureAddress, String arrivalAddress) {
        Integer distance = travelTimeService.countDistance(departureAddress, arrivalAddress);
        BigDecimal price = BigDecimal.valueOf((double) (distance / METERS_IN_KILOMETER + 1) * PRICE_FOR_KILOMETER);
        return price.setScale(PRICE_DECIMAL_SCALE, RoundingMode.CEILING);
    }

}
