package order_service.controllers;

import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;
import order_service.services.OrderService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import common_lib.grpc.CreateOrderRequest;
import common_lib.grpc.CreateOrderResponse;
import common_lib.grpc.GetOrderInfoRequest;
import common_lib.grpc.GetUserOrdersRequest;
import common_lib.grpc.GetUserOrdersResponse;
import common_lib.grpc.OrderInfo;
import common_lib.models.Order;
import common_lib.models.OrderStatus;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OrderGrpcControllerTest {

    @Mock
    private OrderService orderService;

    @Mock
    private StreamObserver<CreateOrderResponse> createResponseObserver;

    @Mock
    private StreamObserver<GetUserOrdersResponse> getOrdersResponseObserver;

    @Mock
    private StreamObserver<OrderInfo> orderInfoResponseObserver;

    @InjectMocks
    private OrderGrpcController orderGrpcController;

    @Test
    void testCreateOrder() {
        var request = CreateOrderRequest.newBuilder().setUserId(1).setAmount(100).setDescription("Test order").build();
        var order = new Order(1, 1, 100, "Test order", OrderStatus.NEW, Instant.now());

        when(orderService.createOrder(anyInt(), anyInt(), anyString())).thenReturn(order);

        orderGrpcController.createOrder(request, createResponseObserver);

        verify(createResponseObserver).onNext(any(CreateOrderResponse.class));
        verify(createResponseObserver).onCompleted();
    }

    @Test
    void testCreateOrderWithError() {
        var request = CreateOrderRequest.newBuilder().setUserId(1).setAmount(100).setDescription("Test order").build();

        doThrow(new RuntimeException()).when(orderService).createOrder(anyInt(), anyInt(), anyString());

        orderGrpcController.createOrder(request, createResponseObserver);

        verify(createResponseObserver).onError(any(StatusRuntimeException.class));
    }

    @Test
    void testGetUserOrders() {
        var request = GetUserOrdersRequest.newBuilder().setUserId(1).build();
        var orders = List.of(new Order(1, 1, 100, "Test order", OrderStatus.NEW, Instant.now()),
                new Order(2, 1, 200, "Test order", OrderStatus.NEW, Instant.now()));

        when(orderService.getUserOrders(anyInt())).thenReturn(orders);

        orderGrpcController.getUserOrders(request, getOrdersResponseObserver);

        verify(getOrdersResponseObserver).onNext(any(GetUserOrdersResponse.class));
        verify(getOrdersResponseObserver).onCompleted();
    }

    @Test
    void testGetUserOrdersWithError() {
        var request = GetUserOrdersRequest.newBuilder().setUserId(1).build();

        when(orderService.getUserOrders(anyInt())).thenThrow(new RuntimeException());

        orderGrpcController.getUserOrders(request, getOrdersResponseObserver);

        verify(getOrdersResponseObserver, times(1)).onError(any(StatusRuntimeException.class));
    }

    @Test
    void testGetOrderInfo() {
        var request = GetOrderInfoRequest.newBuilder().setOrderId(1).build();
        var order = new Order(1, 1, 100, "Test order", OrderStatus.NEW, Instant.now());

        when(orderService.getOrder(anyInt())).thenReturn(Optional.of(order));

        orderGrpcController.getOrderInfo(request, orderInfoResponseObserver);

        verify(orderInfoResponseObserver).onNext(any(OrderInfo.class));
        verify(orderInfoResponseObserver).onCompleted();
    }

    @Test
    void testGetOrderInfoWithError() {
        var request = GetOrderInfoRequest.newBuilder().setOrderId(1).build();

        when(orderService.getOrder(anyInt())).thenReturn(Optional.empty()).thenThrow(new RuntimeException());

        orderGrpcController.getOrderInfo(request, orderInfoResponseObserver);
        orderGrpcController.getOrderInfo(request, orderInfoResponseObserver);

        verify(orderInfoResponseObserver, times(2)).onError(any(StatusRuntimeException.class));
    }
}
