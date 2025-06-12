package api_gateway.clients;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import common_lib.convert.OrderGrpcConvert;
import common_lib.grpc.CreateOrderRequest;
import common_lib.grpc.CreateOrderResponse;
import common_lib.grpc.GetOrderInfoRequest;
import common_lib.grpc.GetUserOrdersRequest;
import common_lib.grpc.GetUserOrdersResponse;
import common_lib.grpc.OrderInfo;
import common_lib.grpc.OrderServiceGrpc;
import common_lib.models.Order;
import common_lib.models.OrderStatus;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.grpc.Status;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class OrderServiceClientTest {
    @Mock
    OrderServiceGrpc.OrderServiceBlockingStub orderServiceStub;

    @Mock
    ManagedChannel channel;

    @InjectMocks
    OrderServiceClient orderServiceClient = new OrderServiceClient(orderServiceStub);

    final Order order = new Order(1, 1, 100, "Test order", OrderStatus.NEW, Instant.now());
    final OrderInfo orderProto = OrderGrpcConvert.fromOrder(order);

    @Test
    void testCreateOrder() {
        CreateOrderRequest request = CreateOrderRequest.newBuilder().setUserId(1).setAmount(100)
                .setDescription("Test order").build();
        CreateOrderResponse response = CreateOrderResponse.newBuilder().setOrderId(1).build();
        doReturn(response).when(orderServiceStub).createOrder(request);

        int orderId = orderServiceClient.createOrder(1, 100, "Test order");

        assertEquals(1, orderId);
    }

    @Test
    void testGetUserOrders() {
        GetUserOrdersRequest request = GetUserOrdersRequest.newBuilder().setUserId(1).build();
        GetUserOrdersResponse response = GetUserOrdersResponse.newBuilder().addOrders(orderProto).build();
        doReturn(response).when(orderServiceStub).getUserOrders(request);

        List<Order> orders = orderServiceClient.getUserOrders(1);

        assertEquals(1, orders.size());
        assertEquals(1, orders.get(0).id());
    }

    @Test
    void testGetOrder() {
        GetOrderInfoRequest request = GetOrderInfoRequest.newBuilder().setOrderId(1).build();
        doReturn(orderProto).when(orderServiceStub).getOrderInfo(request);

        Optional<Order> o = orderServiceClient.getOrder(1);

        assertTrue(o.isPresent());
        assertEquals(1, o.get().id());
    }

    @Test
    void testGetOrderNotFound() {
        GetOrderInfoRequest request = GetOrderInfoRequest.newBuilder().setOrderId(1).build();
        doThrow(new StatusRuntimeException(Status.NOT_FOUND)).when(orderServiceStub).getOrderInfo(request);

        Optional<Order> o = orderServiceClient.getOrder(1);

        assertTrue(o.isEmpty());
    }
}
