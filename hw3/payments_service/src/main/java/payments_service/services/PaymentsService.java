package payments_service.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common_lib.models.PaymentRequest;
import jakarta.transaction.Transactional;
import payments_service.entities.PaymentResult;
import payments_service.storages.PaymentResultStorage;

@Service
public class PaymentsService {
    private AccountService accountService;
    private PaymentResultStorage paymentResults;

    @Autowired
    public PaymentsService(AccountService accountService, PaymentResultStorage paymentResults) {
        this.accountService = accountService;
        this.paymentResults = paymentResults;
    }

    @Transactional
    public void processOrderPayment(PaymentRequest request) {
        var builder = PaymentResult.builder().orderId(request.orderId());
        PaymentResult result;

        try {
            accountService.deposit(request.userId(), -request.amount());
            result = builder.success(true).build();
        } catch (Exception e) {
            result = builder.success(false).build();
        }

        paymentResults.save(result);
    }
}
