package order_service.processors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import order_service.entities.PaymentRequest;
import order_service.kafka.PaymentProducer;
import order_service.storages.PaymentRequestStorage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentRequestsProcessorTest {
    @Mock
    private PaymentRequestStorage paymentRequests;

    @Mock
    private PaymentProducer producer;

    @InjectMocks
    private PaymentRequestsProcessor paymentRequestsProcessor;

    @Test
    void testProcess() {
        var paymentRequestsList = List.of(PaymentRequest.builder().orderId(1).userId(1).amount(100).build(),
                PaymentRequest.builder().orderId(2).userId(2).amount(200).build());

        when(paymentRequests.findAll()).thenReturn(paymentRequestsList);

        paymentRequestsProcessor.process();

        verify(paymentRequests, times(2)).delete(any(PaymentRequest.class));
        verify(producer, times(2)).sendPaymentRequest(any(common_lib.models.PaymentRequest.class));
    }

    @Test
    void testProcessNothing() {
        when(paymentRequests.findAll()).thenReturn(List.of());

        paymentRequestsProcessor.process();

        verify(paymentRequests, never()).delete(any(PaymentRequest.class));
        verify(producer, never()).sendPaymentRequest(any(common_lib.models.PaymentRequest.class));
    }

    @Test
    void testProcessOne() {
        var paymentRequest = PaymentRequest.builder().orderId(1).userId(1).amount(100).build();

        var result = paymentRequestsProcessor.processOne(paymentRequest);

        assertTrue(result);
        verify(producer).sendPaymentRequest(paymentRequest.toModel());
        verify(paymentRequests).delete(paymentRequest);
    }

    @Test
    void testProcessOneWithError() {
        var paymentRequest = PaymentRequest.builder().orderId(1).userId(1).amount(100).build();

        doThrow(new RuntimeException()).when(producer).sendPaymentRequest(paymentRequest.toModel());

        var result = paymentRequestsProcessor.processOne(paymentRequest);

        assertFalse(result);
        verify(paymentRequests, never()).delete(paymentRequest);
    }
}