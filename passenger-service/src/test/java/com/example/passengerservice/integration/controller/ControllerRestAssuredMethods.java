package com.example.passengerservice.integration.controller;

import static com.example.passengerservice.configuration.constants.GeneralUtilityConstants.ENDPOINT_WITH_ID;
import static com.example.passengerservice.configuration.constants.GeneralUtilityConstants.ID_PARAMETER_NAME;
import static com.example.passengerservice.configuration.constants.GeneralUtilityConstants.LIMIT_PARAMETER_NAME;
import static com.example.passengerservice.configuration.constants.GeneralUtilityConstants.OFFSET_PARAMETER_NAME;
import static io.restassured.RestAssured.given;

import com.example.passengerservice.dto.exception.ExceptionHandlerResponse;
import com.example.passengerservice.dto.passenger.PassengerPageResponse;
import com.example.passengerservice.dto.passenger.PassengerRequest;
import com.example.passengerservice.dto.passenger.PassengerResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ControllerRestAssuredMethods {

    public static PassengerResponse getPassenger(UUID id) {
        Response response = given()
                .pathParam(ID_PARAMETER_NAME, id)
                .when()
                .get(ENDPOINT_WITH_ID);
        return getResponse(response, HttpStatus.OK, PassengerResponse.class);
    }

    public static ExceptionHandlerResponse getPassengerException(UUID id, HttpStatus httpStatus) {
        Response response = given()
                .pathParam(ID_PARAMETER_NAME, id)
                .when()
                .get(ENDPOINT_WITH_ID);
        return getResponse(response, httpStatus, ExceptionHandlerResponse.class);
    }

    public static PassengerPageResponse getAllPassengers(int offset, int limit) {
        Response response = given()
                .queryParam(OFFSET_PARAMETER_NAME, offset)
                .queryParam(LIMIT_PARAMETER_NAME, limit)
                .when()
                .get();
        return getResponse(response, HttpStatus.OK, PassengerPageResponse.class);
    }

    public static ExceptionHandlerResponse getAllPassengersException(int offset, int limit, HttpStatus httpStatus) {
        Response response = given()
                .queryParam(OFFSET_PARAMETER_NAME, offset)
                .queryParam(LIMIT_PARAMETER_NAME, limit)
                .when()
                .get();
        return getResponse(response, httpStatus, ExceptionHandlerResponse.class);
    }

    public static PassengerResponse createPassenger(PassengerRequest passengerRequest) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(passengerRequest)
                .when()
                .post();
        return getResponse(response, HttpStatus.CREATED, PassengerResponse.class);
    }

    public static ExceptionHandlerResponse createPassengerException(PassengerRequest passengerRequest,
                                                                    HttpStatus httpStatus) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(passengerRequest)
                .when()
                .post();
        return getResponse(response, httpStatus, ExceptionHandlerResponse.class);
    }

    public static PassengerResponse updatePassenger(PassengerRequest passengerRequest, UUID id) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(passengerRequest)
                .pathParam(ID_PARAMETER_NAME, id)
                .when()
                .patch(ENDPOINT_WITH_ID);
        return getResponse(response, HttpStatus.OK, PassengerResponse.class);
    }

    public static ExceptionHandlerResponse updatePassengerException(PassengerRequest passengerRequest,
                                                                    UUID id, HttpStatus httpStatus) {
        Response response = given()
                .contentType(ContentType.JSON)
                .body(passengerRequest)
                .pathParam(ID_PARAMETER_NAME, id)
                .when()
                .patch(ENDPOINT_WITH_ID);
        return getResponse(response, httpStatus, ExceptionHandlerResponse.class);
    }

    public static void deletePassenger(UUID id) {
        given()
                .pathParam(ID_PARAMETER_NAME, id)
                .when()
                .delete(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    public static ExceptionHandlerResponse deletePassengerException(UUID id, HttpStatus httpStatus) {
        Response response = given()
                .pathParam(ID_PARAMETER_NAME, id)
                .when()
                .delete(ENDPOINT_WITH_ID);
        return getResponse(response, httpStatus, ExceptionHandlerResponse.class);
    }

    private static <T> T getResponse(Response response, HttpStatus httpStatus, Class<T> clazz) {
        return response
                .then()
                .statusCode(httpStatus.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(clazz);
    }

}
