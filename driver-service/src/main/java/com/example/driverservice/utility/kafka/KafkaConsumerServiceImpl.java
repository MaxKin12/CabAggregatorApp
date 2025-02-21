package com.example.driverservice.utility.kafka;

import static com.example.driverservice.utility.constants.InternationalizationExceptionVariablesConstants.DRIVER_NOT_FOUND;

import com.example.driverservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.driverservice.exception.custom.ResourceNotFoundException;
import com.example.driverservice.model.entity.Driver;
import com.example.driverservice.repository.DriverRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KafkaConsumerServiceImpl {

    private final DriverRepository driverRepository;

    private final MessageSource messageSource;

    @KafkaListener(
            topics = "${spring.kafka.consumer.topic-driver-rate}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listen(RateChangeEventResponse event) {
        updateRate(event);
    }

    @Transactional
    protected void updateRate(RateChangeEventResponse event) {
        Driver driver = findByIdOrThrow(event.recipientId());
        driver.setRate(event.rate());
        driverRepository.save(driver);
    }

    private Driver findByIdOrThrow(Long id) {
        return driverRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(getExceptionMessage(id)));
    }

    private String getExceptionMessage(Object... args) {
        return messageSource.getMessage(DRIVER_NOT_FOUND, args, LocaleContextHolder.getLocale());
    }

}
