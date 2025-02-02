package com.example.passengerservice.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RegularExpressionsConstants {
    public static final String PHONE_NUMBER_CHECK = "^\\+375(24|25|29|33|44)\\d{7}$";
}
