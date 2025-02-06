package com.example.ridesservice.model;

import com.example.ridesservice.enums.RideStatus;
import com.example.ridesservice.enums.converter.StatusConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "rides")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "update rides set deleted_at=current_timestamp() where id=?")
@SQLRestriction("deleted_at is null")
public class Ride {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "passenger_id", nullable = false)
    private Long passengerId;

    @Column(name = "driver_id")
    private Long driverId;

    @Column(name = "car_id")
    private Long carId;

    @Column(name = "pick_up_address", nullable = false, length = 100)
    private String pickUpAddress;

    @Column(name = "destination_address", nullable = false, length = 100)
    private String destinationAddress;

    @Column(name = "status", nullable = false)
    @Convert(converter = StatusConverter.class)
    private RideStatus status;

    @Column(name = "order_time", nullable = false)
    private LocalDateTime orderTime;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "deleted_at")
    private LocalDateTime deleteAt;

}
