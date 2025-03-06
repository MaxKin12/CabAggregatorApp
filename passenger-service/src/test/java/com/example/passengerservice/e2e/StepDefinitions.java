package com.example.passengerservice.e2e;

import static com.example.passengerservice.utility.constants.GeneralUtilityConstants.CONTROLLER_BASE_URI_DETERMINED;
import static com.example.passengerservice.utility.constants.GeneralUtilityConstants.ENDPOINT_WITH_ID;
import static com.example.passengerservice.utility.constants.GeneralUtilityConstants.ID_PARAMETER_NAME;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.passengerservice.dto.passenger.PassengerRequest;
import com.example.passengerservice.dto.passenger.PassengerResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class StepDefinitions {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private Long passengerId;
    private Response response;
    private PassengerRequest passengerRequest;
    private PassengerResponse actualResponse;

    @Before
    public void init() {
        baseURI = CONTROLLER_BASE_URI_DETERMINED;
    }

    @Given("passenger with id {long}")
    public void findPassengerById(Long id) {
        passengerId = id;
    }

    @Given("passenger's data for signing up")
    @SneakyThrows
    public void signingPassengerData(String createPassengerJson) {
        passengerRequest = objectMapper.readValue(createPassengerJson, PassengerRequest.class);
    }

    @Given("passenger's data for update with id {long}")
    @SneakyThrows
    public void updatedPassengerData(Long id, String updatePassengerJson) {
        passengerId = id;
        passengerRequest = objectMapper.readValue(updatePassengerJson, PassengerRequest.class);
    }

    @When("searching passenger by id")
    public void searchEntityById() {
        response = given()
                .pathParam(ID_PARAMETER_NAME, passengerId)
                .when()
                .get(ENDPOINT_WITH_ID);
    }

    @When("getting all passengers")
    public void getAllPassengers() {
        response = given()
                .when()
                .get();
    }

    @When("saving passenger")
    public void passengerGetSaved() {
        response = given()
                .contentType(ContentType.JSON)
                .body(passengerRequest)
                .when()
                .post();
    }

    @When("updating passenger")
    public void passengerUpdated() {
        response = given()
                .pathParam(ID_PARAMETER_NAME, passengerId)
                .contentType(ContentType.JSON)
                .body(passengerRequest)
                .when()
                .patch(ENDPOINT_WITH_ID);
    }

    @When("deleting passenger")
    public void passengerDeleted() {
        response = given()
                .pathParam(ID_PARAMETER_NAME, passengerId)
                .when()
                .delete(ENDPOINT_WITH_ID);
    }

    @Then("received response has status {int}")
    public void receivedResponseHasStatus(int status) {
        response.then()
                .statusCode(status);
    }

    @And("body equals to")
    @SneakyThrows
    public void bodyEqualsTo(String expectedPassengerJson) {
        PassengerResponse expectedResponse = objectMapper.readValue(expectedPassengerJson, PassengerResponse.class);
        PassengerResponse actualResponse = response.as(PassengerResponse.class);

        assertThat(expectedResponse)
                .usingRecursiveComparison()
                .ignoringFields(ID_PARAMETER_NAME)
                .isEqualTo(actualResponse);

        assertEquals(expectedResponse, actualResponse);
    }

    @And("body after create operation equals to")
    @SneakyThrows
    public void createdBodyEqualsTo(String expectedPassengerJson) {
        PassengerResponse expectedResponse = objectMapper.readValue(expectedPassengerJson, PassengerResponse.class);
        actualResponse = response.as(PassengerResponse.class);

        assertThat(expectedResponse)
                .usingRecursiveComparison()
                .ignoringFields(ID_PARAMETER_NAME)
                .isEqualTo(actualResponse);
    }

    @And("check if created in db")
    public void checkIfCreatedInDb() {
        given()
                .pathParam(ID_PARAMETER_NAME, actualResponse.id())
                .when()
                .get(ENDPOINT_WITH_ID)
                .then()
                .statusCode(HttpStatus.OK.value());
    }

}
