package payment_service.controllers;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.grpc.StatusRuntimeException;
import io.grpc.stub.StreamObserver;

import common_lib.errors.ErrorCodes;
import common_lib.errors.ServiceError;
import common_lib.grpc.CreateAccountRequest;
import common_lib.grpc.CreateAccountResponse;
import common_lib.grpc.DepositRequest;
import common_lib.grpc.GetAccountBalanceRequest;
import common_lib.grpc.AccountBalance;
import payments_service.controllers.PaymentsGrpcContoller;
import payments_service.services.AccountService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MockitoExtension.class)
class PaymentsGrpcContollerTest {

    @Mock
    private AccountService accountService;

    @Mock
    private StreamObserver<CreateAccountResponse> createAccountResponseObserver;

    @Mock
    private StreamObserver<AccountBalance> accountBalanceResponseObserver;

    @InjectMocks
    private PaymentsGrpcContoller paymentsGrpcContoller;

    @Test
    void testCreateAccountSuccess() {
        CreateAccountRequest request = CreateAccountRequest.newBuilder().setUserId(1).build();
        when(accountService.createAccount(request.getUserId())).thenReturn(true);

        paymentsGrpcContoller.createAccount(request, createAccountResponseObserver);

        verify(createAccountResponseObserver, times(1))
                .onNext(CreateAccountResponse.newBuilder().setCreated(true).build());
        verify(createAccountResponseObserver, times(1)).onCompleted();
    }

    @Test
    void testCreateAccountFailure() {
        CreateAccountRequest request = CreateAccountRequest.newBuilder().setUserId(1).build();
        when(accountService.createAccount(request.getUserId())).thenReturn(false);

        paymentsGrpcContoller.createAccount(request, createAccountResponseObserver);

        verify(createAccountResponseObserver, times(1))
                .onNext(CreateAccountResponse.newBuilder().setCreated(false).build());
        verify(createAccountResponseObserver, times(1)).onCompleted();
    }

    @Test
    void testDepositSuccess() throws ServiceError {
        DepositRequest request = DepositRequest.newBuilder().setUserId(1).setAmount(100).build();
        when(accountService.deposit(request.getUserId(), request.getAmount())).thenReturn(100);

        paymentsGrpcContoller.deposit(request, accountBalanceResponseObserver);

        verify(accountBalanceResponseObserver, times(1)).onNext(AccountBalance.newBuilder().setBalance(100).build());
        verify(accountBalanceResponseObserver, times(1)).onCompleted();
    }

    @Test
    void testDepositFailureAccountNotExists() throws ServiceError {
        DepositRequest request = DepositRequest.newBuilder().setUserId(1).setAmount(100).build();
        when(accountService.deposit(request.getUserId(), request.getAmount()))
                .thenThrow(new ServiceError(ErrorCodes.NOT_EXISTS));

        paymentsGrpcContoller.deposit(request, accountBalanceResponseObserver);

        verify(accountBalanceResponseObserver, times(1)).onError(any(StatusRuntimeException.class));
    }

    @Test
    void testGetAccountBalanceSuccess() throws ServiceError {
        GetAccountBalanceRequest request = GetAccountBalanceRequest.newBuilder().setUserId(1).build();
        when(accountService.getBalance(request.getUserId())).thenReturn(100);

        paymentsGrpcContoller.getAccountBalance(request, accountBalanceResponseObserver);

        verify(accountBalanceResponseObserver, times(1)).onNext(AccountBalance.newBuilder().setBalance(100).build());
        verify(accountBalanceResponseObserver, times(1)).onCompleted();
    }

    @Test
    void testGetAccountBalanceFailureAccountNotExists() throws ServiceError {
        GetAccountBalanceRequest request = GetAccountBalanceRequest.newBuilder().setUserId(1).build();
        when(accountService.getBalance(request.getUserId())).thenThrow(new ServiceError(ErrorCodes.NOT_EXISTS));

        paymentsGrpcContoller.getAccountBalance(request, accountBalanceResponseObserver);

        verify(accountBalanceResponseObserver, times(1)).onError(any(StatusRuntimeException.class));
    }
}