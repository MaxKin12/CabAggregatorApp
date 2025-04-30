package com.example.ratesservice.e2e;

import static com.example.ratesservice.configuration.constants.GeneralUtilityConstants.CONTROLLER_BASE_URI;
import static com.example.ratesservice.configuration.constants.GeneralUtilityConstants.ENDPOINT_WITH_DRIVERS;
import static com.example.ratesservice.configuration.constants.GeneralUtilityConstants.ENDPOINT_WITH_PASSENGERS_ID;
import static com.example.ratesservice.configuration.constants.GeneralUtilityConstants.GET_DRIVER_REQUEST_URL;
import static com.example.ratesservice.configuration.constants.GeneralUtilityConstants.GET_PASSENGER_REQUEST_URL;
import static com.example.ratesservice.configuration.constants.GeneralUtilityConstants.GET_RIDES_REQUEST_URL;
import static com.example.ratesservice.configuration.constants.GeneralUtilityConstants.VALUE_PARAMETER_NAME;
import static com.example.ratesservice.configuration.constants.JsonExternalResponses.DRIVER_JSON_RESPONSE;
import static com.example.ratesservice.configuration.constants.JsonExternalResponses.PASSENGER_JSON_RESPONSE;
import static com.example.ratesservice.configuration.constants.JsonExternalResponses.RIDE_JSON_RESPONSE;
import static com.example.ratesservice.configuration.constants.RateTestData.DRIVER_ID;
import static com.example.ratesservice.configuration.constants.RateTestData.PASSENGER_ID;
import static com.example.ratesservice.configuration.constants.RateTestData.RIDE_ID;
import static com.example.ratesservice.configuration.constants.SqlConstants.SQL_DELETE_ALL_TEST_DATA;
import static com.example.ratesservice.configuration.constants.GeneralUtilityConstants.ENDPOINT_WITH_ID;
import static com.example.ratesservice.configuration.constants.GeneralUtilityConstants.ID_PARAMETER_NAME;
import static com.example.ratesservice.configuration.wiremock.WireMockStubs.setGetResponseStub;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.example.ratesservice.dto.rate.response.RatePageResponse;
import com.example.ratesservice.dto.rate.request.RateRequest;
import com.example.ratesservice.dto.rate.response.RateResponse;
import com.example.ratesservice.dto.rate.request.RateUpdateRequest;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor
public class StepDefinitions {

    private final JdbcTemplate jdbcTemplate;
    private Response response;
    private Long rateId;
    private RateRequest rateRequest;

    @LocalServerPort
    private int curPort;

    @PostConstruct
    public void setUp() {
        baseURI = CONTROLLER_BASE_URI;
        port = curPort;
    }

    @Before
    public void restoreDbData() {
        jdbcTemplate.execute(SQL_DELETE_ALL_TEST_DATA);
    }

    @Given("{string} with id {string} wants to rate {int} the ride {long} with driver {string} and left the comment {string}")
    public void withIdWantsToRateTheRideWithDriverAndLeftTheComment(
            String givenRecipientType,
            String givenPassengerId,
            Integer givenRateValue,
            Long givenRideId,
            String givenDriverId,
            String givenComment) {
        rateRequest = RateRequest.builder()
                .passengerId(UUID.fromString(givenPassengerId))
                .rideId(givenRideId)
                .driverId(UUID.fromString(givenDriverId))
                .recipient(givenRecipientType)
                .value(givenRateValue)
                .comment(givenComment)
                .build();

        setGetResponseStub(GET_PASSENGER_REQUEST_URL, PASSENGER_ID.toString(), PASSENGER_JSON_RESPONSE);
        setGetResponseStub(GET_DRIVER_REQUEST_URL, DRIVER_ID.toString(), DRIVER_JSON_RESPONSE);
        setGetResponseStub(GET_RIDES_REQUEST_URL, RIDE_ID.toString(), RIDE_JSON_RESPONSE);
    }

    @When("the passenger rates a ride")
    public void thePassengerRatesARide() {
        response = given()
                .contentType(ContentType.JSON)
                .body(rateRequest)
                .when()
                .post();
    }

    @Then("the rate is created")
    public void theRateIsCreated() {
        RateResponse rateResponse = response.then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(RateResponse.class);
        rateId = rateResponse.id();
    }

    @And("we check the existence")
    public void weCheckTheExistence() {
        given()
                .pathParam(ID_PARAMETER_NAME, rateId)
                .when()
                .get(ENDPOINT_WITH_ID)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @When("we try to find the rate by id")
    public void weTryToFindTheRateById() {
        response = given()
                .pathParam(ID_PARAMETER_NAME, rateId)
                .when()
                .get(ENDPOINT_WITH_ID);
    }

    @Then("we successfully get the rate")
    public void weSuccessfullyGetTheRate() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }

    @When("passenger tries to find his average rate")
    public void passengerTriesToFindHisAverageRate() {
        response = given()
                .pathParam(ID_PARAMETER_NAME, rateId)
                .when()
                .get(ENDPOINT_WITH_PASSENGERS_ID);
    }

    @Then("we successfully get the average rate")
    public void weSuccessfullyGetTheAverageRate() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }

    @When("we try to check all driver's rates")
    public void checkAllDriversRates() {
        response = given()
                .when()
                .get(ENDPOINT_WITH_DRIVERS);
    }

    @Then("we get the page")
    public void weGetThePage() {
        RatePageResponse ratePageResponse = response.then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body("totalElements", equalTo(1))
                .extract()
                .as(RatePageResponse.class);
        assertThat(ratePageResponse.rateList().get(0).id()).isEqualTo(rateId);
    }

    @When("passenger changes the rate value to {int}")
    public void passengerChangesTheRateValueTo(Integer rateValue) {
        RateUpdateRequest updateRequest = RateUpdateRequest.builder()
                .value(rateValue)
                .build();

        response = given()
                .contentType(ContentType.JSON)
                .pathParam(ID_PARAMETER_NAME, rateId)
                .body(updateRequest)
                .when()
                .patch(ENDPOINT_WITH_ID);
    }

    @Then("the rate value is changed")
    public void theRateValueIsChanged() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }

    @And("we check value changes to {int}")
    public void weCheckChanges(Integer rateValue) {
        given()
                .pathParam(ID_PARAMETER_NAME, rateId)
                .when()
                .get(ENDPOINT_WITH_ID)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(VALUE_PARAMETER_NAME, equalTo(rateValue));
    }

    @When("passenger deletes the rate")
    public void passengerDeletesTheRate() {
        response = given()
                .pathParam(ID_PARAMETER_NAME, rateId)
                .when()
                .delete(ENDPOINT_WITH_ID);
    }

    @Then("the rate is actually deleted")
    public void theRateIsActuallyDeleted() {
        response.then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given()
                .pathParam(ID_PARAMETER_NAME, rateId)
                .when()
                .get(ENDPOINT_WITH_ID)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
