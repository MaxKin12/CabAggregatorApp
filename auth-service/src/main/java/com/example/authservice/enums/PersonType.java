package com.example.authservice.enums;

public enum PersonType {

    ADMIN("admin"),
    PASSENGER("passenger"),
    DRIVER("driver");

    private final String displayName;

    PersonType(String displayName) {
        this.displayName = displayName;
    }

    public String getType() {
        return displayName;
    }

}
