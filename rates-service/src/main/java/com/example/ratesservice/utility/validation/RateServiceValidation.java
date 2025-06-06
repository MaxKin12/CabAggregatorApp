package com.example.ratesservice.utility.validation;

import com.example.ratesservice.dto.external.RidesResponse;
import com.example.ratesservice.dto.rate.request.RateUpdateRequest;
import com.example.ratesservice.enums.RecipientType;
import com.example.ratesservice.model.Rate;
import java.util.List;
import java.util.UUID;

public interface RateServiceValidation {

    Rate findByIdOrThrow(UUID id);

    int cutDownLimit(int limit);

    Rate saveOrThrow(Rate rate);

    void updateOrThrow(Rate rate, RateUpdateRequest rateUpdateRequest);

    RidesResponse getRideById(UUID id);

    void checkPassengerExistence(UUID id);

    void checkDriverExistence(UUID id);

    void checkRidesRules(RidesResponse ridesResponse, Rate rate);

    void checkRateExistence(Rate rate);

    double countAverage(List<Rate> ratePage, UUID personId, RecipientType recipientType);

    List<Rate> getLastRatesPage(UUID personId, RecipientType recipientType);

}
