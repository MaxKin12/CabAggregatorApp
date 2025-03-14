package com.example.ratesservice.integration.controller;

import static com.example.ratesservice.configuration.constants.GeneralUtilityConstants.ENDPOINT_WITH_DRIVERS;
import static com.example.ratesservice.configuration.constants.GeneralUtilityConstants.ENDPOINT_WITH_ID;
import static com.example.ratesservice.configuration.constants.GeneralUtilityConstants.ENDPOINT_WITH_PASSENGERS;
import static com.example.ratesservice.configuration.constants.GeneralUtilityConstants.ID_PARAMETER_NAME;
import static com.example.ratesservice.configuration.constants.GeneralUtilityConstants.LIMIT_PARAMETER_NAME;
import static com.example.ratesservice.configuration.constants.GeneralUtilityConstants.OFFSET_PARAMETER_NAME;
import static io.restassured.RestAssured.given;

import com.example.ratesservice.dto.exception.ExceptionHandlerResponse;
import com.example.ratesservice.dto.rate.RatePageResponse;
import com.example.ratesservice.dto.rate.RateRequest;
import com.example.ratesservice.dto.rate.RateResponse;
import com.example.ratesservice.dto.rate.RateUpdateRequest;
import io.restassured.http.ContentType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ControllerRestAssuredMethods {

    public static RateResponse getRate(Long id) {
        return given()
                .pathParam(ID_PARAMETER_NAME, id)
                .when()
                .get(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(RateResponse.class);
    }

    public static ExceptionHandlerResponse getRateException(Long id, HttpStatus httpStatus) {
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

    public static RatePageResponse getAllPassengersRates(int offset, int limit) {
        return given()
                .queryParam(OFFSET_PARAMETER_NAME, offset)
                .queryParam(LIMIT_PARAMETER_NAME, limit)
                .when()
                .get(ENDPOINT_WITH_PASSENGERS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(RatePageResponse.class);
    }

    public static RatePageResponse getAllDriversRates(int offset, int limit) {
        return given()
                .queryParam(OFFSET_PARAMETER_NAME, offset)
                .queryParam(LIMIT_PARAMETER_NAME, limit)
                .when()
                .get(ENDPOINT_WITH_DRIVERS)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(RatePageResponse.class);
    }

    public static ExceptionHandlerResponse getAllPassengersRatesException(int offset, int limit,
                                                                          HttpStatus httpStatus) {
        return given()
                .queryParam(OFFSET_PARAMETER_NAME, offset)
                .queryParam(LIMIT_PARAMETER_NAME, limit)
                .when()
                .get(ENDPOINT_WITH_PASSENGERS)
                .then()
                .statusCode(httpStatus.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(ExceptionHandlerResponse.class);
    }

    public static ExceptionHandlerResponse getAllDriversRatesException(int offset, int limit,
                                                                          HttpStatus httpStatus) {
        return given()
                .queryParam(OFFSET_PARAMETER_NAME, offset)
                .queryParam(LIMIT_PARAMETER_NAME, limit)
                .when()
                .get(ENDPOINT_WITH_DRIVERS)
                .then()
                .statusCode(httpStatus.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(ExceptionHandlerResponse.class);
    }

    public static RateResponse rateRide(RateRequest rateRequest) {
        return given()
                .contentType(ContentType.JSON)
                .body(rateRequest)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(RateResponse.class);
    }

    public static ExceptionHandlerResponse createRateException(RateRequest rateRequest, HttpStatus httpStatus) {
        return given()
                .contentType(ContentType.JSON)
                .body(rateRequest)
                .when()
                .post()
                .then()
                .statusCode(httpStatus.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(ExceptionHandlerResponse.class);
    }

    public static RateResponse updateRate(RateUpdateRequest rateRequest, Long id) {
        return given()
                .contentType(ContentType.JSON)
                .body(rateRequest)
                .pathParam(ID_PARAMETER_NAME, id)
                .when()
                .patch(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(RateResponse.class);
    }

    public static ExceptionHandlerResponse updateRateException(RateUpdateRequest rateRequest,
                                                               Long id, HttpStatus httpStatus) {
        return given()
                .contentType(ContentType.JSON)
                .body(rateRequest)
                .pathParam(ID_PARAMETER_NAME, id)
                .when()
                .patch(ENDPOINT_WITH_ID)
                .then()
                .statusCode(httpStatus.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(ExceptionHandlerResponse.class);
    }

    public static void deleteRate(Long id) {
        given()
                .pathParam(ID_PARAMETER_NAME, id)
                .when()
                .delete(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    public static ExceptionHandlerResponse deleteRateException(Long id, HttpStatus httpStatus) {
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
