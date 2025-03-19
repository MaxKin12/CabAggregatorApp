package com.example.ratesservice.integration.controller;

import static com.example.ratesservice.configuration.constants.GeneralUtilityConstants.CONTROLLER_BASE_URI;
import static com.example.ratesservice.configuration.constants.GeneralUtilityConstants.GET_DRIVER_REQUEST_URL;
import static com.example.ratesservice.configuration.constants.GeneralUtilityConstants.GET_PASSENGER_REQUEST_URL;
import static com.example.ratesservice.configuration.constants.GeneralUtilityConstants.GET_RIDES_REQUEST_URL;
import static com.example.ratesservice.configuration.constants.GeneralUtilityConstants.ID_PARAMETER_NAME;
import static com.example.ratesservice.configuration.constants.JsonExternalResponses.DRIVER_JSON_RESPONSE;
import static com.example.ratesservice.configuration.constants.JsonExternalResponses.PASSENGER_JSON_RESPONSE;
import static com.example.ratesservice.configuration.constants.JsonExternalResponses.RIDE_JSON_RESPONSE;
import static com.example.ratesservice.configuration.constants.RateTestData.DRIVER_ID;
import static com.example.ratesservice.configuration.constants.RateTestData.INVALID_LIMIT;
import static com.example.ratesservice.configuration.constants.RateTestData.INVALID_OFFSET;
import static com.example.ratesservice.configuration.constants.RateTestData.INVALID_RATE_ID;
import static com.example.ratesservice.configuration.constants.RateTestData.INVALID_RATE_REQUEST;
import static com.example.ratesservice.configuration.constants.RateTestData.INVALID_RATE_REQUEST_UPDATED;
import static com.example.ratesservice.configuration.constants.RateTestData.LIMIT;
import static com.example.ratesservice.configuration.constants.RateTestData.NOT_EXIST_RATE_ID;
import static com.example.ratesservice.configuration.constants.RateTestData.OFFSET;
import static com.example.ratesservice.configuration.constants.RateTestData.PASSENGER_ID;
import static com.example.ratesservice.configuration.constants.RateTestData.RATE_ID;
import static com.example.ratesservice.configuration.constants.RateTestData.RATE_PAGE_RESPONSE;
import static com.example.ratesservice.configuration.constants.RateTestData.RATE_PAGE_RESPONSE_DRIVERS;
import static com.example.ratesservice.configuration.constants.RateTestData.RATE_REQUEST_CREATED;
import static com.example.ratesservice.configuration.constants.RateTestData.RATE_REQUEST_UPDATED;
import static com.example.ratesservice.configuration.constants.RateTestData.RATE_RESPONSE;
import static com.example.ratesservice.configuration.constants.RateTestData.RATE_RESPONSE_CREATED;
import static com.example.ratesservice.configuration.constants.RateTestData.RATE_RESPONSE_UPDATED;
import static com.example.ratesservice.configuration.constants.RateTestData.RIDE_ID;
import static com.example.ratesservice.configuration.wiremock.WireMockStubs.setGetResponseStub;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.port;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.ratesservice.configuration.KafkaProducerConfig;
import com.example.ratesservice.configuration.container.MySqlTestContainer;
import com.example.ratesservice.dto.exception.ExceptionHandlerResponse;
import com.example.ratesservice.dto.rate.RatePageResponse;
import com.example.ratesservice.dto.rate.RateResponse;
import com.example.ratesservice.utility.validation.KafkaProducerServiceValidation;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 9050)
@Sql(
        scripts = {
                "classpath:sql/delete_all_test_data_rate.sql",
                "classpath:sql/init_test_data_rate.sql"
        },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class RateControllerIT extends MySqlTestContainer {

    @LocalServerPort
    private int curPort;

    @MockitoBean
    protected KafkaProducerConfig kafkaProducerConfig;

    @MockitoBean
    protected KafkaProducerServiceValidation kafkaProducer;

    @PostConstruct
    void setUpUri() {
        baseURI = CONTROLLER_BASE_URI;
        port = curPort;
    }

    @Test
    void getRate_ValidId_ReturnsValidResponseEntity() {
        RateResponse rateResponse = ControllerRestAssuredMethods.getRate(RATE_ID);

        assertThat(rateResponse)
                .usingRecursiveComparison()
                .isEqualTo(RATE_RESPONSE);
    }

    @Test
    void getRate_InvalidId_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .getRateException(INVALID_RATE_ID, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void getRate_RateNotFound_ReturnsExceptionResponseWithNotFoundStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .getRateException(NOT_EXIST_RATE_ID, HttpStatus.NOT_FOUND);

        assertExceptionResponse(exception, HttpStatus.NOT_FOUND);
    }

    @Test
    void getAllPassengersRates_ValidOffsetAndLimitParameters_ReturnsValidResponseEntity() {
        RatePageResponse ratePageResponse = ControllerRestAssuredMethods.getAllPassengersRates(OFFSET, LIMIT);

        assertThat(ratePageResponse)
                .usingRecursiveComparison()
                .isEqualTo(RATE_PAGE_RESPONSE);
    }

    @Test
    void getAllDriversRates_ValidOffsetAndLimitParameters_ReturnsValidResponseEntity() {
        RatePageResponse ratePageResponse = ControllerRestAssuredMethods.getAllDriversRates(OFFSET, LIMIT);

        assertThat(ratePageResponse)
                .usingRecursiveComparison()
                .isEqualTo(RATE_PAGE_RESPONSE_DRIVERS);
    }

    @Test
    void getAllRates_InvalidOffsetParameter_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .getAllPassengersRatesException(INVALID_OFFSET, LIMIT, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void getAllRates_InvalidLimitParameter_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .getAllDriversRatesException(OFFSET, INVALID_LIMIT, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void createRate_ValidRequest_ReturnsValidResponseEntity() {
        setGetResponseStub(GET_PASSENGER_REQUEST_URL, PASSENGER_ID.toString(), PASSENGER_JSON_RESPONSE);
        setGetResponseStub(GET_DRIVER_REQUEST_URL, DRIVER_ID.toString(), DRIVER_JSON_RESPONSE);
        setGetResponseStub(GET_RIDES_REQUEST_URL, RIDE_ID.toString(), RIDE_JSON_RESPONSE);

        RateResponse rateResponse = ControllerRestAssuredMethods.rateRide(RATE_REQUEST_CREATED);

        assertThat(rateResponse)
                .usingRecursiveComparison()
                .ignoringFields(ID_PARAMETER_NAME)
                .isEqualTo(RATE_RESPONSE_CREATED);
    }

    @Test
    void createRate_InvalidRequest_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .createRateException(INVALID_RATE_REQUEST, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void updateRate_ValidRequestAndId_ReturnsValidResponseEntity() {
        RateResponse rateResponse = ControllerRestAssuredMethods
                .updateRate(RATE_REQUEST_UPDATED, RATE_ID);

        assertThat(rateResponse)
                .usingRecursiveComparison()
                .isEqualTo(RATE_RESPONSE_UPDATED);
    }

    @Test
    void updateRate_InvalidRequest_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .updateRateException(INVALID_RATE_REQUEST_UPDATED, RATE_ID, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void updateRate_InvalidId_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .updateRateException(RATE_REQUEST_UPDATED, INVALID_RATE_ID, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void updateRate_RateNotFound_ReturnsExceptionResponseWithNotFoundStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .updateRateException(RATE_REQUEST_UPDATED, NOT_EXIST_RATE_ID, HttpStatus.NOT_FOUND);

        assertExceptionResponse(exception, HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteRate_ValidId_ReturnsVoid() {
        ControllerRestAssuredMethods.deleteRate(RATE_ID);
    }

    @Test
    void deleteRate_InvalidId_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .deleteRateException(INVALID_RATE_ID, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void deleteRate_RateNotFound_ReturnsExceptionResponseWithNotFoundStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .deleteRateException(NOT_EXIST_RATE_ID, HttpStatus.NOT_FOUND);

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
