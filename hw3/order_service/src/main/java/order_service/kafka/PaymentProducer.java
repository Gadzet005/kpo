package order_service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import common_lib.config.Consts;
import common_lib.models.PaymentRequest;

@Service
public class PaymentProducer {
    private KafkaTemplate<String, PaymentRequest> paymentRequestQueue;

    @Autowired
    public PaymentProducer(KafkaTemplate<String, PaymentRequest> paymentRequestQueue) {
        this.paymentRequestQueue = paymentRequestQueue;
    }

    public void sendPaymentRequest(PaymentRequest request) {
        var key = String.valueOf(request.orderId());
        paymentRequestQueue.send(Consts.PAYMENT_REQUESTS_TOPIC, key, request);
    }
}