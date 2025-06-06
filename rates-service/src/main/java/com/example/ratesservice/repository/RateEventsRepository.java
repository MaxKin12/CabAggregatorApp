package com.example.ratesservice.repository;

import com.example.ratesservice.enums.RecipientType;
import com.example.ratesservice.model.RateChangeEvent;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateEventsRepository extends JpaRepository<RateChangeEvent, UUID> {

    List<RateChangeEvent> findTopByRecipientTypeOrderByChangedAt(RecipientType recipientType, Pageable pageable);

}
