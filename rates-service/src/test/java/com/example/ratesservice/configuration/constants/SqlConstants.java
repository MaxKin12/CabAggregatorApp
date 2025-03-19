package com.example.ratesservice.configuration.constants;

import static com.example.ratesservice.configuration.constants.RateTestData.COMMENT;
import static com.example.ratesservice.configuration.constants.RateTestData.COMMENT_2;
import static com.example.ratesservice.configuration.constants.RateTestData.DRIVER_ID;
import static com.example.ratesservice.configuration.constants.RateTestData.PASSENGER_ID;
import static com.example.ratesservice.configuration.constants.RateTestData.RATE_ID;
import static com.example.ratesservice.configuration.constants.RateTestData.RATE_ID_2;
import static com.example.ratesservice.configuration.constants.RateTestData.RECIPIENT;
import static com.example.ratesservice.configuration.constants.RateTestData.RIDE_ID;
import static com.example.ratesservice.configuration.constants.RateTestData.RIDE_ID_2;
import static com.example.ratesservice.configuration.constants.RateTestData.VALUE;
import static com.example.ratesservice.configuration.constants.RateTestData.VALUE_2;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SqlConstants {

    public final static String SQL_DELETE_ALL_TEST_DATA = "delete from rates;";

    public final static String SQL_INSERT_TEST_DATA =
        "insert into rates(id, ride_id, passenger_id, driver_id, recipient, value, comment)" +
        "values (:id, :rideId, :passengerId, :driverId, :recipient, :value, :comment);";

    public final static SqlParameterSource INSERT_DATA_PARAMS = new MapSqlParameterSource()
            .addValue("id", RATE_ID)
            .addValue("rideId", RIDE_ID)
            .addValue("passengerId", PASSENGER_ID)
            .addValue("driverId", DRIVER_ID)
            .addValue("recipient", RECIPIENT)
            .addValue("value", VALUE)
            .addValue("comment", COMMENT);

    public final static SqlParameterSource INSERT_DATA_PARAMS_2 = new MapSqlParameterSource()
            .addValue("id", RATE_ID_2)
            .addValue("rideId", RIDE_ID_2)
            .addValue("passengerId", PASSENGER_ID)
            .addValue("driverId", DRIVER_ID)
            .addValue("recipient", RECIPIENT)
            .addValue("value", VALUE_2)
            .addValue("comment", COMMENT_2);

}
