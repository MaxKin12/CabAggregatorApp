package com.example.driverservice.model;

import com.example.driverservice.model.enums.Sex;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

import static com.example.driverservice.constant.SqlConstants.SQL_DELETE_DRIVER_REPLACEMENT;

@Entity
@Table(name = "drivers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = SQL_DELETE_DRIVER_REPLACEMENT)
@SQLRestriction("deleted_at is null")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "email", unique = true, length = 100, nullable = false)
    private String email;
    @Column(name = "phone", unique = true, length = 13, nullable = false)
    private String phone;
    @Column(name = "sex", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Sex sex;
    @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Car car;
    @Column(name = "deleted_at")
    private LocalDateTime deleteAt;
}
