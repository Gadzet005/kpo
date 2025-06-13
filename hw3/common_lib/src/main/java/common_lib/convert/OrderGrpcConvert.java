package common_lib.convert;

import java.time.Instant;

import com.google.protobuf.Timestamp;

import common_lib.grpc.OrderInfo;
import common_lib.models.Order;
import common_lib.models.OrderStatus;

public class OrderGrpcConvert {
    private OrderGrpcConvert() {
    }

    public static OrderStatus toOrderStatus(common_lib.grpc.OrderStatus status) {
        return OrderStatus.valueOf(status.name());
    }

    public static common_lib.grpc.OrderStatus fromOrderStatus(OrderStatus status) {
        return common_lib.grpc.OrderStatus.valueOf(status.name());
    }

    public static Order toOrder(OrderInfo info) {
        var status = toOrderStatus(info.getStatus());
        var createdAt = Instant.ofEpochSecond(info.getCreatedAt().getSeconds(), info.getCreatedAt().getNanos());

        return new Order(info.getOrderId(), info.getUserId(), info.getAmount(), info.getDescription(), status,
                createdAt);
    }

    public static OrderInfo fromOrder(Order order) {
        var createdAt = Timestamp.newBuilder().setSeconds(order.createdAt().getEpochSecond())
                .setNanos(order.createdAt().getNano()).build();
        return OrderInfo.newBuilder().setOrderId(order.id()).setUserId(order.userId()).setAmount(order.amount())
                .setDescription(order.description()).setStatus(fromOrderStatus(order.status())).setCreatedAt(createdAt)
                .build();
    }
}
