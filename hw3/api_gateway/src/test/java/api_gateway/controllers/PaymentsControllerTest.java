package api_gateway.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import api_gateway.clients.PaymentsServiceClient;
import common_lib.errors.ErrorCodes;
import common_lib.errors.ServiceError;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PaymentsControllerTest {

    @Mock
    PaymentsServiceClient paymentsServiceClient;

    @InjectMocks
    PaymentsController paymentsController;

    MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(paymentsController).build();
    }

    @Test
    void testCreateAccount() throws Exception {
        var request = new CreateAccountRequest(1);
        var response = new CreateAccountResponse(true);

        doReturn(true).when(paymentsServiceClient).createAccount(anyInt());

        mockMvc.perform(post("/payments/create-account").contentType("application/json")
                .content(mapper.writeValueAsString(request))).andExpect(status().isOk())
                .andExpect(result -> assertEquals(mapper.writeValueAsString(response),
                        result.getResponse().getContentAsString()));
    }

    @Test
    void testCreateAccountError() throws Exception {
        var request = new CreateAccountRequest(1);

        doThrow(new RuntimeException()).when(paymentsServiceClient).createAccount(anyInt());

        mockMvc.perform(post("/payments/create-account").contentType("application/json")
                .content(mapper.writeValueAsString(request))).andExpect(status().isInternalServerError());
    }

    @Test
    void testDeposit() throws Exception {
        var request = new DepositRequest(1, 100);
        var response = new AccountBalance(200);

        doReturn(200).when(paymentsServiceClient).deposit(anyInt(), anyInt());

        mockMvc.perform(
                post("/payments/deposit").contentType("application/json").content(mapper.writeValueAsString(request)))
                .andExpect(status().isOk()).andExpect(result -> assertEquals(mapper.writeValueAsString(response),
                        result.getResponse().getContentAsString()));
    }

    @Test
    void testDepositError() throws Exception {
        var request = new DepositRequest(1, 100);

        doThrow(new ServiceError("error")).when(paymentsServiceClient).deposit(anyInt(), anyInt());

        mockMvc.perform(
                post("/payments/deposit").contentType("application/json").content(mapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testDepositNegativeAmount() throws Exception {
        var request = new DepositRequest(1, -100);

        doThrow(new ServiceError(ErrorCodes.INVALID_ARGUMENTS)).when(paymentsServiceClient).deposit(anyInt(), anyInt());

        mockMvc.perform(
                post("/payments/deposit").contentType("application/json").content(mapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDepositNotFound() throws Exception {
        var request = new DepositRequest(1, 100);

        doThrow(new ServiceError(ErrorCodes.NOT_EXISTS)).when(paymentsServiceClient).deposit(anyInt(), anyInt());

        mockMvc.perform(
                post("/payments/deposit").contentType("application/json").content(mapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetBalance() throws Exception {
        var response = new AccountBalance(200);
        doReturn(200).when(paymentsServiceClient).getBalance(anyInt());

        mockMvc.perform(get("/payments/balance/1")).andExpect(status().isOk()).andExpect(
                result -> assertEquals(mapper.writeValueAsString(response), result.getResponse().getContentAsString()));
    }

    @Test
    void testGetBalanceError() throws Exception {
        doThrow(new ServiceError("error")).when(paymentsServiceClient).getBalance(anyInt());

        mockMvc.perform(get("/payments/balance/1")).andExpect(status().isInternalServerError());
    }

    @Test
    void testGetBalanceNotFound() throws Exception {
        doThrow(new ServiceError(ErrorCodes.NOT_EXISTS)).when(paymentsServiceClient).getBalance(anyInt());

        mockMvc.perform(get("/payments/balance/1")).andExpect(status().isNotFound());
    }
}