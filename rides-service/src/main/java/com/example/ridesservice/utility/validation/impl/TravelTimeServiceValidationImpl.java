package com.example.ridesservice.utility.validation.impl;

import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.ADDRESS_NOT_FOUND;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.ADDRESS_TOO_FAR;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.TIMETRAVEL_REQUEST_EXCEPTION;

import com.example.ridesservice.exception.custom.TimetravelRequestException;
import com.example.ridesservice.utility.validation.TravelTimeServiceValidation;
import com.traveltime.sdk.dto.responses.GeocodingResponse;
import com.traveltime.sdk.dto.responses.TimeFilterResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import org.springframework.stereotype.Service;

@Service
public class TravelTimeServiceValidationImpl implements TravelTimeServiceValidation {

    @Override
    public void checkIfFilterExceptionThrown(Either<TravelTimeError, TimeFilterResponse> response) {
        if (!response.isRight()) {
            throw new TimetravelRequestException(TIMETRAVEL_REQUEST_EXCEPTION, response.getLeft().getMessage());
        }
    }

    @Override
    public void checkIfGeocodingExceptionThrown(Either<TravelTimeError, GeocodingResponse> response) {
        if (!response.isRight()) {
            throw new TimetravelRequestException(TIMETRAVEL_REQUEST_EXCEPTION, response.getLeft().getMessage());
        }
    }

    @Override
    public void checkIfUnreachable(Either<TravelTimeError, TimeFilterResponse> response,
                                   String departureAddress,
                                   String arrivalAddress) {
        if (!response.get().getResults().get(0).getUnreachable().isEmpty()) {
            throw new TimetravelRequestException(ADDRESS_TOO_FAR, departureAddress, arrivalAddress);
        }
    }

    @Override
    public void checkIfAddressExists(Either<TravelTimeError, GeocodingResponse> response, String address) {
        if (response.get().getFeatures().isEmpty()) {
            throw new TimetravelRequestException(ADDRESS_NOT_FOUND, address);
        }
    }

}
