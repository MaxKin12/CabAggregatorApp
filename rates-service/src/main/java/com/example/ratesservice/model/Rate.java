package com.example.ratesservice.model;

import com.example.ratesservice.enums.RecipientType;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "rates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rate {

    @Id
    private UUID id;

    @Field("ride_id")
    private UUID rideId;

    @Field("passenger_id")
    private UUID passengerId;

    @Field("driver_id")
    private UUID driverId;

    @Field("recipient")
    private RecipientType recipient;

    @Field("value")
    private Integer value;

    @Field("comment")
    private String comment;

}
