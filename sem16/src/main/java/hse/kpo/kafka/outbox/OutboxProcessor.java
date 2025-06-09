package hse.kpo.kafka.outbox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import hse.kpo.kafka.CustomerAddedEvent;
import hse.kpo.kafka.KafkaProducerService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OutboxProcessor {
    private final OutboxEventRepository outboxEventRepository;
    private final KafkaProducerService kafkaProducerService;
    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public OutboxProcessor(OutboxEventRepository outboxEventRepository,
            KafkaProducerService kafkaProducerService) {
        this.outboxEventRepository = outboxEventRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void processOutbox() {
        log.info("Processing outbox messages...");

        outboxEventRepository.findAllBySentFalseOrderByCreatedAtAsc()
                .forEach(event -> {
                    try {
                        var customerEvent = objectMapper.readValue(
                                event.getPayload(), CustomerAddedEvent.class);
                        kafkaProducerService
                                .sendCustomerToTraining(customerEvent);
                        event.setSent(true);
                        outboxEventRepository.save(event);
                    } catch (Exception e) {
                        log.error("Failed to send event: {}",
                                event.getPayload());
                    }
                });
    }
}
