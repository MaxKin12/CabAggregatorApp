package com.example.ratesservice.model;

import com.example.ratesservice.enums.RecipientType;
import com.example.ratesservice.enums.converter.RecipientTypeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "rate_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class RateChangeEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    @JdbcType(VarcharJdbcType.class)
    private UUID eventId;

    @Column(name = "recipient_id", nullable = false)
    @JdbcType(VarcharJdbcType.class)
    private UUID recipientId;

    @Column(name = "recipient_type", nullable = false)
    @Convert(converter = RecipientTypeConverter.class)
    private RecipientType recipientType;

    @Column(name = "rate", nullable = false)
    private BigDecimal rate;

    @Column(name = "changed_at")
    @UpdateTimestamp
    private LocalDateTime changedAt;

}
