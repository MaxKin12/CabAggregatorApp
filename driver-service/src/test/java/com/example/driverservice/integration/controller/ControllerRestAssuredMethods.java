package com.example.driverservice.integration.controller;

import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.ENDPOINT_WITH_ID;
import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.ID_PARAMETER_NAME;
import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.LIMIT_PARAMETER_NAME;
import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.OFFSET_PARAMETER_NAME;
import static io.restassured.RestAssured.given;

import com.example.driverservice.dto.EntityRequest;
import com.example.driverservice.dto.EntityResponse;
import com.example.driverservice.dto.exception.ExceptionHandlerResponse;
import io.restassured.http.ContentType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ControllerRestAssuredMethods {

    public static <T extends EntityResponse> T getEntity(Long id, Class<T> clazz) {
        return given()
                .pathParam(ID_PARAMETER_NAME, id)
                .when()
                .get(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(clazz);
    }

    public static ExceptionHandlerResponse getEntityException(Long id, HttpStatus httpStatus) {
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

    public static void getAllEntities(int offset, int limit) {
        given()
                .queryParam(OFFSET_PARAMETER_NAME, offset)
                .queryParam(LIMIT_PARAMETER_NAME, limit)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    public static ExceptionHandlerResponse getAllEntitiesException(int offset, int limit, HttpStatus httpStatus) {
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

    public static <G extends EntityRequest, T extends EntityResponse> T createEntity(
            G passengerRequest,
            Class<T> clazz
    ) {
        return given()
                .contentType(ContentType.JSON)
                .body(passengerRequest)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(clazz);
    }

    public static <G extends EntityRequest> ExceptionHandlerResponse createEntityException(
            G passengerRequest,
            HttpStatus httpStatus
    ){
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

    public static <T extends EntityResponse, G extends EntityRequest> T updateEntity(
            G passengerRequest,
            Long id,
            Class<T> clazz
    ) {
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
                .as(clazz);
    }

    public static <G extends EntityRequest> ExceptionHandlerResponse updateEntityException(
            G passengerRequest,
            Long id,
            HttpStatus httpStatus
    ) {
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

    public static void deleteEntity(Long id) {
        given()
                .pathParam(ID_PARAMETER_NAME, id)
                .when()
                .delete(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    public static ExceptionHandlerResponse deleteEntityException(Long id, HttpStatus httpStatus) {
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
