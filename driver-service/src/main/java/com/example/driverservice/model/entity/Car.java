package com.example.driverservice.model.entity;

import com.example.driverservice.model.DriverEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "cars")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@SQLDelete(sql = "update cars set deleted_at=current_timestamp() where id=?")
@SQLRestriction("deleted_at is null")
public class Car implements DriverEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "brand", nullable = false, length = 30)
    private String brand;

    @Column(name = "number", nullable = false, length = 8, unique = true)
    private String number;

    @Column(name = "color", nullable = false, length = 20)
    private String color;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", updatable = false)
    private Driver driver;

    @Column(name = "deleted_at")
    private LocalDateTime deleteAt;

}
