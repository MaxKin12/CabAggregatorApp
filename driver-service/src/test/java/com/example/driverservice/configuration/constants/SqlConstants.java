package com.example.driverservice.configuration.constants;

import static com.example.driverservice.configuration.constants.CarTestData.CAR_BRAND;
import static com.example.driverservice.configuration.constants.CarTestData.CAR_BRAND_2;
import static com.example.driverservice.configuration.constants.CarTestData.CAR_COLOR;
import static com.example.driverservice.configuration.constants.CarTestData.CAR_COLOR_2;
import static com.example.driverservice.configuration.constants.CarTestData.CAR_ID;
import static com.example.driverservice.configuration.constants.CarTestData.CAR_ID_2;
import static com.example.driverservice.configuration.constants.CarTestData.CAR_NUMBER;
import static com.example.driverservice.configuration.constants.CarTestData.CAR_NUMBER_2;
import static com.example.driverservice.configuration.constants.DriverTestData.DRIVER_EMAIL;
import static com.example.driverservice.configuration.constants.DriverTestData.DRIVER_EMAIL_2;
import static com.example.driverservice.configuration.constants.DriverTestData.DRIVER_ID;
import static com.example.driverservice.configuration.constants.DriverTestData.DRIVER_ID_2;
import static com.example.driverservice.configuration.constants.DriverTestData.DRIVER_NAME;
import static com.example.driverservice.configuration.constants.DriverTestData.DRIVER_NAME_2;
import static com.example.driverservice.configuration.constants.DriverTestData.DRIVER_PHONE;
import static com.example.driverservice.configuration.constants.DriverTestData.DRIVER_PHONE_2;
import static com.example.driverservice.configuration.constants.DriverTestData.DRIVER_RATE;
import static com.example.driverservice.configuration.constants.DriverTestData.DRIVER_RATE_2;

import com.example.driverservice.enums.UserGender;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SqlConstants {

    public final static String SQL_DELETE_ALL_TEST_DATA_DRIVERS = "delete from drivers;";
    public final static String SQL_DELETE_ALL_TEST_DATA_CARS = "delete from cars;";
    public final static String SQL_INSERT_TEST_ENTITY_DATA_DRIVERS =
            "insert into drivers (id, name, email, phone, sex, rate) values (" +
                    DRIVER_ID + ", '" +
                    DRIVER_NAME + "', '" +
                    DRIVER_EMAIL + "', '" +
                    DRIVER_PHONE + "', " +
                    UserGender.MALE.getCode() + ", " +
                    DRIVER_RATE + ");\n";
    public final static String SQL_INSERT_TEST_ENTITY_DATA_DRIVERS_2 =
            "insert into drivers (id, name, email, phone, sex, rate) values (" +
                    DRIVER_ID_2 + ", '" +
                    DRIVER_NAME_2 + "', '" +
                    DRIVER_EMAIL_2 + "', '" +
                    DRIVER_PHONE_2 + "', " +
                    UserGender.MALE.getCode() + ", " +
                    DRIVER_RATE_2 + ");";
    public final static String SQL_INSERT_TEST_ENTITY_DATA_CARS =
            "insert into cars (id, brand, number, color, driver_id) values (" +
                    CAR_ID + ", '" +
                    CAR_BRAND + "', '" +
                    CAR_NUMBER + "', '" +
                    CAR_COLOR + "', " +
                    DRIVER_ID + ");";
    public final static String SQL_INSERT_TEST_ENTITY_DATA_CARS_2 =
            "insert into cars (id, brand, number, color, driver_id) values (" +
                    CAR_ID_2 + ", '" +
                    CAR_BRAND_2 + "', '" +
                    CAR_NUMBER_2 + "', '" +
                    CAR_COLOR_2 + "', " +
                    DRIVER_ID_2 + ");";

}
