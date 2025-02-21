package com.example.ratesservice.utility.kafka;

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

    @Scheduled(fixedDelayString = "${schedule.fixed-delay}")
    @SchedulerLock(
            name = "${schedule.scheduler-lock-name-passenger}",
            lockAtLeastFor = "${schedule.lock-at-least-for}",
            lockAtMostFor = "${schedule.lock-at-most-for}"
    )
    public void sendPassengerTopicEvents() {
        List<RateChangeEvent> events = rateEventsRepository.findTopByRecipientTypeOrderByChangedAt(
                RecipientType.PASSENGER,
                Pageable.ofSize(kafkaProperties.schedulerProcessingBatchSize())
        );
        for (RateChangeEvent event : events) {
            producerService.sendAndDeleteMessage(kafkaProperties.topicPassengerRate(), event);
        }
    }

    @Scheduled(fixedDelayString = "${schedule.fixed-delay}")
    @SchedulerLock(
            name = "${schedule.scheduler-lock-name-driver}",
            lockAtLeastFor = "${schedule.lock-at-least-for}",
            lockAtMostFor = "${schedule.lock-at-most-for}"
    )
    public void sendDriverTopicEvents() {
        List<RateChangeEvent> events = rateEventsRepository.findTopByRecipientTypeOrderByChangedAt(
                RecipientType.DRIVER,
                Pageable.ofSize(kafkaProperties.schedulerProcessingBatchSize())
        );
        for (RateChangeEvent event : events) {
            producerService.sendAndDeleteMessage(kafkaProperties.topicDriverRate(), event);
        }
    }

}
