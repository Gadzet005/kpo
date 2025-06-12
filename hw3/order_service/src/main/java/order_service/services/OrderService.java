package order_service.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common_lib.models.OrderStatus;
import jakarta.transaction.Transactional;
import order_service.entities.Order;
import order_service.entities.PaymentRequest;
import order_service.storages.PaymentRequestStorage;
import order_service.storages.OrderStorage;

@Service
public class OrderService {
    private OrderStorage orderStorage;
    private PaymentRequestStorage paymentRequests;

    @Autowired
    public OrderService(OrderStorage orderStorage, PaymentRequestStorage paymentRequests) {
        this.orderStorage = orderStorage;
        this.paymentRequests = paymentRequests;
    }

    @Transactional
    public common_lib.models.Order createOrder(int userId, int amount, String description) {
        var createdAt = Instant.now();
        var order = Order.builder().userId(userId).amount(amount).description(description).createdAt(createdAt).build();
        order = orderStorage.save(order);

        var request = PaymentRequest.builder().orderId(order.getId()).userId(userId).amount(amount).build();
        paymentRequests.save(request);

        return order.toModel();
    }

    public List<common_lib.models.Order> getUserOrders(int userId) {
        var orders = orderStorage.findAllByUserId(userId);
        return orders.stream().map(Order::toModel).toList();
    }

    public Optional<common_lib.models.Order> getOrder(int orderId) {
        var order = orderStorage.findById(orderId);
        return order.map(Order::toModel);
    }

    public void updateOrderStatus(int orderId, OrderStatus status) {
        var order = orderStorage.findById(orderId);
        if (order.isPresent()) {
            order.get().setStatus(status);
            orderStorage.save(order.get());
        }
    }
}
