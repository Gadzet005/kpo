package payment_service.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import common_lib.errors.ServiceError;
import common_lib.models.PaymentRequest;
import payments_service.entities.PaymentResult;
import payments_service.services.AccountService;
import payments_service.services.PaymentsService;
import payments_service.storages.PaymentResultStorage;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentsServiceTest {

    @Mock
    private AccountService accountService;

    @Mock
    private PaymentResultStorage paymentResults;

    @InjectMocks
    private PaymentsService paymentsService;

    private PaymentRequest paymentRequest;

    @BeforeEach
    void setup() {
        paymentRequest = new PaymentRequest(1, 1, 100);
    }

    @Test
    void testProcessOrderPayment() throws ServiceError {
        when(accountService.deposit(anyInt(), anyInt())).thenReturn(200);
        paymentsService.processOrderPayment(paymentRequest);
        verify(paymentResults, times(1)).save(any(PaymentResult.class));
    }

    @Test
    void testProcessOrderPaymentNotEnoughMoney() throws ServiceError {
        when(accountService.deposit(anyInt(), anyInt())).thenThrow(ServiceError.class);
        paymentsService.processOrderPayment(paymentRequest);
        verify(paymentResults, times(1)).save(any(PaymentResult.class));
    }

    @Test
    void testProcessOrderPaymentAccountServiceException() throws ServiceError {
        when(accountService.deposit(anyInt(), anyInt())).thenThrow(RuntimeException.class);
        paymentsService.processOrderPayment(paymentRequest);
        verify(paymentResults, times(1)).save(any(PaymentResult.class));
    }
}