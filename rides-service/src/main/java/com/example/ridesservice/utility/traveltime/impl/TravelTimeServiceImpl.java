package com.example.ridesservice.utility.traveltime.impl;

import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.ADDRESS_NOT_FOUND;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.ADDRESS_TOO_FAR;
import static com.example.ridesservice.utility.constants.InternationalizationExceptionVariablesConstants.TIMETRAVEL_REQUEST_EXCEPTION;

import com.example.ridesservice.configuration.properties.TimetravelRouteProperties;
import com.example.ridesservice.exception.custom.TimetravelRequestException;
import com.example.ridesservice.utility.traveltime.TravelTimeService;
import com.traveltime.sdk.TravelTimeSDK;
import com.traveltime.sdk.auth.TravelTimeCredentials;
import com.traveltime.sdk.dto.common.Coordinates;
import com.traveltime.sdk.dto.common.Location;
import com.traveltime.sdk.dto.common.Property;
import com.traveltime.sdk.dto.common.transportation.Driving;
import com.traveltime.sdk.dto.requests.GeocodingRequest;
import com.traveltime.sdk.dto.requests.TimeFilterRequest;
import com.traveltime.sdk.dto.requests.timefilter.DepartureSearch;
import com.traveltime.sdk.dto.responses.GeocodingResponse;
import com.traveltime.sdk.dto.responses.TimeFilterResponse;
import com.traveltime.sdk.dto.responses.errors.TravelTimeError;
import io.vavr.control.Either;
import jakarta.annotation.PostConstruct;
import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.geojson.LngLatAlt;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(TimetravelRouteProperties.class)
public class TravelTimeServiceImpl implements TravelTimeService {

    private final TimetravelRouteProperties timetravelRouteProperties;

    private final MessageSource messageSource;

    private TravelTimeSDK sdk;

    @PostConstruct
    public void initSdk() {
        TravelTimeCredentials credentials =
                new TravelTimeCredentials(timetravelRouteProperties.appId(), timetravelRouteProperties.apiKey());
        sdk = new TravelTimeSDK(credentials);
    }

    @Override
    public Integer countDistance(String departureAddress, String arrivalAddress) {
        Location departureLocation = new Location(departureAddress, getCoordinates(departureAddress));
        Location arrivalLocation = new Location(arrivalAddress, getCoordinates(arrivalAddress));

        DepartureSearch departureSearch = DepartureSearch
                .builder()
                .id("departure search")
                .departureLocationId(departureAddress)
                .arrivalLocationIds(List.of(arrivalAddress))
                .transportation(Driving.builder().build())
                .departureTime(Instant.now())
                .travelTime(14400)
                .properties(List.of(Property.DISTANCE))
                .build();

        TimeFilterRequest request = TimeFilterRequest
                .builder()
                .locations(Arrays.asList(departureLocation, arrivalLocation))
                .departureSearches(Collections.singletonList(departureSearch))
                .build();

        Either<TravelTimeError, TimeFilterResponse> response = sdk.send(request);

        if (!response.isRight()) {
            throw new TimetravelRequestException(getTimetravelRequestExceptionMessage(response.getLeft().getMessage()));
        }
        if (!response.get().getResults().get(0).getUnreachable().isEmpty()) {
            throw new TimetravelRequestException(getAddressesTooFarMessage(departureAddress, arrivalAddress));
        }
        return response.get().getResults().get(0).getLocations().get(0).getProperties().get(0).getDistance();
    }

    private Coordinates getCoordinates(String address) {
        GeocodingRequest request = GeocodingRequest
                .builder()
                .query(address)
                .withinCountry(timetravelRouteProperties.countryCode())
                .limit(1)
                .build();
        Either<TravelTimeError, GeocodingResponse> response = sdk.send(request);

        if (!response.isRight()) {
            throw new TimetravelRequestException(getTimetravelRequestExceptionMessage(response.getLeft().getMessage()));
        }
        if (response.get().getFeatures().isEmpty()) {
            throw new TimetravelRequestException(getAddressNotFoundExceptionMessage(address));
        }

        LngLatAlt lngLatAlt = response.get().getFeatures().get(0).getGeometry().getCoordinates();
        return new Coordinates(lngLatAlt.getLatitude(), lngLatAlt.getLongitude());
    }

    private String getTimetravelRequestExceptionMessage(String exceptionMessage) {
        return messageSource
                .getMessage(TIMETRAVEL_REQUEST_EXCEPTION, new Object[] {exceptionMessage},
                        LocaleContextHolder.getLocale());
    }

    private String getAddressNotFoundExceptionMessage(String address) {
        return messageSource
                .getMessage(ADDRESS_NOT_FOUND, new Object[] {address},
                        LocaleContextHolder.getLocale());
    }

    private String getAddressesTooFarMessage(String address1, String address2) {
        return messageSource
                .getMessage(ADDRESS_TOO_FAR, new Object[] {address1, address2},
                        LocaleContextHolder.getLocale());
    }

}
