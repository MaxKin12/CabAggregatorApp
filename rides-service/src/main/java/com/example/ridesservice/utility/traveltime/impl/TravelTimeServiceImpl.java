package com.example.ridesservice.utility.traveltime.impl;

import com.example.ridesservice.exception.TimetravelRequestException;
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
import lombok.RequiredArgsConstructor;
import org.geojson.LngLatAlt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TravelTimeServiceImpl implements TravelTimeService {
    private final MessageSource messageSource;

    @Value("${timetravel.appid}")
    private String TIMETRAVEL_APPID;
    @Value("${timetravel.apikey}")
    private String TIMETRAVEL_APIKEY;
    @Value("${timetravel.country.code}")
    private String COUNTRY_CODE;

    private TravelTimeSDK sdk;

    @PostConstruct
    public void initSdk() {
        TravelTimeCredentials credentials = new TravelTimeCredentials(TIMETRAVEL_APPID, TIMETRAVEL_APIKEY);
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

        if (!response.isRight())
            throw new TimetravelRequestException(messageSource
                    .getMessage("exception.timetravel.request", new Object[] {response.getLeft().getMessage()},
                            LocaleContextHolder.getLocale()));
        return response.get().getResults().get(0).getLocations().get(0).getProperties().get(0).getDistance();
    }

    private Coordinates getCoordinates(String address) {
        GeocodingRequest request = GeocodingRequest
                .builder()
                .query(address)
                .withinCountry(COUNTRY_CODE)
                .limit(1)
                .build();
        Either<TravelTimeError, GeocodingResponse> response = sdk.send(request);

        if (!response.isRight())
            throw new TimetravelRequestException(messageSource
                    .getMessage("exception.timetravel.request", new Object[] {response.getLeft().getMessage()},
                            LocaleContextHolder.getLocale()));
        if (response.get().getFeatures().isEmpty())
            throw new TimetravelRequestException(messageSource
                    .getMessage("exception.address.not.found", new Object[] {address},
                            LocaleContextHolder.getLocale()));

        LngLatAlt lngLatAlt = response.get().getFeatures().get(0).getGeometry().getCoordinates();
        return new Coordinates(lngLatAlt.getLatitude(), lngLatAlt.getLongitude());
    }
}
