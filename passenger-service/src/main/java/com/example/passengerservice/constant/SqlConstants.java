package com.example.passengerservice.constant;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SqlConstants {
    public static final String SQL_DELETE_REPLACEMENT = "update passenger set deleted_at=current_timestamp() where id=?";
}
