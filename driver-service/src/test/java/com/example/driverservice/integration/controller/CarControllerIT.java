package com.example.driverservice.integration.controller;

import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.CAR_CONTROLLER_BASE_URI;
import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.ID_PARAMETER_NAME;
import static com.example.driverservice.configuration.constants.CarTestData.INVALID_LIMIT;
import static com.example.driverservice.configuration.constants.CarTestData.INVALID_OFFSET;
import static com.example.driverservice.configuration.constants.CarTestData.INVALID_CAR_ID;
import static com.example.driverservice.configuration.constants.CarTestData.INVALID_CAR_REQUEST;
import static com.example.driverservice.configuration.constants.CarTestData.LIMIT;
import static com.example.driverservice.configuration.constants.CarTestData.NOT_EXIST_CAR_ID;
import static com.example.driverservice.configuration.constants.CarTestData.OFFSET;
import static com.example.driverservice.configuration.constants.CarTestData.CAR_ID;
import static com.example.driverservice.configuration.constants.CarTestData.CAR_REQUEST_CREATED;
import static com.example.driverservice.configuration.constants.CarTestData.CAR_REQUEST_UPDATED;
import static com.example.driverservice.configuration.constants.CarTestData.CAR_RESPONSE;
import static com.example.driverservice.configuration.constants.CarTestData.CAR_RESPONSE_CREATED;
import static com.example.driverservice.configuration.constants.CarTestData.CAR_RESPONSE_UPDATED;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.port;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.driverservice.configuration.KafkaConsumerConfig;
import com.example.driverservice.configuration.container.MySqlTestContainer;
import com.example.driverservice.dto.exception.ExceptionHandlerResponse;
import com.example.driverservice.dto.car.CarResponse;
import com.example.driverservice.kafka.KafkaListenerService;
import com.example.driverservice.utility.validation.KafkaConsumerServiceValidation;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(
        scripts = {
                "classpath:sql/delete_all_test_data_cars.sql",
                "classpath:sql/delete_all_test_data_drivers.sql",
                "classpath:sql/init_test_data_drivers.sql",
                "classpath:sql/init_test_data_cars.sql"
        },
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
public class CarControllerIT extends MySqlTestContainer {


    @MockitoBean
    protected KafkaConsumerConfig kafkaProducerConfig;

    @MockitoBean
    protected KafkaListenerService kafkaListener;

    @LocalServerPort
    private int curPort;

    @PostConstruct
    void setUp() {
        baseURI = CAR_CONTROLLER_BASE_URI;
        port = curPort;
    }

    @Test
    void getCar_ValidId_ReturnsValidResponseEntity() {
        CarResponse carResponse = ControllerRestAssuredMethods
                .getEntity(CAR_ID, CarResponse.class);

        assertThat(carResponse)
                .usingRecursiveComparison()
                .isEqualTo(CAR_RESPONSE);
    }

    @Test
    void getCar_InvalidId_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .getEntityException(INVALID_CAR_ID, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void getCar_CarNotFound_ReturnsExceptionResponseWithNotFoundStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .getEntityException(NOT_EXIST_CAR_ID, HttpStatus.NOT_FOUND);

        assertExceptionResponse(exception, HttpStatus.NOT_FOUND);
    }

    @Test
    void getAllCars_ValidOffsetAndLimitParameters_ReturnsValidResponseEntity() {
        ControllerRestAssuredMethods
                .getAllEntities(OFFSET, LIMIT);
    }

    @Test
    void getAllCars_InvalidOffsetParameter_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .getAllEntitiesException(INVALID_OFFSET, LIMIT, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void getAllCars_InvalidLimitParameter_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .getAllEntitiesException(OFFSET, INVALID_LIMIT, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void createCar_ValidRequest_ReturnsValidResponseEntity() {
        CarResponse carResponse = ControllerRestAssuredMethods
                .createEntity(CAR_REQUEST_CREATED, CarResponse.class);

        assertThat(carResponse)
                .usingRecursiveComparison()
                .ignoringFields(ID_PARAMETER_NAME)
                .isEqualTo(CAR_RESPONSE_CREATED);
    }

    @Test
    void createCar_InvalidRequest_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .createEntityException(INVALID_CAR_REQUEST, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void updateCar_ValidRequestAndId_ReturnsValidResponseEntity() {
        CarResponse carResponse = ControllerRestAssuredMethods
                .updateEntity(CAR_REQUEST_UPDATED, CAR_ID, CarResponse.class);

        assertThat(carResponse)
                .usingRecursiveComparison()
                .isEqualTo(CAR_RESPONSE_UPDATED);
    }

    @Test
    void updateCar_InvalidRequest_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .updateEntityException(INVALID_CAR_REQUEST, CAR_ID, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void updateCar_InvalidId_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .updateEntityException(CAR_REQUEST_UPDATED, INVALID_CAR_ID, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void updateCar_CarNotFound_ReturnsExceptionResponseWithNotFoundStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .updateEntityException(CAR_REQUEST_UPDATED, NOT_EXIST_CAR_ID, HttpStatus.NOT_FOUND);

        assertExceptionResponse(exception, HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteCar_ValidId_ReturnsVoid() {
        ControllerRestAssuredMethods.deleteEntity(CAR_ID);
    }

    @Test
    void deleteCar_InvalidId_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .deleteEntityException(INVALID_CAR_ID, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void deleteCar_CarNotFound_ReturnsExceptionResponseWithNotFoundStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .deleteEntityException(NOT_EXIST_CAR_ID, HttpStatus.NOT_FOUND);

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
