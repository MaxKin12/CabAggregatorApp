package com.example.ratesservice.configuration.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JsonExternalResponses {

    public final static String PASSENGER_JSON_RESPONSE = """
            {
                "id": "e5f3a033-51a5-4bc0-9443-2b63d5f59ca3",
                "name": "John Cena",
                "email": "john@gmail.com",
                "phone": "+375291111111",
                "rate": 4.52
            }
            """;
    public final static String DRIVER_JSON_RESPONSE = """
            {
                "id": "4ba41155-bcff-48d5-8675-bc0aac800e99",
                "name": "John Cena",
                "email": "john@gmail.com",
                "phone": "+375291111111",
                "gender": "male",
                "rate": 4.52,
                "carIds": [1]
            }
            """;
    public final static String RIDE_JSON_RESPONSE = """
            {
                "id": 1,
                "passengerId": "e5f3a033-51a5-4bc0-9443-2b63d5f59ca3",
                "driverId": "4ba41155-bcff-48d5-8675-bc0aac800e99",
                "carId": 1,
                "pickUpAddress": "Независимости 4, Минск",
                "destinationAddress": "Гикало 9, Минск",
                "status": "completed",
                "orderTime": "2025-02-02T15:00:00",
                "price": 17.50
            }
            """;

}
