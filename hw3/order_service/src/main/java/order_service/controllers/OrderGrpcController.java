package order_service.controllers;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import order_service.services.OrderService;

import org.springframework.beans.factory.annotation.Autowired;

import common_lib.convert.OrderGrpcConvert;
import common_lib.grpc.CreateOrderRequest;
import common_lib.grpc.CreateOrderResponse;
import common_lib.grpc.GetOrderInfoRequest;
import common_lib.grpc.GetUserOrdersRequest;
import common_lib.grpc.GetUserOrdersResponse;
import common_lib.grpc.OrderInfo;
import common_lib.grpc.OrderServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

@GrpcService
@Slf4j
public class OrderGrpcController extends OrderServiceGrpc.OrderServiceImplBase {
    private final OrderService orderService;

    @Autowired
    public OrderGrpcController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void createOrder(CreateOrderRequest request, StreamObserver<CreateOrderResponse> responseObserver) {
        try {
            var order = orderService.createOrder(request.getUserId(), request.getAmount(), request.getDescription());

            log.info("created order: id = {}, userId = {}, amount = {}, description = {}", order.id(), order.userId(),
                    order.amount(), order.description());

            responseObserver.onNext(CreateOrderResponse.newBuilder().setOrderId(order.id()).build());
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("error creating order", e);
            responseObserver.onError(Status.INTERNAL.asRuntimeException());
        }
    }

    @Override
    public void getUserOrders(GetUserOrdersRequest request, StreamObserver<GetUserOrdersResponse> responseObserver) {
        try {
            var orders = orderService.getUserOrders(request.getUserId());
            var orderInfos = orders.stream().map(OrderGrpcConvert::fromOrder).toList();

            var resp = GetUserOrdersResponse.newBuilder().addAllOrders(orderInfos).build();
            responseObserver.onNext(resp);
            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("error getting user orders", e);
            responseObserver.onError(Status.INTERNAL.asRuntimeException());
        }
    }

    @Override
    public void getOrderInfo(GetOrderInfoRequest request, StreamObserver<OrderInfo> responseObserver) {
        try {
            var order = orderService.getOrder(request.getOrderId());
            if (order.isPresent()) {
                var orderInfo = OrderGrpcConvert.fromOrder(order.get());
                responseObserver.onNext(orderInfo);
            } else {
                responseObserver.onError(Status.NOT_FOUND.asRuntimeException());
            }

            responseObserver.onCompleted();
        } catch (Exception e) {
            log.error("error getting order info", e);
            responseObserver.onError(Status.INTERNAL.asRuntimeException());
        }
    }
}
