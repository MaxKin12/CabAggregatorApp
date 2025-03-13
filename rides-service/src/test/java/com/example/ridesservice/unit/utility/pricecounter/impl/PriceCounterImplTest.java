package com.example.ridesservice.unit.utility.pricecounter.impl;

import static com.example.ridesservice.configuration.constants.RideTestData.RIDE;
import static com.example.ridesservice.utility.constants.PriceCounterConstants.METERS_IN_KILOMETER;
import static com.example.ridesservice.utility.constants.PriceCounterConstants.PRICE_DECIMAL_SCALE;
import static com.example.ridesservice.utility.constants.PriceCounterConstants.PRICE_FOR_KILOMETER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.example.ridesservice.exception.custom.TimetravelRequestException;
import com.example.ridesservice.utility.pricecounter.impl.PriceCounterImpl;
import com.example.ridesservice.utility.traveltime.TravelTimeService;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PriceCounterImplTest {

    @Mock
    private TravelTimeService travelTimeService;

    @InjectMocks
    private PriceCounterImpl priceCounter;

    @Test
    void countTest_ValidAddresses_ReturnsCorrectPrice() {
        String departureAddress = RIDE.getPickUpAddress();
        String arrivalAddress = RIDE.getDestinationAddress();
        int distanceInMeters = 5000;
        BigDecimal expectedPrice = BigDecimal.valueOf((double)
                        (distanceInMeters / METERS_IN_KILOMETER + 1) * PRICE_FOR_KILOMETER)
                .setScale(PRICE_DECIMAL_SCALE, RoundingMode.CEILING);

        when(travelTimeService.countDistance(departureAddress, arrivalAddress)).thenReturn(distanceInMeters);

        BigDecimal result = priceCounter.count(departureAddress, arrivalAddress);

        assertEquals(expectedPrice, result);
        verify(travelTimeService).countDistance(departureAddress, arrivalAddress);
    }

    @Test
    void countTest_NullAddresses_ThrowsException() {
        String departureAddress = null;
        String arrivalAddress = null;

        when(travelTimeService.countDistance(departureAddress, arrivalAddress))
                .thenThrow(new TimetravelRequestException("Addresses cannot be null"));

        assertThrows(TimetravelRequestException.class, () -> priceCounter.count(departureAddress, arrivalAddress));

        verify(travelTimeService).countDistance(departureAddress, arrivalAddress);
    }

}
