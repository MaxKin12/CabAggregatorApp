package com.example.passengerservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

import static com.example.passengerservice.constant.SqlConstants.SQL_DELETE_REPLACEMENT;

@Entity
@Table(name = "passenger")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = SQL_DELETE_REPLACEMENT)
@SQLRestriction("deleted_at is null")
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "name", nullable = false, length = 50)
    private String name;
    @Column(name = "email", unique = true, length = 100, nullable = false)
    private String email;
    @Column(name = "phone", unique = true, length = 20, nullable = false)
    private String phone;
    @Column(name = "deleted_at")
    private LocalDateTime deleteAt;
}
