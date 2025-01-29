package com.example.driverservice.model;

import com.example.driverservice.enums.Sex;
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

@Entity
@Table(name = "drivers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "update drivers set deleted_at=current_timestamp() where id=?")
@SQLRestriction("deleted_at is null")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", length = 50, nullable = false)
    private String name;
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;
    @Column(name = "phone", length = 13, nullable = false, unique = true)
    private String phone;
    @Column(name = "sex", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Sex sex;
    @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Car car;
    @Column(name = "deleted_at")
    private LocalDateTime deleteAt;
}
