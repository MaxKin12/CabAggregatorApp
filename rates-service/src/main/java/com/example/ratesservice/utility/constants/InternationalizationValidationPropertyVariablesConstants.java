package com.example.ratesservice.utility.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class InternationalizationValidationPropertyVariablesConstants {

    public static final String RIDE_ID_NULL = "{validate.field.ride-id.null}";
    public static final String RECIPIENT_BLANK = "{validate.field.recipient.blank}";
    public static final String RECIPIENT_INVALID_PATTERN = "{validate.field.recipient.invalid.pattern}";
    public static final String VALUE_NULL = "{validate.field.value.null}";
    public static final String COMMENT_TOO_LONG = "{validate.field.comment.too-long}";

}
