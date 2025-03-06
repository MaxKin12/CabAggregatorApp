package com.example.driverservice.controller;

import static com.example.driverservice.utility.constants.GeneralUtilityConstants.DRIVER_CONTROLLER_BASE_URI;
import static com.example.driverservice.utility.constants.GeneralUtilityConstants.ENDPOINT_WITH_ID;
import static com.example.driverservice.utility.constants.GeneralUtilityConstants.ID_PARAMETER_NAME;
import static com.example.driverservice.utility.constants.DriverTestData.INVALID_LIMIT;
import static com.example.driverservice.utility.constants.DriverTestData.INVALID_OFFSET;
import static com.example.driverservice.utility.constants.DriverTestData.INVALID_DRIVER_ID;
import static com.example.driverservice.utility.constants.DriverTestData.INVALID_DRIVER_REQUEST;
import static com.example.driverservice.utility.constants.DriverTestData.LIMIT;
import static com.example.driverservice.utility.constants.GeneralUtilityConstants.LIMIT_PARAMETER_NAME;
import static com.example.driverservice.utility.constants.DriverTestData.NOT_EXIST_DRIVER_ID;
import static com.example.driverservice.utility.constants.DriverTestData.OFFSET;
import static com.example.driverservice.utility.constants.GeneralUtilityConstants.OFFSET_PARAMETER_NAME;
import static com.example.driverservice.utility.constants.DriverTestData.DRIVER_ID;
import static com.example.driverservice.utility.constants.DriverTestData.DRIVER_REQUEST_CREATED;
import static com.example.driverservice.utility.constants.DriverTestData.DRIVER_REQUEST_UPDATED;
import static com.example.driverservice.utility.constants.DriverTestData.DRIVER_RESPONSE;
import static com.example.driverservice.utility.constants.DriverTestData.DRIVER_RESPONSE_CREATED;
import static com.example.driverservice.utility.constants.DriverTestData.DRIVER_RESPONSE_UPDATED;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.driverservice.configuration.DbContainer;
import com.example.driverservice.dto.exception.ExceptionHandlerResponse;
import com.example.driverservice.dto.driver.DriverResponse;
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
public class DriverControllerIntegrationTest extends DbContainer {

    @LocalServerPort
    private int curPort;

    @BeforeEach
    void setUp() {
        baseURI = DRIVER_CONTROLLER_BASE_URI;
        port = curPort;
    }

    @Test
    @Order(0)
    void getDriver_ValidId_ReturnsValidResponseEntity() {
        DriverResponse driverResponse = given()
                .pathParam(ID_PARAMETER_NAME, DRIVER_ID)
                .when()
                .get(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(DriverResponse.class);

        assertEquals(driverResponse, DRIVER_RESPONSE);
    }

    @Test
    void getDriver_InvalidId_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = given()
                .pathParam(ID_PARAMETER_NAME, INVALID_DRIVER_ID)
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
    void getDriver_DriverNotFound_ReturnsExceptionResponseWithNotFoundStatus() {
        ExceptionHandlerResponse exception = given()
                .pathParam(ID_PARAMETER_NAME, NOT_EXIST_DRIVER_ID)
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
    void getAllDrivers_ValidOffsetAndLimitParameters_ReturnsValidResponseEntity() {
        given()
                .queryParam(OFFSET_PARAMETER_NAME, OFFSET)
                .queryParam(LIMIT_PARAMETER_NAME, LIMIT)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void getAllDrivers_InvalidOffsetParameter_ReturnsExceptionResponseWithBadRequestStatus() {
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
    void getAllDrivers_InvalidLimitParameter_ReturnsExceptionResponseWithBadRequestStatus() {
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
    void createDriver_ValidRequest_ReturnsValidResponseEntity() {
        DriverResponse driverResponse = given()
                .contentType(ContentType.JSON)
                .body(DRIVER_REQUEST_CREATED)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(DriverResponse.class);

        assertThat(driverResponse)
                .usingRecursiveComparison()
                .ignoringFields(ID_PARAMETER_NAME)
                .isEqualTo(DRIVER_RESPONSE_CREATED);
    }

    @Test
    void createDriver_InvalidRequest_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = given()
                .contentType(ContentType.JSON)
                .body(INVALID_DRIVER_REQUEST)
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
    void updateDriver_ValidRequestAndId_ReturnsValidResponseEntity() {
        DriverResponse driverResponse = given()
                .contentType(ContentType.JSON)
                .body(DRIVER_REQUEST_UPDATED)
                .pathParam(ID_PARAMETER_NAME, DRIVER_ID)
                .when()
                .patch(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(DriverResponse.class);

        assertEquals(driverResponse, DRIVER_RESPONSE_UPDATED);
    }

    @Test
    void updateDriver_InvalidRequest_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = given()
                .contentType(ContentType.JSON)
                .body(INVALID_DRIVER_REQUEST)
                .pathParam(ID_PARAMETER_NAME, DRIVER_ID)
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
    void updateDriver_InvalidId_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = given()
                .contentType(ContentType.JSON)
                .body(DRIVER_REQUEST_UPDATED)
                .pathParam(ID_PARAMETER_NAME, INVALID_DRIVER_ID)
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
    void updateDriver_DriverNotFound_ReturnsExceptionResponseWithNotFoundStatus() {
        ExceptionHandlerResponse exception = given()
                .contentType(ContentType.JSON)
                .body(DRIVER_REQUEST_UPDATED)
                .pathParam(ID_PARAMETER_NAME, NOT_EXIST_DRIVER_ID)
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
    void deleteDriver_ValidId_ReturnsVoid() {
        given()
                .pathParam(ID_PARAMETER_NAME, DRIVER_ID)
                .when()
                .delete(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deleteDriver_InvalidId_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = given()
                .pathParam(ID_PARAMETER_NAME, INVALID_DRIVER_ID)
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
    void deleteDriver_DriverNotFound_ReturnsExceptionResponseWithNotFoundStatus() {
        ExceptionHandlerResponse exception = given()
                .pathParam(ID_PARAMETER_NAME, NOT_EXIST_DRIVER_ID)
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
