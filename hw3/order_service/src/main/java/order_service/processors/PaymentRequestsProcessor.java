package order_service.processors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import order_service.entities.PaymentRequest;
import order_service.kafka.PaymentProducer;
import order_service.storages.PaymentRequestStorage;

@Service
@Slf4j
public class PaymentRequestsProcessor {
    private static final int PROCESS_DELAY = 1000;

    private PaymentRequestStorage paymentRequests;
    private PaymentProducer producer;

    @Autowired
    public PaymentRequestsProcessor(PaymentRequestStorage paymentRequests, PaymentProducer producer) {
        this.paymentRequests = paymentRequests;
        this.producer = producer;
    }

    @Scheduled(fixedDelay = PROCESS_DELAY)
    public void process() {
        var requests = paymentRequests.findAll();
        if (requests.isEmpty()) {
            log.debug("No payment requests to process");
            return;
        }

        log.debug("Processing {} payment requests", requests.size());

        var successCount = 0;
        for (var request : requests) {
            if (processOne(request)) {
                successCount++;
            }
        }

        log.debug("Successfully processed {} requests", successCount);
    }

    @Transactional
    boolean processOne(PaymentRequest request) {
        try {
            producer.sendPaymentRequest(request.toModel());

            paymentRequests.delete(request);

            return true;
        } catch (Exception e) {
            log.error("failed to process payment request", e);
            return false;
        }
    }
}
