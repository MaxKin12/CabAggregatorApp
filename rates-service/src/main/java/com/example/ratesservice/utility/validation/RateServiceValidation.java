package com.example.ratesservice.utility.validation;

import com.example.ratesservice.client.dto.RidesResponse;
import com.example.ratesservice.dto.rate.RateUpdateRequest;
import com.example.ratesservice.enums.RecipientType;
import com.example.ratesservice.model.Rate;
import java.util.List;

public interface RateServiceValidation {

    Rate findByIdOrThrow(Long id);

    int cutDownLimit(int limit);

    Rate saveOrThrow(Rate rate);

    void updateOrThrow(Rate rate, RateUpdateRequest rateUpdateRequest);

    RidesResponse getRideById(Long id);

    void checkPassengerExistence(Long id);

    void checkDriverExistence(Long id);

    void checkRidesRules(RidesResponse ridesResponse, Rate rate);

    void checkRateExistence(Rate rate);

    double countAverage(List<Rate> ratePage, Long personId, RecipientType recipientType);

    List<Rate> getLastRatesPage(Long personId, RecipientType recipientType);

}
