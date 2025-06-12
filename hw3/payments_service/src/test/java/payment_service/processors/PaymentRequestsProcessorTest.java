package payment_service.processors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import payments_service.entities.PaymentRequest;
import payments_service.processors.PaymentRequestsProcessor;
import payments_service.services.PaymentsService;
import payments_service.storages.PaymentRequestStorage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.Instant;

@ExtendWith(MockitoExtension.class)
class PaymentRequestsProcessorTest {
    @Mock
    private PaymentsService paymentsService;

    @Mock
    private PaymentRequestStorage paymentRequests;

    @InjectMocks
    private PaymentRequestsProcessor paymentRequestsProcessor;

    private PaymentRequest paymentRequest;

    @BeforeEach
    void setup() {
        paymentRequest = PaymentRequest.builder().orderId(1).userId(1).amount(100).build();
    }

    @Test
    void testProcessOne() {
        boolean result = paymentRequestsProcessor.processOne(paymentRequest);

        assertEquals(true, result);
        assertEquals(true, paymentRequest.isProcessed());
        verify(paymentRequests, times(1)).save(any(PaymentRequest.class));
        verify(paymentsService, times(1)).processOrderPayment(any(common_lib.models.PaymentRequest.class));
    }

    @Test
    void testProcessOneWithException() {
        doThrow(RuntimeException.class).when(paymentsService)
                .processOrderPayment(any(common_lib.models.PaymentRequest.class));

        boolean result = paymentRequestsProcessor.processOne(paymentRequest);
        assertEquals(false, result);
    }

    @Test
    void testProcessMany() {
        var paymentRequest1 = PaymentRequest.builder().orderId(1).userId(1).amount(100).build();
        var paymentRequest2 = PaymentRequest.builder().orderId(3).userId(2).amount(100).build();

        when(paymentRequests.findAllByProcessed(false)).thenReturn(java.util.List.of(paymentRequest1, paymentRequest2));

        paymentRequestsProcessor.process();

        verify(paymentRequests, times(2)).save(any(PaymentRequest.class));
        verify(paymentsService, times(2)).processOrderPayment(any(common_lib.models.PaymentRequest.class));
    }

    @Test
    void testCleanup() {
        paymentRequestsProcessor.cleanup();
        verify(paymentRequests, times(1)).cleanup(any(Instant.class));
    }
}