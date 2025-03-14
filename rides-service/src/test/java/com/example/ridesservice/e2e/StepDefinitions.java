package com.example.ridesservice.e2e;

import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.CONTROLLER_BASE_URI;
import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.ENDPOINT_FOR_BOOKING;
import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.ENDPOINT_FOR_SETTING_DRIVER;
import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.ENDPOINT_FOR_STATUS_UPDATING;
import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.ENDPOINT_WITH_ID;
import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.ENDPOINT_WITH_PASSENGER_ID;
import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.GET_CAR_REQUEST_URL;
import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.GET_DRIVER_REQUEST_URL;
import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.GET_PASSENGER_REQUEST_URL;
import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.ID_PARAMETER_NAME;
import static com.example.ridesservice.configuration.constants.GeneralUtilityConstants.STATUS_PARAMETER_NAME;
import static com.example.ridesservice.configuration.constants.JsonExternalResponses.CAR_JSON_RESPONSE;
import static com.example.ridesservice.configuration.constants.JsonExternalResponses.DRIVER_JSON_RESPONSE;
import static com.example.ridesservice.configuration.constants.JsonExternalResponses.PASSENGER_JSON_RESPONSE;
import static com.example.ridesservice.configuration.constants.RideTestData.RIDE_BOOKING_REQUEST;
import static com.example.ridesservice.configuration.constants.RideTestData.RIDE_SETTING_REQUEST;
import static com.example.ridesservice.configuration.constants.SqlConstants.SQL_DELETE_ALL_TEST_DATA;
import static com.example.ridesservice.configuration.constants.SqlConstants.SQL_INSERT_TEST_DATA;
import static com.example.ridesservice.configuration.constants.SqlConstants.SQL_INSERT_TEST_DATA_2;
import static com.example.ridesservice.configuration.wiremock.WireMockStubs.setGetResponseStub;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.example.ridesservice.dto.ride.request.RideStatusRequest;
import com.example.ridesservice.dto.ride.response.RidePageResponse;
import com.example.ridesservice.dto.ride.response.RideResponse;
import io.cucumber.java.Before;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor
public class StepDefinitions {

    private final JdbcTemplate jdbcTemplate;
    private Response response;
    private Long rideId;
    private Long passengerId;

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
        jdbcTemplate.execute(SQL_INSERT_TEST_DATA);
        jdbcTemplate.execute(SQL_INSERT_TEST_DATA_2);
    }

    @Given("passenger with id {long} and driver with id {long} and car with id {long}")
    public void passengerWithIdAndDriverWithId(Long givenPassengerId, Long givenDriverId, Long givenCarId) {
        passengerId = givenPassengerId;

        setGetResponseStub(GET_PASSENGER_REQUEST_URL, givenPassengerId.toString(), PASSENGER_JSON_RESPONSE);
        setGetResponseStub(GET_DRIVER_REQUEST_URL, givenDriverId.toString(), DRIVER_JSON_RESPONSE);
        setGetResponseStub(GET_CAR_REQUEST_URL, givenCarId.toString(), CAR_JSON_RESPONSE);
    }

    @When("the passenger books a ride")
    public void thePassengerBooksARide() {
        response = given()
                .contentType(ContentType.JSON)
                .body(RIDE_BOOKING_REQUEST)
                .when()
                .post(ENDPOINT_FOR_BOOKING);
    }

    @Then("the ride is create actually and stored in a queue")
    public void theRideIsCreateActuallyAndStoredInAQueue() {
        RideResponse rideResponse = response.then()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(ContentType.JSON)
                .extract()
                .as(RideResponse.class);
        rideId = rideResponse.id();
        given()
                .pathParam(ID_PARAMETER_NAME, rideId)
                .when()
                .get(ENDPOINT_WITH_ID)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value());
    }

    @When("the driver is ready and accepts a ride")
    public void theDriverIsReadyAndAcceptsARide() {
        response = given()
                .contentType(ContentType.JSON)
                .body(RIDE_SETTING_REQUEST)
                .when()
                .patch(ENDPOINT_FOR_SETTING_DRIVER);
    }

    @Then("the driver is attached to the ride and rides status is changed to {string}")
    public void theDriverIsAttachedToTheRideAndRidesStatusIsChangedTo(String status) {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .contentType(ContentType.JSON)
                .body(STATUS_PARAMETER_NAME, equalTo(status))
                .extract()
                .as(RideResponse.class);
    }

    @When("we try to find the ride by id")
    public void weTryToFindTheRideById() {
        response = given()
                .pathParam(ID_PARAMETER_NAME, rideId)
                .when()
                .get(ENDPOINT_WITH_ID);
    }

    @Then("we successfully get the ride")
    public void weSuccessfullyGetTheRide() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }

    @When("the passenger search for the last ride")
    public void thePassengerSearchForTheLastRide() {
        response = given()
                .queryParam("limit", 1)
                .pathParam(ID_PARAMETER_NAME, passengerId)
                .when()
                .get(ENDPOINT_WITH_PASSENGER_ID);
    }

    @Then("he gets the current ride")
    public void heGetsTheCurrentRide() {
        RidePageResponse ridePageResponse = response.then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .extract()
                .as(RidePageResponse.class);

        assertThat(ridePageResponse.rideList().size())
                .isEqualTo(1);
        assertThat(ridePageResponse.rideList().get(0).id())
                .isEqualTo(rideId);
    }

    @When("a person changes the ride status to {string}")
    public void aPersonChangesTheRideStatusTo(String status) {
        RideStatusRequest statusRequest = new RideStatusRequest(status);

        response = given()
                .contentType(ContentType.JSON)
                .pathParam(ID_PARAMETER_NAME, rideId)
                .body(statusRequest)
                .when()
                .patch(ENDPOINT_FOR_STATUS_UPDATING);
    }

    @Then("the ride status is actually changed to {string}")
    public void theRideStatusIsActuallyChangedTo(String status) {
        response.then()
                .statusCode(HttpStatus.OK.value());

        given()
                .pathParam(ID_PARAMETER_NAME, rideId)
                .when()
                .get(ENDPOINT_WITH_ID)
                .then()
                .assertThat()
                .statusCode(HttpStatus.OK.value())
                .body(STATUS_PARAMETER_NAME, equalTo(status));
    }

    @When("a person deletes the ride")
    public void aPersonDeletesTheRide() {
        response = given()
                .pathParam(ID_PARAMETER_NAME, rideId)
                .when()
                .delete(ENDPOINT_WITH_ID);
    }

    @Then("the ride is actually deleted")
    public void theRideIsActuallyDeleted() {
        response.then()
                .statusCode(HttpStatus.NO_CONTENT.value());

        given()
                .pathParam(ID_PARAMETER_NAME, rideId)
                .when()
                .get(ENDPOINT_WITH_ID)
                .then()
                .assertThat()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

}
