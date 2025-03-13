package com.example.ridesservice.configuration.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonExternalResponses {

    public final static String PASSENGER_JSON_RESPONSE = """
            {
                "id": 1,
                "name": "John Cena",
                "email": "john@gmail.com",
                "phone": "+375291111111",
                "rate": 4.52
            }
            """;
    public final static String DRIVER_JSON_RESPONSE = """
            {
                "id": 1,
                "name": "John Cena",
                "email": "john@gmail.com",
                "phone": "+375291111111",
                "gender": "male",
                "rate": 4.52,
                "carIds": [1]
            }
            """;
    public final static String CAR_JSON_RESPONSE = """
            {
                "id": 1,
                "brand": "Toyota",
                "number": "1234AB-3",
                "color": "Red",
                "driverId": 1
            }
            """;

}
