package payments_service.processors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import payments_service.entities.PaymentResult;
import payments_service.kafka.PaymentProducer;
import payments_service.storages.PaymentResultStorage;

@Service
@Slf4j
public class PaymentResultsProcessor {
    private static final int PROCESS_DELAY = 1000; // 1 second

    private PaymentResultStorage paymentResults;
    private PaymentProducer producer;

    @Autowired
    public PaymentResultsProcessor(PaymentResultStorage paymentResults, PaymentProducer producer) {
        this.paymentResults = paymentResults;
        this.producer = producer;
    }

    @Scheduled(fixedDelay = PROCESS_DELAY)
    public void process() {
        var results = paymentResults.findAll();
        if (results.isEmpty()) {
            log.debug("No payment results to process");
            return;
        }

        log.debug("Processing {} payment results", results.size());

        var successCount = 0;
        for (var result : results) {
            if (processOne(result)) {
                successCount++;
            }
        }

        log.debug("Successfully processed {} results", successCount);
    }

    @Transactional
    boolean processOne(PaymentResult result) {
        try {
            producer.sendPaymentResult(result.toModel());
            paymentResults.delete(result);
            return true;
        } catch (Exception e) {
            log.error("failed to process payment result", e);
            return false;
        }
    }
}
