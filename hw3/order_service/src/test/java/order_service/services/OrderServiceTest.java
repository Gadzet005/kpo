package order_service.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import common_lib.models.OrderStatus;
import order_service.entities.Order;
import order_service.entities.PaymentRequest;
import order_service.storages.OrderStorage;
import order_service.storages.PaymentRequestStorage;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private OrderStorage orderStorage;

    @Mock
    private PaymentRequestStorage paymentRequests;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testCreateOrder() {
        var userId = 1;
        var amount = 100;
        var description = "Test order";
        var order = Order.builder().userId(userId).amount(amount).description(description).build();

        when(orderStorage.save(any(Order.class))).thenReturn(order);
        when(paymentRequests.save(any(PaymentRequest.class))).thenReturn(null);

        var result = orderService.createOrder(userId, amount, description);

        assertEquals(userId, result.userId());
        assertEquals(amount, result.amount());
        assertEquals(description, result.description());
    }

    @Test
    void testGetUserOrders() {
        var userId = 1;
        var orders = List.of(Order.builder().userId(userId).amount(100).description("Order 1").build(),
                Order.builder().userId(userId).amount(200).description("Order 2").build());

        when(orderStorage.findAllByUserId(userId)).thenReturn(orders);

        var result = orderService.getUserOrders(userId);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(order -> order.userId() == userId));
    }

    @Test
    void testGetOrder() {
        var orderId = 1;
        var order = Order.builder().id(orderId).userId(1).amount(100).description("Test order").build();

        when(orderStorage.findById(orderId)).thenReturn(Optional.of(order));

        var result = orderService.getOrder(orderId);

        assertTrue(result.isPresent());
        assertEquals(orderId, result.get().id());
    }

    @Test
    void testUpdateOrderStatus() {
        var orderId = 1;
        var status = OrderStatus.FINISHED;
        var order = Order.builder().id(orderId).userId(1).amount(100).description("Test order").build();

        when(orderStorage.findById(orderId)).thenReturn(Optional.of(order));

        orderService.updateOrderStatus(orderId, status);

        verify(orderStorage).save(order);
        assertEquals(status, order.getStatus());
    }

    @Test
    void testUpdateOrderStatusNotFound() {
        var orderId = 1;
        var status = OrderStatus.FINISHED;

        when(orderStorage.findById(orderId)).thenReturn(Optional.empty());

        orderService.updateOrderStatus(orderId, status);

        verify(orderStorage, never()).save(any(Order.class));
    }
}