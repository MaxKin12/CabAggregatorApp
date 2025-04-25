package com.example.gatewayservice.utility.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UtilConstants {

    public static final String USER_ID_HEADER_NAME = "X-User-Id";
    public static final String REALM_ACCESS_CLAIM = "realm_access";
    public static final String REALM_ACCESS_ROLES = "roles";
    public static final String ROLE_PREFIX = "ROLE_";

}
