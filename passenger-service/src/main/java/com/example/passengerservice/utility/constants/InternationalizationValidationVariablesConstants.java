package com.example.passengerservice.utility.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InternationalizationValidationVariablesConstants {

    public static final String NAME_BLANK = "{validate.field.name.blank}";
    public static final String NAME_TOO_LONG = "{validate.field.name.too-long}";
    public static final String EMAIL_BLANK = "{validate.field.email.blank}";
    public static final String EMAIL_TOO_LONG = "{validate.field.email.too-long}";
    public static final String EMAIL_INVALID_PATTERN = "{validate.field.email.invalid.pattern}";
    public static final String PHONE_BLANK = "{validate.field.phone.blank}";
    public static final String PHONE_TOO_LONG = "{validate.field.phone.too-long}";
    public static final String PHONE_INVALID_PATTERN = "{validate.field.phone.invalid.pattern}";
    public static final String ID_NEGATIVE = "{validate.method.parameter.id.negative}";

}
