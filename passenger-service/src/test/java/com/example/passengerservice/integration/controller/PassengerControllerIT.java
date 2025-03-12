package com.example.passengerservice.integration.controller;

import static com.example.passengerservice.configuration.constants.GeneralUtilityConstants.CONTROLLER_BASE_URI;
import static com.example.passengerservice.configuration.constants.GeneralUtilityConstants.ID_PARAMETER_NAME;
import static com.example.passengerservice.configuration.constants.PassengerTestData.INVALID_LIMIT;
import static com.example.passengerservice.configuration.constants.PassengerTestData.INVALID_OFFSET;
import static com.example.passengerservice.configuration.constants.PassengerTestData.INVALID_PASSENGER_ID;
import static com.example.passengerservice.configuration.constants.PassengerTestData.INVALID_PASSENGER_REQUEST;
import static com.example.passengerservice.configuration.constants.PassengerTestData.LIMIT;
import static com.example.passengerservice.configuration.constants.PassengerTestData.NOT_EXIST_PASSENGER_ID;
import static com.example.passengerservice.configuration.constants.PassengerTestData.OFFSET;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER_ID;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER_PAGE_RESPONSE;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER_REQUEST_CREATED;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER_REQUEST_UPDATED;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER_RESPONSE;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER_RESPONSE_CREATED;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER_RESPONSE_UPDATED;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.port;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.passengerservice.configuration.container.MySqlTestContainer;
import com.example.passengerservice.dto.exception.ExceptionHandlerResponse;
import com.example.passengerservice.dto.passenger.PassengerPageResponse;
import com.example.passengerservice.dto.passenger.PassengerResponse;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(
        scripts = {
                "classpath:sql/delete_all_test_data_passenger.sql",
                "classpath:sql/init_test_data_passenger.sql"
        },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class PassengerControllerIT extends MySqlTestContainer {

    @LocalServerPort
    private int curPort;

    @PostConstruct
    void setUpUri() {
        baseURI = CONTROLLER_BASE_URI;
        port = curPort;
    }

    @Test
    void getPassenger_ValidId_ReturnsValidResponseEntity() {
        PassengerResponse passengerResponse = ControllerRestAssuredMethods.getPassenger(PASSENGER_ID);

        assertThat(passengerResponse)
                .usingRecursiveComparison()
                .isEqualTo(PASSENGER_RESPONSE);
    }

    @Test
    void getPassenger_InvalidId_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .getPassengerException(INVALID_PASSENGER_ID, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void getPassenger_PassengerNotFound_ReturnsExceptionResponseWithNotFoundStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .getPassengerException(NOT_EXIST_PASSENGER_ID, HttpStatus.NOT_FOUND);

        assertExceptionResponse(exception, HttpStatus.NOT_FOUND);
    }

    @Test
    void getAllPassengers_ValidOffsetAndLimitParameters_ReturnsValidResponseEntity() {
        PassengerPageResponse passengerPageResponse = ControllerRestAssuredMethods.getAllPassengers(OFFSET, LIMIT);

        assertThat(passengerPageResponse)
                .usingRecursiveComparison()
                .isEqualTo(PASSENGER_PAGE_RESPONSE);
    }

    @Test
    void getAllPassengers_InvalidOffsetParameter_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .getAllPassengersException(INVALID_OFFSET, LIMIT, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void getAllPassengers_InvalidLimitParameter_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .getAllPassengersException(OFFSET, INVALID_LIMIT, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void createPassenger_ValidRequest_ReturnsValidResponseEntity() {
        PassengerResponse passengerResponse = ControllerRestAssuredMethods.createPassenger(PASSENGER_REQUEST_CREATED);

        assertThat(passengerResponse)
                .usingRecursiveComparison()
                .ignoringFields(ID_PARAMETER_NAME)
                .isEqualTo(PASSENGER_RESPONSE_CREATED);
    }

    @Test
    void createPassenger_InvalidRequest_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .createPassengerException(INVALID_PASSENGER_REQUEST, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void updatePassenger_ValidRequestAndId_ReturnsValidResponseEntity() {
        PassengerResponse passengerResponse = ControllerRestAssuredMethods
                .updatePassenger(PASSENGER_REQUEST_UPDATED, PASSENGER_ID);

        assertThat(passengerResponse)
                .usingRecursiveComparison()
                .isEqualTo(PASSENGER_RESPONSE_UPDATED);
    }

    @Test
    void updatePassenger_InvalidRequest_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .updatePassengerException(INVALID_PASSENGER_REQUEST, PASSENGER_ID, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void updatePassenger_InvalidId_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .updatePassengerException(PASSENGER_REQUEST_UPDATED, INVALID_PASSENGER_ID, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void updatePassenger_PassengerNotFound_ReturnsExceptionResponseWithNotFoundStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .updatePassengerException(PASSENGER_REQUEST_UPDATED, NOT_EXIST_PASSENGER_ID, HttpStatus.NOT_FOUND);

        assertExceptionResponse(exception, HttpStatus.NOT_FOUND);
    }


    @Test
    void deletePassenger_ValidId_ReturnsVoid() {
        ControllerRestAssuredMethods.deletePassenger(PASSENGER_ID);
    }

    @Test
    void deletePassenger_InvalidId_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .deletePassengerException(INVALID_PASSENGER_ID, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void deletePassenger_PassengerNotFound_ReturnsExceptionResponseWithNotFoundStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .deletePassengerException(NOT_EXIST_PASSENGER_ID, HttpStatus.NOT_FOUND);

        assertExceptionResponse(exception, HttpStatus.NOT_FOUND);
    }

    private void assertExceptionResponse(ExceptionHandlerResponse exception, HttpStatus httpStatus) {
        assertThat(exception.statusCode())
                .isEqualTo(httpStatus.value());
        assertThat(exception.message())
                .isNotNull();
        assertThat(exception.localDateTime())
                .isNotNull();
    }

}
