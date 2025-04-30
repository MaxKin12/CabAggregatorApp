package com.example.ratesservice.model;

import com.example.ratesservice.enums.RecipientType;
import com.example.ratesservice.enums.converter.RecipientTypeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "rates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "update rates set deleted_at=current_timestamp() where id=?")
@SQLRestriction("deleted_at is null")
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "ride_id", nullable = false)
    private UUID rideId;

    @Column(name = "passenger_id", nullable = false)
    private UUID passengerId;

    @Column(name = "driver_id", nullable = false)
    private UUID driverId;

    @Column(name = "recipient")
    @Convert(converter = RecipientTypeConverter.class)
    private RecipientType recipient;

    @Column(name = "value", nullable = false)
    private Integer value;

    @Column(name = "comment")
    private String comment;

    @Column(name = "deleted_at")
    private LocalDateTime deleteAt;

}
