package com.example.ridesservice.integration.controller;

import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.CONTROLLER_BASE_URI;
import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.GET_CAR_REQUEST_URL;
import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.GET_DRIVER_REQUEST_URL;
import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.GET_PASSENGER_REQUEST_URL;
import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.ID_PARAMETER_NAME;
import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.ORDER_TIME_PARAMETER_NAME;
import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.PRICE_PARAMETER_NAME;
import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.RIDE_LIST_PARAMETER_NAME;
import static com.example.ridesservice.configuration.constants.JsonExternalResponses.CAR_JSON_RESPONSE;
import static com.example.ridesservice.configuration.constants.JsonExternalResponses.DRIVER_JSON_RESPONSE;
import static com.example.ridesservice.configuration.constants.JsonExternalResponses.PASSENGER_JSON_RESPONSE;
import static com.example.ridesservice.configuration.constants.RideTestData.CAR_ID;
import static com.example.ridesservice.configuration.constants.RideTestData.DRIVER_ID;
import static com.example.ridesservice.configuration.constants.RideTestData.INVALID_LIMIT;
import static com.example.ridesservice.configuration.constants.RideTestData.INVALID_OFFSET;
import static com.example.ridesservice.configuration.constants.RideTestData.INVALID_RIDE_ID;
import static com.example.ridesservice.configuration.constants.RideTestData.INVALID_RIDE_REQUEST;
import static com.example.ridesservice.configuration.constants.RideTestData.LIMIT;
import static com.example.ridesservice.configuration.constants.RideTestData.NOT_EXIST_RIDE_ID;
import static com.example.ridesservice.configuration.constants.RideTestData.OFFSET;
import static com.example.ridesservice.configuration.constants.RideTestData.PASSENGER_ID;
import static com.example.ridesservice.configuration.constants.RideTestData.RIDE_ID;
import static com.example.ridesservice.configuration.constants.RideTestData.RIDE_PAGE_RESPONSE;
import static com.example.ridesservice.configuration.constants.RideTestData.RIDE_REQUEST_CREATED;
import static com.example.ridesservice.configuration.constants.RideTestData.RIDE_REQUEST_UPDATED;
import static com.example.ridesservice.configuration.constants.RideTestData.RIDE_RESPONSE;
import static com.example.ridesservice.configuration.constants.RideTestData.RIDE_RESPONSE_CREATED;
import static com.example.ridesservice.configuration.constants.RideTestData.RIDE_RESPONSE_UPDATED;
import static com.example.ridesservice.configuration.wiremock.WireMockStubs.setGetResponseStub;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.port;
import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.BIG_DECIMAL;

import com.example.ridesservice.configuration.container.MySqlTestContainer;
import com.example.ridesservice.dto.exception.ExceptionHandlerResponse;
import com.example.ridesservice.dto.ride.response.RidePageResponse;
import com.example.ridesservice.dto.ride.response.RideResponse;
import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;
import org.assertj.core.util.BigDecimalComparator;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 9050)
@Sql(
        scripts = {
                "classpath:sql/delete_all_test_data_ride.sql",
                "classpath:sql/init_test_data_ride.sql"
        },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class RideControllerIT extends MySqlTestContainer {

    @LocalServerPort
    private int curPort;

    @PostConstruct
    void setUpUri() {
        baseURI = CONTROLLER_BASE_URI;
        port = curPort;
    }

    @Test
    void getRide_ValidId_ReturnsValidResponseEntity() {
        RideResponse rideResponse = ControllerRestAssuredMethods.getRide(RIDE_ID);

        assertThat(rideResponse)
                .usingRecursiveComparison()
                .ignoringFields(PRICE_PARAMETER_NAME)
                .isEqualTo(RIDE_RESPONSE);
        assertThat(rideResponse)
                .extracting(RideResponse::price, as(BIG_DECIMAL))
                .isEqualByComparingTo(RIDE_RESPONSE.price());
    }

    @Test
    void getRide_InvalidId_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .getRideException(INVALID_RIDE_ID, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void getRide_RideNotFound_ReturnsExceptionResponseWithNotFoundStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .getRideException(NOT_EXIST_RIDE_ID, HttpStatus.NOT_FOUND);

        assertExceptionResponse(exception, HttpStatus.NOT_FOUND);
    }

    @Test
    void getAllRides_ValidOffsetAndLimitParameters_ReturnsValidResponseEntity() {
        RidePageResponse ridePageResponse = ControllerRestAssuredMethods.getAllRides(OFFSET, LIMIT);

        assertThat(ridePageResponse)
                .usingRecursiveComparison()
                .ignoringFields(RIDE_LIST_PARAMETER_NAME)
                .isEqualTo(RIDE_PAGE_RESPONSE);
        assertThat(ridePageResponse.rideList())
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields(PRICE_PARAMETER_NAME)
                .isEqualTo(RIDE_PAGE_RESPONSE.rideList());

        List<BigDecimal> expectedPrices = RIDE_PAGE_RESPONSE.rideList().stream()
                .map(RideResponse::price).toList();
        List<BigDecimal> actualPrices = ridePageResponse.rideList().stream()
                .map(RideResponse::price).toList();
        assertThat(actualPrices)
                .usingComparatorForType(BigDecimalComparator.BIG_DECIMAL_COMPARATOR, BigDecimal.class)
                .isEqualTo(expectedPrices);
    }

    @Test
    void getAllRides_InvalidOffsetParameter_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .getAllRidesException(INVALID_OFFSET, LIMIT, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void getAllRides_InvalidLimitParameter_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .getAllRidesException(OFFSET, INVALID_LIMIT, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void createRide_ValidRequest_ReturnsValidResponseEntity() {
        setGetResponseStub(GET_PASSENGER_REQUEST_URL, PASSENGER_ID.toString(), PASSENGER_JSON_RESPONSE);
        setGetResponseStub(GET_DRIVER_REQUEST_URL, DRIVER_ID.toString(), DRIVER_JSON_RESPONSE);
        setGetResponseStub(GET_CAR_REQUEST_URL, CAR_ID.toString(), CAR_JSON_RESPONSE);

        RideResponse rideResponse = ControllerRestAssuredMethods.createRide(RIDE_REQUEST_CREATED);

        assertThat(rideResponse)
                .usingRecursiveComparison()
                .ignoringFields(ID_PARAMETER_NAME, ORDER_TIME_PARAMETER_NAME, PRICE_PARAMETER_NAME)
                .isEqualTo(RIDE_RESPONSE_CREATED);
        assertThat(rideResponse)
                .extracting(RideResponse::price, as(BIG_DECIMAL))
                .isEqualByComparingTo(rideResponse.price());
    }

    @Test
    void createRide_InvalidRequest_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .createRideException(INVALID_RIDE_REQUEST, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void updateRide_ValidRequestAndId_ReturnsValidResponseEntity() {
        setGetResponseStub(GET_PASSENGER_REQUEST_URL, PASSENGER_ID.toString(), PASSENGER_JSON_RESPONSE);
        setGetResponseStub(GET_DRIVER_REQUEST_URL, DRIVER_ID.toString(), DRIVER_JSON_RESPONSE);
        setGetResponseStub(GET_CAR_REQUEST_URL, CAR_ID.toString(), CAR_JSON_RESPONSE);

        RideResponse rideResponse = ControllerRestAssuredMethods
                .updateRide(RIDE_REQUEST_UPDATED, RIDE_ID);

        assertThat(rideResponse)
                .usingRecursiveComparison()
                .ignoringFields(PRICE_PARAMETER_NAME)
                .isEqualTo(RIDE_RESPONSE_UPDATED);
        assertThat(rideResponse)
                .extracting(RideResponse::price, as(BIG_DECIMAL))
                .isEqualByComparingTo(rideResponse.price());
    }

    @Test
    void updateRide_InvalidRequest_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .updateRideException(INVALID_RIDE_REQUEST, RIDE_ID, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void updateRide_InvalidId_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .updateRideException(RIDE_REQUEST_UPDATED, INVALID_RIDE_ID, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void updateRide_RideNotFound_ReturnsExceptionResponseWithNotFoundStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .updateRideException(RIDE_REQUEST_UPDATED, NOT_EXIST_RIDE_ID, HttpStatus.NOT_FOUND);

        assertExceptionResponse(exception, HttpStatus.NOT_FOUND);
    }


    @Test
    void deleteRide_ValidId_ReturnsVoid() {
        ControllerRestAssuredMethods.deleteRide(RIDE_ID);
    }

    @Test
    void deleteRide_InvalidId_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .deleteRideException(INVALID_RIDE_ID, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void deleteRide_RideNotFound_ReturnsExceptionResponseWithNotFoundStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .deleteRideException(NOT_EXIST_RIDE_ID, HttpStatus.NOT_FOUND);

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
