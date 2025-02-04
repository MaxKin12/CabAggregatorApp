package com.example.ridesservice.utility.pricecounter;

import java.math.BigDecimal;

public interface PriceCounter {
    BigDecimal count(String departureAddress, String arrivalAddress);
}
