package com.example.passengerservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="passenger")
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
}
