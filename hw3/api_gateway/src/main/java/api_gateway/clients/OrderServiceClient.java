package api_gateway.clients;

import common_lib.convert.OrderGrpcConvert;
import common_lib.grpc.CreateOrderRequest;
import common_lib.grpc.CreateOrderResponse;
import common_lib.grpc.GetOrderInfoRequest;
import common_lib.grpc.GetUserOrdersRequest;
import common_lib.grpc.GetUserOrdersResponse;
import common_lib.grpc.OrderServiceGrpc;
import common_lib.models.Order;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import io.grpc.Status;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
public class OrderServiceClient {
    private OrderServiceGrpc.OrderServiceBlockingStub orderService;

    public OrderServiceClient(String host, int port) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        this.orderService = OrderServiceGrpc.newBlockingStub(channel);
    }

    public OrderServiceClient(OrderServiceGrpc.OrderServiceBlockingStub orderService) {
        this.orderService = orderService;
    }

    public int createOrder(int userId, int amount, String description) {
        var request = CreateOrderRequest.newBuilder().setUserId(userId).setAmount(amount).setDescription(description)
                .build();
        CreateOrderResponse response = orderService.createOrder(request);
        return response.getOrderId();
    }

    public List<Order> getUserOrders(int userId) {
        var request = GetUserOrdersRequest.newBuilder().setUserId(userId).build();
        GetUserOrdersResponse response = orderService.getUserOrders(request);
        return response.getOrdersList().stream().map(OrderGrpcConvert::toOrder).toList();
    }

    public Optional<Order> getOrder(int orderId) {
        var request = GetOrderInfoRequest.newBuilder().setOrderId(orderId).build();

        try {
            var response = orderService.getOrderInfo(request);
            return Optional.of(OrderGrpcConvert.toOrder(response));
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                return Optional.empty();
            }
            throw e;
        }
    }
}
