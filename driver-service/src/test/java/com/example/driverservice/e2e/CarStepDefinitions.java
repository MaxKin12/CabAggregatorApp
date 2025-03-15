package com.example.driverservice.e2e;

import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.CAR_ENDPOINT;
import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.CAR_ENDPOINT_WITH_ID;
import static com.example.driverservice.configuration.constants.GeneralUtilityConstants.CONTROLLER_BASE_URI;
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

import com.example.driverservice.dto.car.CarRequest;
import com.example.driverservice.dto.car.CarResponse;
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
public class CarStepDefinitions {

    private final JdbcTemplate jdbcTemplate;
    private final ObjectMapper objectMapper;
    private Long carId;
    private Response response;
    private CarRequest carRequest;
    private CarResponse actualResponse;

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

    @Given("car with id {long}")
    public void givenCarWithId(Long id) {
        carId = id;
    }

    @Given("car's data for signing up")
    @SneakyThrows
    public void givenSigningUpCarData(String createCarJson) {
        carRequest = objectMapper.readValue(createCarJson, CarRequest.class);
    }

    @Given("car's data for update with id {long}")
    @SneakyThrows
    public void givenUpdatedCarData(Long id, String updateCarJson) {
        carId = id;
        carRequest = objectMapper.readValue(updateCarJson, CarRequest.class);
    }

    @When("searching car by id")
    public void searchCarById() {
        response = given()
                .pathParam(ID_PARAMETER_NAME, carId)
                .when()
                .get(CAR_ENDPOINT_WITH_ID);
    }

    @When("getting all cars")
    public void getAllCars() {
        response = given()
                .when()
                .get(CAR_ENDPOINT);
    }

    @When("saving car")
    public void saveCar() {
        response = given()
                .contentType(ContentType.JSON)
                .body(carRequest)
                .when()
                .post(CAR_ENDPOINT);
    }

    @When("updating car")
    public void updateCar() {
        response = given()
                .pathParam(ID_PARAMETER_NAME, carId)
                .contentType(ContentType.JSON)
                .body(carRequest)
                .when()
                .patch(CAR_ENDPOINT_WITH_ID);
    }

    @When("deleting car")
    public void deleteCar() {
        response = given()
                .pathParam(ID_PARAMETER_NAME, carId)
                .when()
                .delete(CAR_ENDPOINT_WITH_ID);
    }

    @Then("received car response has status {int}")
    public void receivedCarResponseHasStatus(int status) {
        response.then()
                .statusCode(status);
    }

    @And("car body equals to")
    @SneakyThrows
    public void carBodyEqualsTo(String expectedCarJson) {
        CarResponse expectedResponse = objectMapper.readValue(expectedCarJson, CarResponse.class);
        actualResponse = response.as(CarResponse.class);

        assertThat(actualResponse)
                .usingRecursiveComparison()
                .isEqualTo(expectedResponse);
    }

    @And("body after create car operation equals to")
    @SneakyThrows
    public void carCreatedBodyEqualsTo(String expectedCarJson) {
        CarResponse expectedResponse = objectMapper.readValue(expectedCarJson, CarResponse.class);
        actualResponse = response.as(CarResponse.class);

        assertThat(actualResponse)
                .usingRecursiveComparison()
                .ignoringFields(ID_PARAMETER_NAME)
                .isEqualTo(expectedResponse);
    }

    @And("check if created car in db")
    public void checkIfCreatedCarInDb() {
        given()
                .pathParam(ID_PARAMETER_NAME, actualResponse.id())
                .when()
                .get(CAR_ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @And("check if car deleted in db")
    public void checkIfDeletedInDb() {
        given()
                .pathParam(ID_PARAMETER_NAME, carId)
                .when()
                .get(CAR_ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
