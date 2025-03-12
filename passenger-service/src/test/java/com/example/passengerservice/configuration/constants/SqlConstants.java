package com.example.passengerservice.configuration.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER_EMAIL;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER_EMAIL_2;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER_ID;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER_ID_2;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER_NAME;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER_NAME_2;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER_PHONE;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER_PHONE_2;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER_RATE;
import static com.example.passengerservice.configuration.constants.PassengerTestData.PASSENGER_RATE_2;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SqlConstants {

    public final static String SQL_DELETE_ALL_TEST_DATA = "delete from test.passengers;";
    public final static String SQL_INSERT_TEST_ENTITY_DATA =
            "insert into passengers (id, name, email, phone, rate) values (" +
                    PASSENGER_ID + ", '" +
                    PASSENGER_NAME + "', '" +
                    PASSENGER_EMAIL + "', '" +
                    PASSENGER_PHONE + "', " +
                    PASSENGER_RATE + ");";
    public final static String SQL_INSERT_TEST_ENTITY_DATA_2 =
            "insert into passengers (id, name, email, phone, rate) values (" +
                    PASSENGER_ID_2 + ", '" +
                    PASSENGER_NAME_2 + "', '" +
                    PASSENGER_EMAIL_2 + "', '" +
                    PASSENGER_PHONE_2 + "', " +
                    PASSENGER_RATE_2 + ");";

}
