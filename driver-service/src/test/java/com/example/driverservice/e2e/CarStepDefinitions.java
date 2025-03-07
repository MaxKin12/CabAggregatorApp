package com.example.driverservice.e2e;

import static com.example.driverservice.utility.constants.GeneralUtilityConstants.CAR_CONTROLLER_BASE_URI_DETERMINED;
import static com.example.driverservice.utility.constants.GeneralUtilityConstants.ENDPOINT_WITH_ID;
import static com.example.driverservice.utility.constants.GeneralUtilityConstants.ID_PARAMETER_NAME;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import com.example.driverservice.dto.car.CarRequest;
import com.example.driverservice.dto.car.CarResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

public class CarStepDefinitions {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Long carId;
    private Response response;
    private CarRequest carRequest;
    private CarResponse actualResponse;

    @Given("car with id {long}")
    public void findCarById(Long id) {
        carId = id;
    }

    @Given("car's data for signing up")
    @SneakyThrows
    public void signingUpCarData(String createCarJson) {
        carRequest = objectMapper.readValue(createCarJson, CarRequest.class);
    }

    @Given("car's data for update with id {long}")
    @SneakyThrows
    public void updatedCarData(Long id, String updateCarJson) {
        carId = id;
        carRequest = objectMapper.readValue(updateCarJson, CarRequest.class);
    }

    @When("searching car by id")
    public void searchCarById() {
        baseURI = CAR_CONTROLLER_BASE_URI_DETERMINED;
        response = given()
                .pathParam(ID_PARAMETER_NAME, carId)
                .when()
                .get(ENDPOINT_WITH_ID);
    }

    @When("getting all cars")
    public void getAllCars() {
        response = given()
                .when()
                .get();
    }

    @When("saving car")
    public void carGetSaved() {
        baseURI = CAR_CONTROLLER_BASE_URI_DETERMINED;
        response = given()
                .contentType(ContentType.JSON)
                .body(carRequest)
                .when()
                .post();
    }

    @When("updating car")
    public void carUpdated() {
        baseURI = CAR_CONTROLLER_BASE_URI_DETERMINED;
        response = given()
                .pathParam(ID_PARAMETER_NAME, carId)
                .contentType(ContentType.JSON)
                .body(carRequest)
                .when()
                .patch(ENDPOINT_WITH_ID);
    }

    @When("deleting car")
    public void carDeleted() {
        baseURI = CAR_CONTROLLER_BASE_URI_DETERMINED;
        response = given()
                .pathParam(ID_PARAMETER_NAME, carId)
                .when()
                .delete(ENDPOINT_WITH_ID);
    }

    @Then("received car response has status {int}")
    public void receivedCarResponseHasStatus(int status) {
        response.then().statusCode(status);
    }

    @And("car body equals to")
    @SneakyThrows
    public void carBodyEqualsTo(String expectedCarJson) {
        CarResponse expectedResponse = objectMapper.readValue(expectedCarJson, CarResponse.class);
        CarResponse actualResponse = response.as(CarResponse.class);

        assertThat(expectedResponse)
                .usingRecursiveComparison()
                .ignoringFields(ID_PARAMETER_NAME)
                .isEqualTo(actualResponse);
    }

    @And("body after create car operation equals to")
    @SneakyThrows
    public void carCreatedBodyEqualsTo(String expectedCarJson) {
        CarResponse expectedResponse = objectMapper.readValue(expectedCarJson, CarResponse.class);
        actualResponse = response.as(CarResponse.class);

        assertThat(expectedResponse)
                .usingRecursiveComparison()
                .ignoringFields(ID_PARAMETER_NAME)
                .isEqualTo(actualResponse);
    }

    @And("check if created car in db")
    public void checkIfCreatedCarInDb() {
        baseURI = CAR_CONTROLLER_BASE_URI_DETERMINED;
        given()
                .pathParam(ID_PARAMETER_NAME, actualResponse.id())
                .when()
                .get(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

}
