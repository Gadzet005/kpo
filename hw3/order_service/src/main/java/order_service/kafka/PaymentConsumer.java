package order_service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import common_lib.models.PaymentResult;
import common_lib.config.Consts;
import common_lib.models.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import order_service.services.OrderService;

@Component
@Slf4j
public class PaymentConsumer {
    private static final String GROUP_ID = "order_service";

    private OrderService orderService;

    @Autowired
    public PaymentConsumer(OrderService orderService) {
        this.orderService = orderService;
    }

    @KafkaListener(topics = Consts.PAYMENT_RESULTS_TOPIC, groupId = GROUP_ID)
    public void processPaymentResponse(PaymentResult result) {
        log.info("received payment result: orderId = {}, success = {}", result.orderId(), result.success());

        var status = result.success() ? OrderStatus.FINISHED : OrderStatus.CANCELLED;
        orderService.updateOrderStatus(result.orderId(), status);
    }
}
