package com.example.driverservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

import static com.example.driverservice.constant.SqlConstants.SQL_DELETE_CAR_REPLACEMENT;

@Entity
@Table(name = "cars")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = SQL_DELETE_CAR_REPLACEMENT)
@SQLRestriction("deleted_at is null")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "brand", nullable = false, length = 30)
    private String brand;
    @Column(name = "number", unique = true, length = 8, nullable = false)
    private String number;
    @Column(name = "color", length = 20, nullable = false)
    private String color;
    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id")
    private Driver driver;
    @Column(name = "deleted_at")
    private LocalDateTime deleteAt;
}
