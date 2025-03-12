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
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ControllerRestAssuredMethods {

    public static PassengerResponse getPassenger(Long id) {
        return given()
                .pathParam(ID_PARAMETER_NAME, id)
                .when()
                .get(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(PassengerResponse.class);
    }

    public static ExceptionHandlerResponse getPassengerException(Long id, HttpStatus httpStatus) {
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

    public static PassengerPageResponse getAllPassengers(int offset, int limit) {
        return given()
                .queryParam(OFFSET_PARAMETER_NAME, offset)
                .queryParam(LIMIT_PARAMETER_NAME, limit)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(PassengerPageResponse.class);
    }

    public static ExceptionHandlerResponse getAllPassengersException(int offset, int limit, HttpStatus httpStatus) {
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

    public static PassengerResponse createPassenger(PassengerRequest passengerRequest) {
        return given()
                .contentType(ContentType.JSON)
                .body(passengerRequest)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(PassengerResponse.class);
    }

    public static ExceptionHandlerResponse createPassengerException(PassengerRequest passengerRequest,
                                                                    HttpStatus httpStatus) {
        return given()
                .contentType(ContentType.JSON)
                .body(passengerRequest)
                .when()
                .post()
                .then()
                .statusCode(httpStatus.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(ExceptionHandlerResponse.class);
    }

    public static PassengerResponse updatePassenger(PassengerRequest passengerRequest, Long id) {
        return given()
                .contentType(ContentType.JSON)
                .body(passengerRequest)
                .pathParam(ID_PARAMETER_NAME, id)
                .when()
                .patch(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(PassengerResponse.class);
    }

    public static ExceptionHandlerResponse updatePassengerException(PassengerRequest passengerRequest,
                                                                    Long id, HttpStatus httpStatus) {
        return given()
                .contentType(ContentType.JSON)
                .body(passengerRequest)
                .pathParam(ID_PARAMETER_NAME, id)
                .when()
                .patch(ENDPOINT_WITH_ID)
                .then()
                .statusCode(httpStatus.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(ExceptionHandlerResponse.class);
    }

    public static void deletePassenger(Long id) {
        given()
                .pathParam(ID_PARAMETER_NAME, id)
                .when()
                .delete(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    public static ExceptionHandlerResponse deletePassengerException(Long id, HttpStatus httpStatus) {
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
