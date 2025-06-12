package payment_service.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import common_lib.errors.ServiceError;
import payments_service.entities.Account;
import payments_service.services.AccountService;
import payments_service.storages.AccountStorage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    private AccountStorage accountStorage;

    @InjectMocks
    private AccountService accountService;

    private Account account;

    @BeforeEach
    void setup() {
        account = Account.builder().userId(1).amount(100).build();
    }

    @Test
    void testCreateAccount() {
        when(accountStorage.existsById(anyInt())).thenReturn(false);
        boolean result = accountService.createAccount(1);
        assertEquals(true, result);
        verify(accountStorage, times(1)).save(any(Account.class));
    }

    @Test
    void testCreateAccountAlreadyExists() {
        when(accountStorage.existsById(anyInt())).thenReturn(true);
        boolean result = accountService.createAccount(1);
        assertEquals(false, result);
        verify(accountStorage, never()).save(any(Account.class));
    }

    @Test
    void testDeposit() throws ServiceError {
        when(accountStorage.findById(anyInt())).thenReturn(java.util.Optional.of(account));
        int result = accountService.deposit(1, 50);
        assertEquals(150, result);
        verify(accountStorage, times(1)).save(any(Account.class));
    }

    @Test
    void testDepositNotEnoughMoney() {
        when(accountStorage.findById(anyInt())).thenReturn(java.util.Optional.of(account));
        assertThrows(ServiceError.class, () -> accountService.deposit(1, -150));
    }

    @Test
    void testDepositAccountNotFound() {
        when(accountStorage.findById(anyInt())).thenReturn(java.util.Optional.empty());
        assertThrows(ServiceError.class, () -> accountService.deposit(1, 50));
    }

    @Test
    void testGetBalance() throws ServiceError {
        when(accountStorage.findById(anyInt())).thenReturn(java.util.Optional.of(account));
        int result = accountService.getBalance(1);
        assertEquals(100, result);
    }

    @Test
    void testGetBalanceAccountNotFound() {
        when(accountStorage.findById(anyInt())).thenReturn(java.util.Optional.empty());
        assertThrows(ServiceError.class, () -> accountService.getBalance(1));
    }
}