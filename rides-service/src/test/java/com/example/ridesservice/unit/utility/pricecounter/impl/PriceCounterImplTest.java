package com.example.ridesservice.unit.utility.pricecounter.impl;

import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.TEST_DISTANCE;
import static com.example.ridesservice.configuration.constants.RideTestData.DESTINATION_ADDRESS;
import static com.example.ridesservice.configuration.constants.RideTestData.PICK_UP_ADDRESS;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.TIMETRAVEL_REQUEST_EXCEPTION;
import static com.example.ridesservice.utility.constants.PriceCounterConstants.METERS_IN_KILOMETER;
import static com.example.ridesservice.utility.constants.PriceCounterConstants.PRICE_DECIMAL_SCALE;
import static com.example.ridesservice.utility.constants.PriceCounterConstants.PRICE_FOR_KILOMETER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        String departureAddress = PICK_UP_ADDRESS;
        String arrivalAddress = DESTINATION_ADDRESS;
        int distanceInMeters = TEST_DISTANCE;
        BigDecimal expectedPrice = BigDecimal.valueOf((double)
                        (distanceInMeters / METERS_IN_KILOMETER + 1) * PRICE_FOR_KILOMETER)
                .setScale(PRICE_DECIMAL_SCALE, RoundingMode.CEILING);

        when(travelTimeService.countDistance(departureAddress, arrivalAddress)).thenReturn(distanceInMeters);

        BigDecimal result = priceCounter.count(departureAddress, arrivalAddress);

        assertThat(result).isEqualTo(expectedPrice);
        verify(travelTimeService).countDistance(departureAddress, arrivalAddress);
    }

    @Test
    void countTest_NullAddresses_ThrowsException() {
        String departureAddress = null;
        String arrivalAddress = null;
        String[] args = new String[]{departureAddress, arrivalAddress};

        when(travelTimeService.countDistance(departureAddress, arrivalAddress))
                .thenThrow(new TimetravelRequestException(TIMETRAVEL_REQUEST_EXCEPTION, args));

        assertThatExceptionOfType(TimetravelRequestException.class)
                .isThrownBy(() -> priceCounter.count(departureAddress, arrivalAddress))
                .withMessage(TIMETRAVEL_REQUEST_EXCEPTION)
                .satisfies(e -> assertThat(e.getArgs()).isEqualTo(args));

        verify(travelTimeService).countDistance(departureAddress, arrivalAddress);
    }

}
