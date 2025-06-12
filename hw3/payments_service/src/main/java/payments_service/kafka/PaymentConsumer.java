package payments_service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import common_lib.config.Consts;
import lombok.extern.slf4j.Slf4j;
import payments_service.entities.PaymentRequest;
import payments_service.storages.PaymentRequestStorage;

@Component
@Slf4j
public class PaymentConsumer {
    private static final String GROUP_ID = "payments_service";

    private PaymentRequestStorage paymentRequests;

    @Autowired
    public PaymentConsumer(PaymentRequestStorage paymentRequests) {
        this.paymentRequests = paymentRequests;
    }

    @KafkaListener(topics = Consts.PAYMENT_REQUESTS_TOPIC, groupId = GROUP_ID)
    public void processPaymentResponse(common_lib.models.PaymentRequest request) {
        var r = PaymentRequest.fromModel(request);
        if (paymentRequests.existsById(r.getOrderId())) {
            log.debug("Got duplicate payment request for order {}", request.orderId());
            return;
        }

        log.info("Received payment request for order {}", request.orderId());
        paymentRequests.save(r);
    }
}
