package payments_service.processors;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import payments_service.entities.PaymentRequest;
import payments_service.services.PaymentsService;
import payments_service.storages.PaymentRequestStorage;

@Service
@Slf4j
public class PaymentRequestsProcessor {
    private static final int PROCESS_DELAY = 1000; // 1 second
    private static final int CLEANUP_DELAY = 1000 * 60 * 60 * 24; // 24 hours

    private PaymentRequestStorage paymentRequests;
    private PaymentsService paymentsService;

    @Autowired
    public PaymentRequestsProcessor(PaymentRequestStorage paymentRequests, PaymentsService paymentsService) {
        this.paymentRequests = paymentRequests;
        this.paymentsService = paymentsService;
    }

    @Scheduled(fixedDelay = PROCESS_DELAY)
    public void process() {
        var requests = paymentRequests.findAllByProcessed(false).stream().toList();
        if (requests.isEmpty()) {
            return;
        }

        log.info("Processing {} payment requests", requests.size());

        int successCount = 0;
        for (var request : requests) {
            if (processOne(request)) {
                successCount++;
            }
        }

        log.info("Successfully processed {} requests", successCount);
    }

    @Transactional
    public boolean processOne(PaymentRequest request) {
        try {
            paymentsService.processOrderPayment(request.toModel());

            request.setProcessed(true);
            paymentRequests.save(request);
            return true;
        } catch (Exception e) {
            log.error("failed to process payment request", e);
            return false;
        }
    }

    @Scheduled(fixedDelay = CLEANUP_DELAY)
    public void cleanup() {
        log.info("Cleaning up payment requests");

        paymentRequests.cleanup(Instant.now().minusMillis(CLEANUP_DELAY));
    }
}