package com.example.passengerservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name="passenger")
@SQLDelete(sql="update passenger set deleted_at=current_timestamp() where id=?")
@SQLRestriction("deleted_at is null")
@NoArgsConstructor
@AllArgsConstructor
public class Passenger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="name", nullable = false, length = 50)
    private String name;
    @Column(name="email", unique = true, length = 100, nullable = false)
    private String email;
    @Column(name="phone", unique = true, length = 20, nullable = false)
    private String phone;
    @Column(name="deleted_at")
    private LocalDateTime deleteAt;
    @Column(name="password", nullable = false)
    private String password;
}
