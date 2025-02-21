package com.example.passengerservice.utility.kafka;

import static com.example.passengerservice.utility.constants.InternationalizationExceptionVariablesConstants.PASSENGER_NOT_FOUND;

import com.example.passengerservice.dto.kafkaevent.RateChangeEventResponse;
import com.example.passengerservice.exception.custom.PassengerNotFoundException;
import com.example.passengerservice.model.Passenger;
import com.example.passengerservice.repository.PassengerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KafkaConsumerServiceImpl {

    private final PassengerRepository passengerRepository;

    private final MessageSource messageSource;

    @KafkaListener(
            topics = "${spring.kafka.consumer.topic-passenger-rate}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void listen(RateChangeEventResponse event) {
        updateRate(event);
    }

    @Transactional
    protected void updateRate(RateChangeEventResponse event) {
        Passenger passenger = findByIdOrThrow(event.recipientId());
        passenger.setRate(event.rate());
        passengerRepository.save(passenger);
    }

    private Passenger findByIdOrThrow(Long id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new PassengerNotFoundException(getExceptionMessage(id)));
    }

    private String getExceptionMessage(Object... args) {
        return messageSource.getMessage(PASSENGER_NOT_FOUND, args, LocaleContextHolder.getLocale());
    }

}
