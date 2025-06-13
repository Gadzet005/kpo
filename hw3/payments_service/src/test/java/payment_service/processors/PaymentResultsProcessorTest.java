package payment_service.processors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import payments_service.entities.PaymentResult;
import payments_service.kafka.PaymentProducer;
import payments_service.processors.PaymentResultsProcessor;
import payments_service.storages.PaymentResultStorage;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentResultsProcessorTest {
    @Mock
    private PaymentResultStorage paymentResults;

    @Mock
    private PaymentProducer producer;

    @InjectMocks
    private PaymentResultsProcessor paymentResultsProcessor;

    @Test
    void testProcessNoResults() {
        when(paymentResults.findAll()).thenReturn(new ArrayList<>());

        paymentResultsProcessor.process();

        verify(paymentResults).findAll();
        verifyNoMoreInteractions(paymentResults, producer);
    }

    @Test
    void testProcessOne() {
        PaymentResult result = new PaymentResult();
        when(paymentResults.findAll()).thenReturn(List.of(result));

        paymentResultsProcessor.process();

        verify(paymentResults).findAll();
        verify(producer).sendPaymentResult(result.toModel());
        verify(paymentResults).delete(result);
    }

    @Test
    void testProcessOneFailure() {
        PaymentResult result = new PaymentResult();
        when(paymentResults.findAll()).thenReturn(List.of(result));
        doThrow(RuntimeException.class).when(producer).sendPaymentResult(any());

        paymentResultsProcessor.process();

        verify(paymentResults).findAll();
        verify(producer).sendPaymentResult(result.toModel());
        verify(paymentResults, never()).delete(result);
    }

    @Test
    void testProcessMany() {
        PaymentResult result1 = new PaymentResult();
        PaymentResult result2 = new PaymentResult();
        when(paymentResults.findAll()).thenReturn(List.of(result1, result2));

        paymentResultsProcessor.process();

        verify(paymentResults).findAll();
        verify(producer, times(2)).sendPaymentResult(any(common_lib.models.PaymentResult.class));
        verify(paymentResults, times(2)).delete(any(PaymentResult.class));
    }
}
