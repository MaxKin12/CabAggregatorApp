package com.example.driverservice.e2e;

import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.CONTROLLER_BASE_URI;
import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.DRIVER_ENDPOINT;
import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.DRIVER_ENDPOINT_WITH_ID;
import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.ID_PARAMETER_NAME;
import static com.example.driverservice.configuration.constants.SqlConstants.SQL_DELETE_ALL_TEST_DATA_CARS;
import static com.example.driverservice.configuration.constants.SqlConstants.SQL_DELETE_ALL_TEST_DATA_DRIVERS;
import static com.example.driverservice.configuration.constants.SqlConstants.SQL_INSERT_TEST_ENTITY_DATA_CARS;
import static com.example.driverservice.configuration.constants.SqlConstants.SQL_INSERT_TEST_ENTITY_DATA_CARS_2;
import static com.example.driverservice.configuration.constants.SqlConstants.SQL_INSERT_TEST_ENTITY_DATA_DRIVERS;
import static com.example.driverservice.configuration.constants.SqlConstants.SQL_INSERT_TEST_ENTITY_DATA_DRIVERS_2;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.driverservice.dto.driver.DriverRequest;
import com.example.driverservice.dto.driver.DriverResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor
public class DriverStepDefinitions {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;
    private Long driverId;
    private Response response;
    private DriverRequest driverRequest;
    private DriverResponse actualResponse;

    @LocalServerPort
    private int curPort;

    @PostConstruct
    public void setUp() {
        baseURI = CONTROLLER_BASE_URI;
        port = curPort;
    }

    @Before
    public void restoreDbData() {
        jdbcTemplate.execute(SQL_DELETE_ALL_TEST_DATA_CARS);
        jdbcTemplate.execute(SQL_DELETE_ALL_TEST_DATA_DRIVERS);
        jdbcTemplate.execute(SQL_INSERT_TEST_ENTITY_DATA_DRIVERS);
        jdbcTemplate.execute(SQL_INSERT_TEST_ENTITY_DATA_DRIVERS_2);
        jdbcTemplate.execute(SQL_INSERT_TEST_ENTITY_DATA_CARS);
        jdbcTemplate.execute(SQL_INSERT_TEST_ENTITY_DATA_CARS_2);
    }

    @Given("driver with id {long}")
    public void givenDriverWithId(Long id) {
        driverId = id;
    }

    @Given("driver's data for signing up")
    @SneakyThrows
    public void givenSigningDriverData(String createDriverJson) {
        driverRequest = objectMapper.readValue(createDriverJson, DriverRequest.class);
    }

    @Given("driver's data for update with id {long}")
    @SneakyThrows
    public void givenUpdatedDriverData(Long id, String updateDriverJson) {
        driverId = id;
        driverRequest = objectMapper.readValue(updateDriverJson, DriverRequest.class);
    }

    @When("searching driver by id")
    public void searchDriverById() {
        response = given()
                .pathParam(ID_PARAMETER_NAME, driverId)
                .when()
                .get(DRIVER_ENDPOINT_WITH_ID);
    }

    @When("getting all drivers")
    public void getAllDrivers() {
        response = given()
                .when()
                .get(DRIVER_ENDPOINT);
    }

    @When("saving driver")
    public void saveDriver() {
        response = given()
                .contentType(ContentType.JSON)
                .body(driverRequest)
                .when()
                .post(DRIVER_ENDPOINT);
    }

    @When("updating driver")
    public void updateDriver() {
        response = given()
                .pathParam(ID_PARAMETER_NAME, driverId)
                .contentType(ContentType.JSON)
                .body(driverRequest)
                .when()
                .patch(DRIVER_ENDPOINT_WITH_ID);
    }

    @When("deleting driver")
    public void deleteDriver() {
        response = given()
                .pathParam(ID_PARAMETER_NAME, driverId)
                .when()
                .delete(DRIVER_ENDPOINT_WITH_ID);
    }

    @Then("received driver response has status {int}")
    public void receivedDriverResponseHasStatus(int status) {
        response.then()
                .statusCode(status);
    }

    @And("driver body equals to")
    @SneakyThrows
    public void driverBodyEqualsTo(String expectedDriverJson) {
        DriverResponse expectedResponse = objectMapper.readValue(expectedDriverJson, DriverResponse.class);
        actualResponse = response.as(DriverResponse.class);

        assertThat(actualResponse)
                .usingRecursiveComparison()
                .ignoringFields("carIds")
                .isEqualTo(expectedResponse);
    }

    @And("body after driver create operation equals to")
    @SneakyThrows
    public void createdDriverBodyEqualsTo(String expectedDriverJson) {
        DriverResponse expectedResponse = objectMapper.readValue(expectedDriverJson, DriverResponse.class);
        actualResponse = response.as(DriverResponse.class);

        assertThat(actualResponse)
                .usingRecursiveComparison()
                .ignoringFields(ID_PARAMETER_NAME)
                .isEqualTo(expectedResponse);
    }

    @And("check if driver is created in db")
    public void checkIfDriverCreatedInDb() {
        given()
                .pathParam(ID_PARAMETER_NAME, actualResponse.id())
                .when()
                .get(DRIVER_ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @And("check if driver is deleted in db")
    public void checkIfDeletedInDb() {
        given()
                .pathParam(ID_PARAMETER_NAME, driverId)
                .when()
                .get(DRIVER_ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
