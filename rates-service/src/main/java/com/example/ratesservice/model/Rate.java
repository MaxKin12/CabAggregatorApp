package com.example.ratesservice.model;

import com.example.ratesservice.enums.AuthorType;
import com.example.ratesservice.enums.converter.AuthorTypeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
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
@SQLDelete(sql = "update rates set deleted_at=current_timestamp() where id=?")
@SQLRestriction("deleted_at is null")
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "passenger_id", nullable = false)
    private Long passengerId;

    @Column(name = "driver_id", nullable = false)
    private Long driverId;

    @Column(name = "author")
    @Convert(converter = AuthorTypeConverter.class)
    private AuthorType author;

    @Column(name = "value", nullable = false)
    private Integer value;

    @Column(name = "comment")
    private String comment;

    @Column(name = "deleted_at")
    private LocalDateTime deleteAt;

}
