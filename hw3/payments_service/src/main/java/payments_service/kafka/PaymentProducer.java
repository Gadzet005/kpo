package payments_service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import common_lib.config.Consts;
import common_lib.models.PaymentResult;

@Service
public class PaymentProducer {
    private KafkaTemplate<String, PaymentResult> paymentRequestQueue;

    @Autowired
    public PaymentProducer(KafkaTemplate<String, PaymentResult> paymentRequestQueue) {
        this.paymentRequestQueue = paymentRequestQueue;
    }

    public void sendPaymentResult(PaymentResult result) {
        var key = String.valueOf(result.orderId());
        paymentRequestQueue.send(Consts.PAYMENT_RESULTS_TOPIC, key, result);
    }
}