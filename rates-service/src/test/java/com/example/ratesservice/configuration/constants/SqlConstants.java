package com.example.ratesservice.configuration.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.example.ratesservice.configuration.constants.RateTestData.COMMENT;
import static com.example.ratesservice.configuration.constants.RateTestData.COMMENT_2;
import static com.example.ratesservice.configuration.constants.RateTestData.DRIVER_ID;
import static com.example.ratesservice.configuration.constants.RateTestData.PASSENGER_ID;
import static com.example.ratesservice.configuration.constants.RateTestData.RATE_ID;
import static com.example.ratesservice.configuration.constants.RateTestData.RATE_ID_2;
import static com.example.ratesservice.configuration.constants.RateTestData.RECIPIENT;
import static com.example.ratesservice.configuration.constants.RateTestData.RIDE_ID;
import static com.example.ratesservice.configuration.constants.RateTestData.VALUE;
import static com.example.ratesservice.configuration.constants.RateTestData.VALUE_2;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SqlConstants {

    public final static String SQL_DELETE_ALL_TEST_DATA = "delete from rates;";
    public final static String SQL_INSERT_TEST_DATA =
            "insert into rates (id, ride_id, passenger_id, driver_id, recipient, value, comment) values (" +
                    RATE_ID + ", " +
                    RIDE_ID + ", " +
                    PASSENGER_ID + ", " +
                    DRIVER_ID + ", " +
                    RECIPIENT + ", " +
                    VALUE + ", '" +
                    COMMENT +  "');";
    public final static String SQL_INSERT_TEST_DATA_2 =
            "insert into rates (id, ride_id, passenger_id, driver_id, recipient, value, comment) values (" +
                    RATE_ID_2 + ", " +
                    RIDE_ID + ", " +
                    PASSENGER_ID + ", " +
                    DRIVER_ID + ", " +
                    RECIPIENT + ", " +
                    VALUE_2 + ", '" +
                    COMMENT_2 +  "');";

}
