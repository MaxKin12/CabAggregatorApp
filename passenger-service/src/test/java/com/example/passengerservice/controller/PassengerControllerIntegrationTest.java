package com.example.passengerservice.controller;

import static com.example.passengerservice.utility.constants.GeneralUtilityConstants.CONTROLLER_BASE_URI;
import static com.example.passengerservice.utility.constants.GeneralUtilityConstants.ENDPOINT_WITH_ID;
import static com.example.passengerservice.utility.constants.GeneralUtilityConstants.ID_PARAMETER_NAME;
import static com.example.passengerservice.utility.constants.PassengerTestData.INVALID_LIMIT;
import static com.example.passengerservice.utility.constants.PassengerTestData.INVALID_OFFSET;
import static com.example.passengerservice.utility.constants.PassengerTestData.INVALID_PASSENGER_ID;
import static com.example.passengerservice.utility.constants.PassengerTestData.INVALID_PASSENGER_REQUEST;
import static com.example.passengerservice.utility.constants.PassengerTestData.LIMIT;
import static com.example.passengerservice.utility.constants.GeneralUtilityConstants.LIMIT_PARAMETER_NAME;
import static com.example.passengerservice.utility.constants.PassengerTestData.NOT_EXIST_PASSENGER_ID;
import static com.example.passengerservice.utility.constants.PassengerTestData.OFFSET;
import static com.example.passengerservice.utility.constants.GeneralUtilityConstants.OFFSET_PARAMETER_NAME;
import static com.example.passengerservice.utility.constants.PassengerTestData.PASSENGER_ID;
import static com.example.passengerservice.utility.constants.PassengerTestData.PASSENGER_PAGE_RESPONSE;
import static com.example.passengerservice.utility.constants.PassengerTestData.PASSENGER_REQUEST_CREATED;
import static com.example.passengerservice.utility.constants.PassengerTestData.PASSENGER_REQUEST_UPDATED;
import static com.example.passengerservice.utility.constants.PassengerTestData.PASSENGER_RESPONSE;
import static com.example.passengerservice.utility.constants.PassengerTestData.PASSENGER_RESPONSE_CREATED;
import static com.example.passengerservice.utility.constants.PassengerTestData.PASSENGER_RESPONSE_UPDATED;
import static com.example.passengerservice.utility.constants.TestDbInitConstants.DB_DOCKER_IMAGE;
import static com.example.passengerservice.utility.constants.TestDbInitConstants.PROPERTY_DB_PASSWORD;
import static com.example.passengerservice.utility.constants.TestDbInitConstants.PROPERTY_DB_URL;
import static com.example.passengerservice.utility.constants.TestDbInitConstants.PROPERTY_DB_USERNAME;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.example.passengerservice.dto.exception.ExceptionHandlerResponse;
import com.example.passengerservice.dto.passenger.PassengerPageResponse;
import com.example.passengerservice.dto.passenger.PassengerResponse;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
class PassengerControllerIntegrationTest {

    @LocalServerPort
    private int curPort;

    @Container
    private static final MySQLContainer<?> MYSQL_CONTAINER = new MySQLContainer<>(
            DockerImageName.parse(DB_DOCKER_IMAGE));

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add(PROPERTY_DB_URL, MYSQL_CONTAINER::getJdbcUrl);
        registry.add(PROPERTY_DB_USERNAME, MYSQL_CONTAINER::getUsername);
        registry.add(PROPERTY_DB_PASSWORD, MYSQL_CONTAINER::getPassword);
    }

    @BeforeEach
    void setUp() {
        baseURI = CONTROLLER_BASE_URI;
        port = curPort;
    }

    @Test
    @Order(0)
    void getPassenger_ValidId_ReturnsValidResponseEntity() {
        PassengerResponse passengerResponse = given()
                .pathParam(ID_PARAMETER_NAME, PASSENGER_ID)
                .when()
                .get(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(PassengerResponse.class);

        assertEquals(passengerResponse, PASSENGER_RESPONSE);
    }

    @Test
    void getPassenger_InvalidId_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = given()
                .pathParam(ID_PARAMETER_NAME, INVALID_PASSENGER_ID)
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
    void getPassenger_PassengerNotFound_ReturnsExceptionResponseWithNotFoundStatus() {
        ExceptionHandlerResponse exception = given()
                .pathParam(ID_PARAMETER_NAME, NOT_EXIST_PASSENGER_ID)
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
    void getAllPassengers_ValidOffsetAndLimitParameters_ReturnsValidResponseEntity() {
        PassengerPageResponse passengerPageResponse = given()
                .queryParam(OFFSET_PARAMETER_NAME, OFFSET)
                .queryParam(LIMIT_PARAMETER_NAME, LIMIT)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(PassengerPageResponse.class);

        assertEquals(passengerPageResponse, PASSENGER_PAGE_RESPONSE);
    }

    @Test
    void getAllPassengers_InvalidOffsetParameter_ReturnsExceptionResponseWithBadRequestStatus() {
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
    void getAllPassengers_InvalidLimitParameter_ReturnsExceptionResponseWithBadRequestStatus() {
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
    void createPassenger_ValidRequest_ReturnsValidResponseEntity() {
        PassengerResponse passengerResponse = given()
                .contentType(ContentType.JSON)
                .body(PASSENGER_REQUEST_CREATED)
                .when()
                .post()
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(PassengerResponse.class);

        assertEquals(passengerResponse.name(), PASSENGER_RESPONSE_CREATED.name());
        assertEquals(passengerResponse.email(), PASSENGER_RESPONSE_CREATED.email());
        assertEquals(passengerResponse.phone(), PASSENGER_RESPONSE_CREATED.phone());
        assertEquals(passengerResponse.rate(), PASSENGER_RESPONSE_CREATED.rate());
    }

    @Test
    void createPassenger_InvalidRequest_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = given()
                .contentType(ContentType.JSON)
                .body(INVALID_PASSENGER_REQUEST)
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
    void updatePassenger_ValidRequestAndId_ReturnsValidResponseEntity() {
        PassengerResponse passengerResponse = given()
                .contentType(ContentType.JSON)
                .body(PASSENGER_REQUEST_UPDATED)
                .pathParam(ID_PARAMETER_NAME, PASSENGER_ID)
                .when()
                .patch(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(PassengerResponse.class);

        assertEquals(passengerResponse, PASSENGER_RESPONSE_UPDATED);
    }

    @Test
    void updatePassenger_InvalidRequest_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = given()
                .contentType(ContentType.JSON)
                .body(INVALID_PASSENGER_REQUEST)
                .pathParam(ID_PARAMETER_NAME, PASSENGER_ID)
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
    void updatePassenger_InvalidId_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = given()
                .contentType(ContentType.JSON)
                .body(PASSENGER_REQUEST_UPDATED)
                .pathParam(ID_PARAMETER_NAME, INVALID_PASSENGER_ID)
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
    void updatePassenger_PassengerNotFound_ReturnsExceptionResponseWithNotFoundStatus() {
        ExceptionHandlerResponse exception = given()
                .contentType(ContentType.JSON)
                .body(PASSENGER_REQUEST_UPDATED)
                .pathParam(ID_PARAMETER_NAME, NOT_EXIST_PASSENGER_ID)
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
    void deletePassenger_ValidId_ReturnsVoid() {
        given()
                .pathParam(ID_PARAMETER_NAME, PASSENGER_ID)
                .when()
                .delete(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deletePassenger_InvalidId_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = given()
                .pathParam(ID_PARAMETER_NAME, INVALID_PASSENGER_ID)
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
    void deletePassenger_PassengerNotFound_ReturnsExceptionResponseWithNotFoundStatus() {
        ExceptionHandlerResponse exception = given()
                .pathParam(ID_PARAMETER_NAME, NOT_EXIST_PASSENGER_ID)
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
