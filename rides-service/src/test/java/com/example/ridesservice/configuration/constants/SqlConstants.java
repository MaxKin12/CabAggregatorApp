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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SqlConstants {

    public final static String SQL_DELETE_ALL_TEST_DATA = "delete from rides;";

    public final static String SQL_INSERT_TEST_DATA =
        "insert into rides" +
        "(id, passenger_id, driver_id, car_id, pick_up_address, destination_address, status, order_time, price)" +
        "values" +
        "(:id, :passengerId, :driverId, :carId, :pickUpAddress, :destinationAddress, :status, :orderTime, :price);";

    public final static SqlParameterSource INSERT_DATA_PARAMS = new MapSqlParameterSource()
            .addValue("id", RIDE_ID)
            .addValue("passengerId", PASSENGER_ID)
            .addValue("driverId", DRIVER_ID)
            .addValue("carId", CAR_ID)
            .addValue("pickUpAddress", PICK_UP_ADDRESS)
            .addValue("destinationAddress", DESTINATION_ADDRESS)
            .addValue("status", RideStatus.COMPLETED.getCode())
            .addValue("orderTime", ORDER_TIME)
            .addValue("price", PRICE);

    public final static SqlParameterSource INSERT_DATA_PARAMS_2 = new MapSqlParameterSource()
            .addValue("id", RIDE_ID_2)
            .addValue("passengerId", PASSENGER_ID_2)
            .addValue("driverId", DRIVER_ID_2)
            .addValue("carId", CAR_ID_2)
            .addValue("pickUpAddress", PICK_UP_ADDRESS_2)
            .addValue("destinationAddress", DESTINATION_ADDRESS_2)
            .addValue("status", RideStatus.CANCELLED.getCode())
            .addValue("orderTime", ORDER_TIME_2)
            .addValue("price", PRICE_2);

}
