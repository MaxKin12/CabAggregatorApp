package com.example.ridesservice.integration.controller;

import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.ID_PARAMETER_NAME;
import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.ENDPOINT_WITH_ID;
import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.LIMIT_PARAMETER_NAME;
import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.OFFSET_PARAMETER_NAME;
import static io.restassured.RestAssured.given;

import com.example.ridesservice.dto.exception.ExceptionHandlerResponse;
import com.example.ridesservice.dto.ride.request.RideRequest;
import com.example.ridesservice.dto.ride.response.RidePageResponse;
import com.example.ridesservice.dto.ride.response.RideResponse;
import io.restassured.http.ContentType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ControllerRestAssuredMethods {

    public static RideResponse getRide(UUID id) {
        return given()
                .pathParam(ID_PARAMETER_NAME, id)
                .when()
                .get(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(RideResponse.class);
    }

    public static ExceptionHandlerResponse getRideException(UUID id, HttpStatus httpStatus) {
        return given()
                .pathParam(ID_PARAMETER_NAME, id)
                .when()
                .get(ENDPOINT_WITH_ID)
                .then()
                .statusCode(httpStatus.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(ExceptionHandlerResponse.class);
    }

    public static RidePageResponse getAllRides(int offset, int limit) {
        return given()
                .queryParam(OFFSET_PARAMETER_NAME, offset)
                .queryParam(LIMIT_PARAMETER_NAME, limit)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(RidePageResponse.class);
    }

    public static ExceptionHandlerResponse getAllRidesException(int offset, int limit, HttpStatus httpStatus) {
        return given()
                .queryParam(OFFSET_PARAMETER_NAME, offset)
                .queryParam(LIMIT_PARAMETER_NAME, limit)
                .when()
                .get()
                .then()
                .statusCode(httpStatus.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(ExceptionHandlerResponse.class);
    }

    public static RideResponse createRide(RideRequest rideRequest) {
        return given()
                .contentType(ContentType.JSON)
                .body(rideRequest)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(RideResponse.class);
    }

    public static RideResponse updateRide(RideRequest rideRequest, UUID id) {
        return given()
                .contentType(ContentType.JSON)
                .body(rideRequest)
                .pathParam(ID_PARAMETER_NAME, id)
                .when()
                .patch(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(RideResponse.class);
    }

    public static ExceptionHandlerResponse updateRideException(RideRequest rideRequest,
                                                               UUID id, HttpStatus httpStatus) {
        return given()
                .contentType(ContentType.JSON)
                .body(rideRequest)
                .pathParam(ID_PARAMETER_NAME, id)
                .when()
                .patch(ENDPOINT_WITH_ID)
                .then()
                .statusCode(httpStatus.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(ExceptionHandlerResponse.class);
    }

    public static void deleteRide(UUID id) {
        given()
                .pathParam(ID_PARAMETER_NAME, id)
                .when()
                .delete(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    public static ExceptionHandlerResponse deleteRideException(UUID id, HttpStatus httpStatus) {
        return given()
                .pathParam(ID_PARAMETER_NAME, id)
                .when()
                .delete(ENDPOINT_WITH_ID)
                .then()
                .statusCode(httpStatus.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(ExceptionHandlerResponse.class);
    }

}
