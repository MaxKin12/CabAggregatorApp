package com.example.passengerservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SqlConstants {
    public static final String SQL_DELETE_REPLACEMENT = "update passenger set deleted_at=current_timestamp() where id=?";
}
