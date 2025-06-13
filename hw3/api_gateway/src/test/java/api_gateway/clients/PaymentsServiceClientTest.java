package api_gateway.clients;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import common_lib.grpc.AccountBalance;
import common_lib.grpc.CreateAccountRequest;
import common_lib.grpc.CreateAccountResponse;
import common_lib.grpc.DepositRequest;
import common_lib.grpc.GetAccountBalanceRequest;
import common_lib.grpc.PaymentsServiceGrpc;
import common_lib.errors.ServiceError;
import io.grpc.ManagedChannel;
import io.grpc.StatusRuntimeException;
import io.grpc.Status;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
class PaymentsServiceClientTest {
    @Mock
    PaymentsServiceGrpc.PaymentsServiceBlockingStub paymentsServiceStub;

    @Mock
    ManagedChannel channel;

    @InjectMocks
    PaymentsServiceClient paymentsServiceClient = new PaymentsServiceClient(paymentsServiceStub);

    @Test
    void testCreateAccount() {
        CreateAccountRequest request = CreateAccountRequest.newBuilder().setUserId(1).build();
        CreateAccountResponse response = CreateAccountResponse.newBuilder().setCreated(true).build();
        doReturn(response).when(paymentsServiceStub).createAccount(request);

        boolean created = paymentsServiceClient.createAccount(1);

        assertEquals(true, created);
    }

    @Test
    void testDeposit() throws ServiceError {
        DepositRequest request = DepositRequest.newBuilder().setUserId(1).setAmount(100).build();
        var b = AccountBalance.newBuilder().setBalance(200).build();
        doReturn(b).when(paymentsServiceStub).deposit(request);

        int balance = paymentsServiceClient.deposit(1, 100);

        assertEquals(200, balance);
    }

    @Test
    void testGetBalance() throws ServiceError {
        GetAccountBalanceRequest request = GetAccountBalanceRequest.newBuilder().setUserId(1).build();
        var b = AccountBalance.newBuilder().setBalance(200).build();
        doReturn(b).when(paymentsServiceStub).getAccountBalance(request);

        int balance = paymentsServiceClient.getBalance(1);

        assertEquals(200, balance);
    }

    @Test
    void testGetBalanceNotFound() {
        GetAccountBalanceRequest request = GetAccountBalanceRequest.newBuilder().setUserId(1).build();
        doThrow(new StatusRuntimeException(Status.NOT_FOUND)).when(paymentsServiceStub).getAccountBalance(request);

        assertThrows(ServiceError.class, () -> paymentsServiceClient.getBalance(1));
    }

    @Test
    void testDepositNotFound() {
        DepositRequest request = DepositRequest.newBuilder().setUserId(1).setAmount(100).build();
        doThrow(new StatusRuntimeException(Status.NOT_FOUND)).when(paymentsServiceStub).deposit(request);

        assertThrows(ServiceError.class, () -> paymentsServiceClient.deposit(1, 100));
    }

    @Test
    void testDepositNegativeAmount() {
        assertThrows(ServiceError.class, () -> paymentsServiceClient.deposit(1, -100));
    }
}
