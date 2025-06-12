package api_gateway.clients;

import common_lib.errors.ErrorCodes;
import common_lib.errors.ServiceError;
import common_lib.grpc.CreateAccountRequest;
import common_lib.grpc.CreateAccountResponse;
import common_lib.grpc.DepositRequest;
import common_lib.grpc.GetAccountBalanceRequest;
import common_lib.grpc.PaymentsServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

import org.springframework.stereotype.Service;

@Service
public class PaymentsServiceClient {
    private PaymentsServiceGrpc.PaymentsServiceBlockingStub paymentsService;

    public PaymentsServiceClient(String host, int port) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build();
        this.paymentsService = PaymentsServiceGrpc.newBlockingStub(channel);
    }

    public PaymentsServiceClient(PaymentsServiceGrpc.PaymentsServiceBlockingStub paymentsService) {
        this.paymentsService = paymentsService;
    }

    public boolean createAccount(int userId) {
        var request = CreateAccountRequest.newBuilder().setUserId(userId).build();
        CreateAccountResponse response = paymentsService.createAccount(request);
        return response.getCreated();
    }

    public int deposit(int userId, int amount) throws ServiceError {
        if (amount <= 0) {
            throw new ServiceError(ErrorCodes.INVALID_ARGUMENTS);
        }

        var request = DepositRequest.newBuilder().setUserId(userId).setAmount(amount).build();

        try {
            var response = paymentsService.deposit(request);
            return response.getBalance();
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                throw new ServiceError(ErrorCodes.NOT_EXISTS);
            }
            throw e;
        }
    }

    public int getBalance(int userId) throws ServiceError {
        var request = GetAccountBalanceRequest.newBuilder().setUserId(userId).build();

        try {
            var response = paymentsService.getAccountBalance(request);
            return response.getBalance();
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                throw new ServiceError(ErrorCodes.NOT_EXISTS);
            }
            throw e;
        }
    }
}
