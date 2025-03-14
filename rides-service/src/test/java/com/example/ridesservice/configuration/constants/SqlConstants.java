package com.example.ridesservice.configuration.constants;

import static com.example.ridesservice.configuration.constants.RideTestData.CAR_ID;
import static com.example.ridesservice.configuration.constants.RideTestData.CAR_ID_2;
import static com.example.ridesservice.configuration.constants.RideTestData.DESTINATION_ADDRESS;
import static com.example.ridesservice.configuration.constants.RideTestData.DESTINATION_ADDRESS_2;
import static com.example.ridesservice.configuration.constants.RideTestData.DRIVER_ID;
import static com.example.ridesservice.configuration.constants.RideTestData.DRIVER_ID_2;
import static com.example.ridesservice.configuration.constants.RideTestData.ORDER_TIME;
import static com.example.ridesservice.configuration.constants.RideTestData.ORDER_TIME_2;
import static com.example.ridesservice.configuration.constants.RideTestData.PASSENGER_ID;
import static com.example.ridesservice.configuration.constants.RideTestData.PASSENGER_ID_2;
import static com.example.ridesservice.configuration.constants.RideTestData.PICK_UP_ADDRESS;
import static com.example.ridesservice.configuration.constants.RideTestData.PICK_UP_ADDRESS_2;
import static com.example.ridesservice.configuration.constants.RideTestData.PRICE;
import static com.example.ridesservice.configuration.constants.RideTestData.PRICE_2;
import static com.example.ridesservice.configuration.constants.RideTestData.RIDE_ID;
import static com.example.ridesservice.configuration.constants.RideTestData.RIDE_ID_2;

import com.example.ridesservice.enums.RideStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SqlConstants {

    public final static String SQL_DELETE_ALL_TEST_DATA = "delete from rides;";
    public final static String SQL_INSERT_TEST_DATA =
            "insert into rides (id, passenger_id, driver_id, car_id, pick_up_address, destination_address," +
                    "status, order_time, price) values (" +
                    RIDE_ID + ", " +
                    PASSENGER_ID + ", " +
                    DRIVER_ID + ", " +
                    CAR_ID + ", '" +
                    PICK_UP_ADDRESS + "', '" +
                    DESTINATION_ADDRESS + "', " +
                    RideStatus.COMPLETED.getCode() + ", '" +
                    ORDER_TIME + "', " +
                    PRICE + ");";
    public final static String SQL_INSERT_TEST_DATA_2 =
            "insert into rides (id, passenger_id, driver_id, car_id, pick_up_address, destination_address," +
                    "status, order_time, price) values (" +
                    RIDE_ID_2 + ", " +
                    PASSENGER_ID_2 + ", " +
                    DRIVER_ID_2 + ", " +
                    CAR_ID_2 + ", '" +
                    PICK_UP_ADDRESS_2 + "', '" +
                    DESTINATION_ADDRESS_2 + "', " +
                    RideStatus.CANCELLED.getCode() + ", '" +
                    ORDER_TIME_2 + "', " +
                    PRICE_2 + ");";

}
