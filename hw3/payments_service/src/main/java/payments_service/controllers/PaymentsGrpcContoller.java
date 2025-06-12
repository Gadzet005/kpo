package payments_service.controllers;

import net.devh.boot.grpc.server.service.GrpcService;
import payments_service.services.AccountService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import common_lib.errors.ErrorCodes;
import common_lib.errors.ServiceError;
import common_lib.grpc.AccountBalance;
import common_lib.grpc.CreateAccountRequest;
import common_lib.grpc.CreateAccountResponse;
import common_lib.grpc.DepositRequest;
import common_lib.grpc.GetAccountBalanceRequest;
import common_lib.grpc.PaymentsServiceGrpc;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

@GrpcService
@Slf4j
public class PaymentsGrpcContoller extends PaymentsServiceGrpc.PaymentsServiceImplBase {
    private AccountService accountService;

    @Autowired
    public PaymentsGrpcContoller(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void createAccount(CreateAccountRequest request, StreamObserver<CreateAccountResponse> responseObserver) {
        var created = accountService.createAccount(request.getUserId());
        responseObserver.onNext(CreateAccountResponse.newBuilder().setCreated(created).build());
        responseObserver.onCompleted();
    }

    @Override
    public void deposit(DepositRequest request, StreamObserver<AccountBalance> responseObserver) {
        try {
            var balance = accountService.deposit(request.getUserId(), request.getAmount());
            responseObserver.onNext(AccountBalance.newBuilder().setBalance(balance).build());
            responseObserver.onCompleted();
        } catch (ServiceError e) {
            if (e.getCode().equals(ErrorCodes.NOT_EXISTS)) {
                log.error("failed to deposit: account not exists", e);
                responseObserver.onError(Status.NOT_FOUND.asRuntimeException());
            } else {
                log.error("failed to deposit", e);
                responseObserver.onError(Status.INTERNAL.asRuntimeException());
            }
        } catch (Exception e) {
            log.error("failed to deposit: unknown", e);
            responseObserver.onError(Status.INTERNAL.asRuntimeException());
        }
    }

    @Override
    public void getAccountBalance(GetAccountBalanceRequest request, StreamObserver<AccountBalance> responseObserver) {
        try {
            var balance = accountService.getBalance(request.getUserId());
            responseObserver.onNext(AccountBalance.newBuilder().setBalance(balance).build());
            responseObserver.onCompleted();
        } catch (ServiceError e) {
            if (e.getCode().equals(ErrorCodes.NOT_EXISTS)) {
                log.error("failed to get balance: account not exists");
                responseObserver.onError(Status.NOT_FOUND.asRuntimeException());
            } else {
                log.error("failed to get balance", e);
                responseObserver.onError(Status.INTERNAL.asRuntimeException());
            }
        } catch (Exception e) {
            log.error("failed to get balance: unknown", e);
            responseObserver.onError(Status.INTERNAL.asRuntimeException());
        }
    }
}
