package com.example.driverservice.model.entity;

import com.example.driverservice.enums.UserGender;
import com.example.driverservice.enums.converter.UserGenderConverter;
import com.example.driverservice.model.DriverEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Table(name = "drivers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "update drivers set deleted_at=current_timestamp() where id=?")
@SQLRestriction("deleted_at is null")
public class Driver implements DriverEntity {

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
    @Convert(converter = UserGenderConverter.class)
    private UserGender gender;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Car> cars;

    @Column(name = "deleted_at")
    private LocalDateTime deleteAt;

}
