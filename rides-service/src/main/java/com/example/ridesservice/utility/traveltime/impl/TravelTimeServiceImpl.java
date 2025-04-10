package com.example.ridesservice.utility.traveltime.impl;

import com.example.ridesservice.configuration.properties.TimetravelRouteProperties;
import com.example.ridesservice.utility.traveltime.TravelTimeService;
import com.example.ridesservice.utility.validation.TravelTimeServiceValidation;
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
import lombok.extern.slf4j.Slf4j;
import org.geojson.LngLatAlt;
import org.springframework.stereotype.Service;

import static com.example.ridesservice.utility.constants.LogMessagesTemplate.TRAVEL_TIME_RESPONSE_LOG_TEMPLATE;

@Service
@Slf4j
@RequiredArgsConstructor
public class TravelTimeServiceImpl implements TravelTimeService {

    private final TimetravelRouteProperties timetravelRouteProperties;
    private final TravelTimeServiceValidation validation;
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
        validation.checkIfFilterExceptionThrown(response);
        validation.checkIfUnreachable(response, departureAddress, arrivalAddress);

        int distance = response.get().getResults().get(0).getLocations().get(0).getProperties().get(0).getDistance();
        log.info(TRAVEL_TIME_RESPONSE_LOG_TEMPLATE, distance);
        return distance;
    }

    private Coordinates getCoordinates(String address) {
        GeocodingRequest request = GeocodingRequest
                .builder()
                .query(address)
                .withinCountry(timetravelRouteProperties.countryCode())
                .limit(1)
                .build();
        Either<TravelTimeError, GeocodingResponse> response = sdk.send(request);
        validation.checkIfGeocodingExceptionThrown(response);
        validation.checkIfAddressExists(response, address);

        LngLatAlt lngLatAlt = response.get().getFeatures().get(0).getGeometry().getCoordinates();
        return new Coordinates(lngLatAlt.getLatitude(), lngLatAlt.getLongitude());
    }

}
