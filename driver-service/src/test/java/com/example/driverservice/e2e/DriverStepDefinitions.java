package com.example.driverservice.e2e;

import static com.example.driverservice.utility.constants.GeneralUtilityConstants.DRIVER_CONTROLLER_BASE_URI_DETERMINED;
import static com.example.driverservice.utility.constants.GeneralUtilityConstants.ENDPOINT_WITH_ID;
import static com.example.driverservice.utility.constants.GeneralUtilityConstants.ID_PARAMETER_NAME;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.driverservice.dto.driver.DriverRequest;
import com.example.driverservice.dto.driver.DriverResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

public class DriverStepDefinitions {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Long driverId;
    private Response response;
    private DriverRequest driverRequest;
    private DriverResponse actualResponse;

    @Given("driver with id {long}")
    public void findDriverById(Long id) {
        driverId = id;
    }

    @Given("driver's data for signing up")
    @SneakyThrows
    public void signingDriverData(String createDriverJson) {
        driverRequest = objectMapper.readValue(createDriverJson, DriverRequest.class);
    }

    @Given("driver's data for update with id {long}")
    @SneakyThrows
    public void updatedDriverData(Long id, String updateDriverJson) {
        driverId = id;
        driverRequest = objectMapper.readValue(updateDriverJson, DriverRequest.class);
    }

    @When("searching driver by id")
    public void searchDriverById() {
        baseURI = DRIVER_CONTROLLER_BASE_URI_DETERMINED;
        response = given()
                .pathParam(ID_PARAMETER_NAME, driverId)
                .when()
                .get(ENDPOINT_WITH_ID);
    }

    @When("getting all drivers")
    public void getAllDrivers() {
        baseURI = DRIVER_CONTROLLER_BASE_URI_DETERMINED;
        response = given()
                .when()
                .get();
    }

    @When("saving driver")
    public void driverGetSaved() {
        baseURI = DRIVER_CONTROLLER_BASE_URI_DETERMINED;
        response = given()
                .contentType(ContentType.JSON)
                .body(driverRequest)
                .when()
                .post();
    }

    @When("updating driver")
    public void driverUpdated() {
        baseURI = DRIVER_CONTROLLER_BASE_URI_DETERMINED;
        response = given()
                .pathParam(ID_PARAMETER_NAME, driverId)
                .contentType(ContentType.JSON)
                .body(driverRequest)
                .when()
                .patch(ENDPOINT_WITH_ID);
    }

    @When("deleting driver")
    public void driverDeleted() {
        baseURI = DRIVER_CONTROLLER_BASE_URI_DETERMINED;
        response = given()
                .pathParam(ID_PARAMETER_NAME, driverId)
                .when()
                .delete(ENDPOINT_WITH_ID);
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
        DriverResponse actualResponse = response.as(DriverResponse.class);

        assertThat(expectedResponse)
                .usingRecursiveComparison()
                .ignoringFields(ID_PARAMETER_NAME)
                .isEqualTo(actualResponse);
    }

    @And("driver body no carsId equals to")
    @SneakyThrows
    public void driverNoCarsBodyEqualsTo(String expectedDriverJson) {
        DriverResponse expectedResponse = objectMapper.readValue(expectedDriverJson, DriverResponse.class);
        DriverResponse actualResponse = response.as(DriverResponse.class);

        assertThat(expectedResponse)
                .usingRecursiveComparison()
                .ignoringFields("carIds")
                .isEqualTo(actualResponse);
    }

    @And("body after driver create operation equals to")
    @SneakyThrows
    public void createdDriverBodyEqualsTo(String expectedDriverJson) {
        DriverResponse expectedResponse = objectMapper.readValue(expectedDriverJson, DriverResponse.class);
        actualResponse = response.as(DriverResponse.class);

        assertThat(expectedResponse)
                .usingRecursiveComparison()
                .ignoringFields(ID_PARAMETER_NAME)
                .isEqualTo(actualResponse);
    }

    @And("check if driver created in db")
    public void checkIfDriverCreatedInDb() {
        baseURI = DRIVER_CONTROLLER_BASE_URI_DETERMINED;
        given()
                .pathParam(ID_PARAMETER_NAME, actualResponse.id())
                .when()
                .get(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

}
