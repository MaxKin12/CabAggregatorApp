package com.example.driverservice.utility.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegularExpressionsConstants {

    public static final String PHONE_NUMBER_CHECK = "^\\+375(24|25|29|33|44)\\d{7}$";
    public static final String CAR_NUMBER_CHECK = "^\\d{4}[ABEIKMHOPCTX]{2}-[1-7]$";

}
