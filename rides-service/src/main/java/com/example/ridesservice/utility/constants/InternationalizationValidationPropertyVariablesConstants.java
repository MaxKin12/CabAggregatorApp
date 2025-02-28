package com.example.ridesservice.utility.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InternationalizationValidationPropertyVariablesConstants {

    public static final String ID_NEGATIVE = "{validate.method.parameter.id.negative}";
    public static final String PASSENGER_ID_NULL = "{validate.field.passenger-id.null}";
    public static final String PASSENGER_ID_NEGATIVE = "{validate.field.passenger-id.negative}";
    public static final String DRIVER_ID_NEGATIVE = "{validate.field.driver-id.negative}";
    public static final String CAR_ID_NEGATIVE = "{validate.field.car-id.negative}";
    public static final String PICK_UP_ADDRESS_BLANK = "{validate.field.pick-up-address.blank}";
    public static final String PICK_UP_ADDRESS_TOO_LONG = "{validate.field.pick-up-address.too-long}";
    public static final String DESTINATION_ADDRESS_BLANK = "{validate.field.destination-address.blank}";
    public static final String DESTINATION_ADDRESS_TOO_LONG = "{validate.field.destination-address.too-long}";
    public static final String STATUS_BLANK = "{validate.field.status.blank}";
    public static final String STATUS_INVALID_PATTERN = "{validate.field.status.invalid.pattern}";

}
