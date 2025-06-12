package api_gateway.controllers;

import java.time.Instant;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;

import api_gateway.clients.OrderServiceClient;
import common_lib.models.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/orders")
@Slf4j
@Tag(name = "Order Controller", description = "API for managing orders")
public class OrderContoller {
    private final OrderServiceClient client;

    public OrderContoller(OrderServiceClient client) {
        this.client = client;
    }

    @PostMapping("/create")
    @Operation(summary = "Create new order", description = "Creates a new order for specified user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order created successfully", content = @Content(schema = @Schema(implementation = CreateOrderResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error") })
    public ResponseEntity<CreateOrderResponse> createOrder(
            @RequestBody(description = "Order creation request", required = true, content = @Content(schema = @Schema(implementation = CreateOrderRequest.class))) @org.springframework.web.bind.annotation.RequestBody CreateOrderRequest request) {
        log.debug("creating order: user_id = {}, amount = {}, description = {}", request.userId(), request.amount(),
                request.description());

        try {
            var orderId = client.createOrder(request.userId(), request.amount(), request.description());
            return ResponseEntity.ok().body(new CreateOrderResponse(orderId));
        } catch (Exception e) {
            log.error("failed to create order", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/user-orders/{userId}")
    @Operation(summary = "Get user orders", description = "Retrieves all orders for specified user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders retrieved successfully", content = @Content(array = @ArraySchema(schema = @Schema(implementation = OrderSchema.class)))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error") })
    public ResponseEntity<List<Order>> getUserOrders(
            @Parameter(description = "ID of the user", required = true, example = "123") @PathVariable int userId) {
        log.debug("getting user orders: user_id = {}", userId);

        try {
            var orders = client.getUserOrders(userId);
            return ResponseEntity.ok().body(orders);
        } catch (Exception e) {
            log.error("failed to get user orders", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{orderId}")
    @Operation(summary = "Get order by ID", description = "Retrieves order details by order ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order found", content = @Content(schema = @Schema(implementation = OrderSchema.class))),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error") })
    public ResponseEntity<Order> getOrder(
            @Parameter(description = "ID of the order", required = true, example = "456") @PathVariable int orderId) {
        log.debug("getting order: order_id = {}", orderId);

        try {
            var order = client.getOrder(orderId);
            return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error("failed to get order", e);
            return ResponseEntity.internalServerError().build();
        }
    }
}

@Schema(description = "Request to create new order")
record CreateOrderRequest(
        @JsonProperty("user_id") @Schema(description = "ID of the user placing the order", example = "123") int userId,

        @Schema(description = "Order amount in cents", example = "1000") int amount,

        @Schema(description = "Order description", example = "5 x Product XYZ") String description) {}

@Schema(description = "Response after order creation")
record CreateOrderResponse(
        @JsonProperty("order_id") @Schema(description = "ID of the created order", example = "789") int orderId) {}

@Schema(description = "Order information")
record OrderSchema(@Schema(description = "Unique identifier of the order", example = "789") int id,

        @JsonProperty("user_id") @Schema(description = "ID of the user who placed the order", example = "123") int userId,

        @Schema(description = "Order amount in cents", example = "1000") int amount,

        @Schema(description = "Description of the order", example = "2 x Premium Widget") String description,

        @Schema(description = "Current status of the order", example = "CREATED", implementation = OrderStatusSchema.class) OrderStatusSchema status,

        @JsonProperty("created_at") @Schema(description = "Timestamp when order was created", example = "2023-05-15T10:30:00Z", type = "string", format = "date-time") Instant createdAt) {}

@Schema(description = "Order status values")
enum OrderStatusSchema {
    @Schema(description = "Order has been created")
    NEW,

    @Schema(description = "Order has been completed")
    FINISHED,

    @Schema(description = "Order has been cancelled")
    CANCELLED
}