package com.example.ratesservice.kafka;

import static com.example.ratesservice.utility.constants.SchedulePropertyVariablesConstants.FIXED_DELAY;
import static com.example.ratesservice.utility.constants.SchedulePropertyVariablesConstants.LOCK_AT_LEAST_FOR;
import static com.example.ratesservice.utility.constants.SchedulePropertyVariablesConstants.LOCK_AT_MOST_FOR;
import static com.example.ratesservice.utility.constants.SchedulePropertyVariablesConstants.SCHEDULER_LOCK_NAME_DRIVER;
import static com.example.ratesservice.utility.constants.SchedulePropertyVariablesConstants.SCHEDULER_LOCK_NAME_PASSENGER;

import com.example.ratesservice.configuration.properties.KafkaProperties;
import com.example.ratesservice.enums.RecipientType;
import com.example.ratesservice.model.RateChangeEvent;
import com.example.ratesservice.repository.RateEventsRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(KafkaProperties.class)
public class KafkaScheduler {

    private final RateEventsRepository rateEventsRepository;
    private final KafkaProducerService producerService;
    private final KafkaProperties kafkaProperties;

    @Scheduled(fixedDelayString = FIXED_DELAY)
    @SchedulerLock(
            name = SCHEDULER_LOCK_NAME_PASSENGER,
            lockAtLeastFor = LOCK_AT_LEAST_FOR,
            lockAtMostFor = LOCK_AT_MOST_FOR
    )
    public void sendPassengerTopicEvents() {
        List<RateChangeEvent> events = rateEventsRepository.findTopByRecipientTypeOrderByChangedAt(
                RecipientType.PASSENGER,
                Pageable.ofSize(kafkaProperties.schedulerProcessingBatchSize())
        );
        for (RateChangeEvent event : events) {
            producerService.sendAndDeleteMessage(kafkaProperties.topicPassenger(), event);
        }
    }

    @Scheduled(fixedDelayString = FIXED_DELAY)
    @SchedulerLock(
            name = SCHEDULER_LOCK_NAME_DRIVER,
            lockAtLeastFor = LOCK_AT_LEAST_FOR,
            lockAtMostFor = LOCK_AT_MOST_FOR
    )
    public void sendDriverTopicEvents() {
        List<RateChangeEvent> events = rateEventsRepository.findTopByRecipientTypeOrderByChangedAt(
                RecipientType.DRIVER,
                Pageable.ofSize(kafkaProperties.schedulerProcessingBatchSize())
        );
        for (RateChangeEvent event : events) {
            producerService.sendAndDeleteMessage(kafkaProperties.topicDriver(), event);
        }
    }

}
