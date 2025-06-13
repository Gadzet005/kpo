package api_gateway.controllers;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import api_gateway.clients.OrderServiceClient;
import common_lib.models.Order;
import common_lib.models.OrderStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class OrderContollerTest {

    @Mock
    OrderServiceClient orderServiceClient;

    @InjectMocks
    OrderContoller orderContoller;

    MockMvc mockMvc;

    static ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    static void setupClass() {
        mapper.registerModule(new JavaTimeModule());
    }

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(orderContoller).build();
    }

    @Test
    void testCreateOrder() throws Exception {
        var request = new CreateOrderRequest(1, 100, "Test order");
        var response = new CreateOrderResponse(1);

        doReturn(1).when(orderServiceClient).createOrder(anyInt(), anyInt(), anyString());

        mockMvc.perform(
                post("/orders/create").contentType("application/json").content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andExpect(result -> assertEquals(mapper.writeValueAsString(response),
                        result.getResponse().getContentAsString()));
    }

    @Test
    void testCreateOrderInternal() throws Exception {
        var request = new CreateOrderRequest(1, 100, "Test order");

        doThrow(new RuntimeException()).when(orderServiceClient).createOrder(anyInt(), anyInt(), anyString());

        mockMvc.perform(
                post("/orders/create").contentType("application/json").content(mapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testGetUserOrders() throws Exception {
        var orders = List.of(new Order(1, 1, 100, "Test order", OrderStatus.NEW, Instant.now()));
        var ordersJson = mapper.writeValueAsString(orders.stream().map(OrderSchema::new).toList());
        doReturn(orders).when(orderServiceClient).getUserOrders(anyInt());

        mockMvc.perform(get("/orders/user-orders/1")).andExpect(status().isOk())
                .andExpect(result -> assertEquals(ordersJson, result.getResponse().getContentAsString()));
    }

    @Test
    void testGetUserOrdersInternal() throws Exception {
        doThrow(new RuntimeException()).when(orderServiceClient).getUserOrders(anyInt());

        mockMvc.perform(get("/orders/user-orders/1")).andExpect(status().isInternalServerError());
    }

    @Test
    void testGetOrder() throws Exception {
        var order = new Order(1, 1, 100, "Test order", OrderStatus.NEW, Instant.now());
        var orderJson = mapper.writeValueAsString(new OrderSchema(order));
        doReturn(Optional.of(order)).when(orderServiceClient).getOrder(anyInt());

        mockMvc.perform(get("/orders/1")).andExpect(status().isOk())
                .andExpect(result -> assertEquals(orderJson, result.getResponse().getContentAsString()));
    }

    @Test
    void testGetOrderNotFound() throws Exception {
        doReturn(Optional.empty()).when(orderServiceClient).getOrder(anyInt());

        mockMvc.perform(get("/orders/1")).andExpect(status().isNotFound());
    }

    @Test
    void testGetOrderInternalServerError() throws Exception {
        doThrow(new RuntimeException()).when(orderServiceClient).getOrder(anyInt());

        mockMvc.perform(get("/orders/1")).andExpect(status().isInternalServerError());
    }
}
