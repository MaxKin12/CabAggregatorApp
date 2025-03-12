package com.example.driverservice.integration.controller;

import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.DRIVER_CONTROLLER_BASE_URI;
import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.ID_PARAMETER_NAME;
import static com.example.driverservice.configuration.constants.DriverTestData.INVALID_LIMIT;
import static com.example.driverservice.configuration.constants.DriverTestData.INVALID_OFFSET;
import static com.example.driverservice.configuration.constants.DriverTestData.INVALID_DRIVER_ID;
import static com.example.driverservice.configuration.constants.DriverTestData.INVALID_DRIVER_REQUEST;
import static com.example.driverservice.configuration.constants.DriverTestData.LIMIT;
import static com.example.driverservice.configuration.constants.DriverTestData.NOT_EXIST_DRIVER_ID;
import static com.example.driverservice.configuration.constants.DriverTestData.OFFSET;
import static com.example.driverservice.configuration.constants.DriverTestData.DRIVER_ID;
import static com.example.driverservice.configuration.constants.DriverTestData.DRIVER_REQUEST_CREATED;
import static com.example.driverservice.configuration.constants.DriverTestData.DRIVER_REQUEST_UPDATED;
import static com.example.driverservice.configuration.constants.DriverTestData.DRIVER_RESPONSE;
import static com.example.driverservice.configuration.constants.DriverTestData.DRIVER_RESPONSE_CREATED;
import static com.example.driverservice.configuration.constants.DriverTestData.DRIVER_RESPONSE_UPDATED;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.port;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.driverservice.configuration.container.MySqlTestContainer;
import com.example.driverservice.dto.exception.ExceptionHandlerResponse;
import com.example.driverservice.dto.driver.DriverResponse;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
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
public class DriverControllerIT extends MySqlTestContainer {

    @LocalServerPort
    private int curPort;

    @PostConstruct
    void setUp() {
        baseURI = DRIVER_CONTROLLER_BASE_URI;
        port = curPort;
    }

    @Test
    void getDriver_ValidId_ReturnsValidResponseEntity() {
        DriverResponse driverResponse = ControllerRestAssuredMethods
                .getEntity(DRIVER_ID, DriverResponse.class);

        assertThat(driverResponse)
                .usingRecursiveComparison()
                .isEqualTo(DRIVER_RESPONSE);
    }

    @Test
    void getDriver_InvalidId_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .getEntityException(INVALID_DRIVER_ID, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void getDriver_DriverNotFound_ReturnsExceptionResponseWithNotFoundStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .getEntityException(NOT_EXIST_DRIVER_ID, HttpStatus.NOT_FOUND);

        assertExceptionResponse(exception, HttpStatus.NOT_FOUND);
    }

    @Test
    void getAllDrivers_ValidOffsetAndLimitParameters_ReturnsValidResponseEntity() {
        ControllerRestAssuredMethods
                .getAllEntities(OFFSET, LIMIT);
    }

    @Test
    void getAllDrivers_InvalidOffsetParameter_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .getAllEntitiesException(INVALID_OFFSET, LIMIT, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void getAllDrivers_InvalidLimitParameter_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .getAllEntitiesException(OFFSET, INVALID_LIMIT, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void createDriver_ValidRequest_ReturnsValidResponseEntity() {
        DriverResponse driverResponse = ControllerRestAssuredMethods
                .createEntity(DRIVER_REQUEST_CREATED, DriverResponse.class);

        assertThat(driverResponse)
                .usingRecursiveComparison()
                .ignoringFields(ID_PARAMETER_NAME)
                .isEqualTo(DRIVER_RESPONSE_CREATED);
    }

    @Test
    void createDriver_InvalidRequest_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .createEntityException(INVALID_DRIVER_REQUEST, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void updateDriver_ValidRequestAndId_ReturnsValidResponseEntity() {
        DriverResponse driverResponse = ControllerRestAssuredMethods
                .updateEntity(DRIVER_REQUEST_UPDATED, DRIVER_ID, DriverResponse.class);

        assertThat(driverResponse)
                .usingRecursiveComparison()
                .isEqualTo(DRIVER_RESPONSE_UPDATED);
    }

    @Test
    void updateDriver_InvalidRequest_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .updateEntityException(INVALID_DRIVER_REQUEST, DRIVER_ID, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void updateDriver_InvalidId_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .updateEntityException(DRIVER_REQUEST_UPDATED, INVALID_DRIVER_ID, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void updateDriver_DriverNotFound_ReturnsExceptionResponseWithNotFoundStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .updateEntityException(DRIVER_REQUEST_UPDATED, NOT_EXIST_DRIVER_ID, HttpStatus.NOT_FOUND);

        assertExceptionResponse(exception, HttpStatus.NOT_FOUND);
    }

    @Test
    void deleteDriver_ValidId_ReturnsVoid() {
        ControllerRestAssuredMethods.deleteEntity(DRIVER_ID);
    }

    @Test
    void deleteDriver_InvalidId_ReturnsExceptionResponseWithBadRequestStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .deleteEntityException(INVALID_DRIVER_ID, HttpStatus.BAD_REQUEST);

        assertExceptionResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @Test
    void deleteDriver_DriverNotFound_ReturnsExceptionResponseWithNotFoundStatus() {
        ExceptionHandlerResponse exception = ControllerRestAssuredMethods
                .deleteEntityException(NOT_EXIST_DRIVER_ID, HttpStatus.NOT_FOUND);

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
