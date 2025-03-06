package com.example.driverservice.controller;

import static com.example.driverservice.utility.constants.GeneralUtilityConstants.CAR_CONTROLLER_BASE_URI;
import static com.example.driverservice.utility.constants.GeneralUtilityConstants.ENDPOINT_WITH_ID;
import static com.example.driverservice.utility.constants.GeneralUtilityConstants.ID_PARAMETER_NAME;
import static com.example.driverservice.utility.constants.CarTestData.INVALID_LIMIT;
import static com.example.driverservice.utility.constants.CarTestData.INVALID_OFFSET;
import static com.example.driverservice.utility.constants.CarTestData.INVALID_CAR_ID;
import static com.example.driverservice.utility.constants.CarTestData.INVALID_CAR_REQUEST;
import static com.example.driverservice.utility.constants.CarTestData.LIMIT;
import static com.example.driverservice.utility.constants.GeneralUtilityConstants.LIMIT_PARAMETER_NAME;
import static com.example.driverservice.utility.constants.CarTestData.NOT_EXIST_CAR_ID;
import static com.example.driverservice.utility.constants.CarTestData.OFFSET;
import static com.example.driverservice.utility.constants.GeneralUtilityConstants.OFFSET_PARAMETER_NAME;
import static com.example.driverservice.utility.constants.CarTestData.CAR_ID;
import static com.example.driverservice.utility.constants.CarTestData.CAR_REQUEST_CREATED;
import static com.example.driverservice.utility.constants.CarTestData.CAR_REQUEST_UPDATED;
import static com.example.driverservice.utility.constants.CarTestData.CAR_RESPONSE;
import static com.example.driverservice.utility.constants.CarTestData.CAR_RESPONSE_CREATED;
import static com.example.driverservice.utility.constants.CarTestData.CAR_RESPONSE_UPDATED;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.driverservice.configuration.DbContainer;
import com.example.driverservice.dto.exception.ExceptionHandlerResponse;
import com.example.driverservice.dto.car.CarResponse;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CarControllerIntegrationTest extends DbContainer {

    @LocalServerPort
    private int curPort;

    @BeforeEach
    void setUp() {
        baseURI = CAR_CONTROLLER_BASE_URI;
        port = curPort;
    }

    @Test
    @Order(0)
    void getCar_ValidId_ReturnsValidResponseEntity() {
        CarResponse carResponse = given()
                .pathParam(ID_PARAMETER_NAME, CAR_ID)
                .when()
                .get(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(CarResponse.class);

        assertEquals(carResponse, CAR_RESPONSE);
    }

    @Test
    void getCar_InvalidId_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = given()
                .pathParam(ID_PARAMETER_NAME, INVALID_CAR_ID)
                .when()
                .get(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(ExceptionHandlerResponse.class);

        assertEquals(exception.statusCode(), HttpStatus.BAD_REQUEST.value());
        assertNotNull(exception.message());
        assertNotNull(exception.localDateTime());
    }

    @Test
    void getCar_CarNotFound_ReturnsExceptionResponseWithNotFoundStatus() {
        ExceptionHandlerResponse exception = given()
                .pathParam(ID_PARAMETER_NAME, NOT_EXIST_CAR_ID)
                .when()
                .get(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(ExceptionHandlerResponse.class);

        assertEquals(exception.statusCode(), HttpStatus.NOT_FOUND.value());
        assertNotNull(exception.message());
        assertNotNull(exception.localDateTime());
    }

    @Test
    @Order(1)
    void getAllCars_ValidOffsetAndLimitParameters_ReturnsValidResponseEntity() {
        given()
                .queryParam(OFFSET_PARAMETER_NAME, OFFSET)
                .queryParam(LIMIT_PARAMETER_NAME, LIMIT)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON);
    }

    @Test
    void getAllCars_InvalidOffsetParameter_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = given()
                .queryParam(OFFSET_PARAMETER_NAME, INVALID_OFFSET)
                .queryParam(LIMIT_PARAMETER_NAME, LIMIT)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(ExceptionHandlerResponse.class);

        assertEquals(exception.statusCode(), HttpStatus.BAD_REQUEST.value());
        assertNotNull(exception.message());
        assertNotNull(exception.localDateTime());
    }

    @Test
    void getAllCars_InvalidLimitParameter_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = given()
                .queryParam(OFFSET_PARAMETER_NAME, OFFSET)
                .queryParam(LIMIT_PARAMETER_NAME, INVALID_LIMIT)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(ExceptionHandlerResponse.class);

        assertEquals(exception.statusCode(), HttpStatus.BAD_REQUEST.value());
        assertNotNull(exception.message());
        assertNotNull(exception.localDateTime());
    }

    @Test
    void createCar_ValidRequest_ReturnsValidResponseEntity() {
        CarResponse carResponse = given()
                .contentType(ContentType.JSON)
                .body(CAR_REQUEST_CREATED)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(CarResponse.class);

        assertThat(carResponse)
                .usingRecursiveComparison()
                .ignoringFields(ID_PARAMETER_NAME)
                .isEqualTo(CAR_RESPONSE_CREATED);
    }

    @Test
    void createCar_InvalidRequest_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = given()
                .contentType(ContentType.JSON)
                .body(INVALID_CAR_REQUEST)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(ExceptionHandlerResponse.class);

        assertEquals(exception.statusCode(), HttpStatus.BAD_REQUEST.value());
        assertNotNull(exception.message());
        assertNotNull(exception.localDateTime());
    }

    @Test
    @Order(2)
    void updateCar_ValidRequestAndId_ReturnsValidResponseEntity() {
        CarResponse carResponse = given()
                .contentType(ContentType.JSON)
                .body(CAR_REQUEST_UPDATED)
                .pathParam(ID_PARAMETER_NAME, CAR_ID)
                .when()
                .patch(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(CarResponse.class);

        assertEquals(carResponse, CAR_RESPONSE_UPDATED);
    }

    @Test
    void updateCar_InvalidRequest_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = given()
                .contentType(ContentType.JSON)
                .body(INVALID_CAR_REQUEST)
                .pathParam(ID_PARAMETER_NAME, CAR_ID)
                .when()
                .patch(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(ExceptionHandlerResponse.class);

        assertEquals(exception.statusCode(), HttpStatus.BAD_REQUEST.value());
        assertNotNull(exception.message());
        assertNotNull(exception.localDateTime());
    }

    @Test
    void updateCar_InvalidId_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = given()
                .contentType(ContentType.JSON)
                .body(CAR_REQUEST_UPDATED)
                .pathParam(ID_PARAMETER_NAME, INVALID_CAR_ID)
                .when()
                .patch(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(ExceptionHandlerResponse.class);

        assertEquals(exception.statusCode(), HttpStatus.BAD_REQUEST.value());
        assertNotNull(exception.message());
        assertNotNull(exception.localDateTime());
    }

    @Test
    void updateCar_CarNotFound_ReturnsExceptionResponseWithNotFoundStatus() {
        ExceptionHandlerResponse exception = given()
                .contentType(ContentType.JSON)
                .body(CAR_REQUEST_UPDATED)
                .pathParam(ID_PARAMETER_NAME, NOT_EXIST_CAR_ID)
                .when()
                .patch(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(ExceptionHandlerResponse.class);

        assertEquals(exception.statusCode(), HttpStatus.NOT_FOUND.value());
        assertNotNull(exception.message());
        assertNotNull(exception.localDateTime());
    }

    @Test
    void deleteCar_ValidId_ReturnsVoid() {
        given()
                .pathParam(ID_PARAMETER_NAME, CAR_ID)
                .when()
                .delete(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deleteCar_InvalidId_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = given()
                .pathParam(ID_PARAMETER_NAME, INVALID_CAR_ID)
                .when()
                .delete(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(ExceptionHandlerResponse.class);

        assertEquals(exception.statusCode(), HttpStatus.BAD_REQUEST.value());
        assertNotNull(exception.message());
        assertNotNull(exception.localDateTime());
    }

    @Test
    void deleteCar_CarNotFound_ReturnsExceptionResponseWithNotFoundStatus() {
        ExceptionHandlerResponse exception = given()
                .pathParam(ID_PARAMETER_NAME, NOT_EXIST_CAR_ID)
                .when()
                .delete(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(ExceptionHandlerResponse.class);

        assertEquals(exception.statusCode(), HttpStatus.NOT_FOUND.value());
        assertNotNull(exception.message());
        assertNotNull(exception.localDateTime());
    }

}
