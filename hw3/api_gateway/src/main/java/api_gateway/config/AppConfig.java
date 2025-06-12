package api_gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import api_gateway.clients.OrderServiceClient;
import api_gateway.clients.PaymentsServiceClient;

@Configuration
public class AppConfig {
    @Value("${grpc.order-service.host}")
    private String orderServiceHost;

    @Value("${grpc.order-service.port}")
    private int orderServicePort;

    @Value("${grpc.payments-service.host}")
    private String paymentsServiceHost;

    @Value("${grpc.payments-service.port}")
    private int paymentsServicePort;

    @Bean
    public OrderServiceClient orderServiceClient() {
        return new OrderServiceClient(orderServiceHost, orderServicePort);
    }

    @Bean
    public PaymentsServiceClient paymentsServiceClient() {
        return new PaymentsServiceClient(paymentsServiceHost, paymentsServicePort);
    }
}
