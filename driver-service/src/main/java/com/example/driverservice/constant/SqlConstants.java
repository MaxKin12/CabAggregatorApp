package com.example.driverservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SqlConstants {
    public static final String SQL_DELETE_DRIVER_REPLACEMENT =
            "update drivers set deleted_at=current_timestamp() where id=?";
    public static final String SQL_DELETE_CAR_REPLACEMENT = "update car set deleted_at=current_timestamp() where id=?";
}
