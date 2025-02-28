package com.example.ridesservice.utility.validation;

import com.traveltime.sdk.dto.responses.GeocodingResponse;
import com.traveltime.sdk.dto.responses.TimeFilterResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;

public interface TravelTimeServiceValidation {

    void checkIfFilterExceptionThrown(Either<TravelTimeError, TimeFilterResponse> response);

    void checkIfGeocodingExceptionThrown(Either<TravelTimeError, GeocodingResponse> response);

    void checkIfUnreachable(Either<TravelTimeError, TimeFilterResponse> response,
                            String departureAddress,
                            String arrivalAddress);

    void checkIfAddressExists(Either<TravelTimeError, GeocodingResponse> response, String address);

}
