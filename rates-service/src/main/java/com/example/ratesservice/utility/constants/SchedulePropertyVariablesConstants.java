package com.example.ratesservice.utility.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class SchedulePropertyVariablesConstants {

    public static final String FIXED_DELAY = "${schedule.fixed-delay}";
    public static final String SCHEDULER_LOCK_NAME_PASSENGER = "${schedule.scheduler-lock-name-passenger}";
    public static final String SCHEDULER_LOCK_NAME_DRIVER = "${schedule.scheduler-lock-name-driver}";
    public static final String LOCK_AT_LEAST_FOR = "${schedule.lock-at-least-for}";
    public static final String LOCK_AT_MOST_FOR = "${schedule.lock-at-most-for}";

}
