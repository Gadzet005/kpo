package project.storages;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.factories.bank_account.BankAccountFactory;
import project.factories.bank_account.BankAccountParams;
import project.utils.IdCounter;

@SpringBootTest
class BankAccountStorageTest {
    @Autowired
    BankAccountStorage accountStorage;

    @BeforeEach
    void setUp() {
        accountStorage.clear();
    }

    @Test
    @DisplayName("BankAccountStorage.createAccount")
    void createAccount() {
        var params = BankAccountParams.empty();
        var res = accountStorage.createAccount(params);
        assertEquals(params.name(), res.getName());
    }

    @Test
    @DisplayName("BankAccountStorage.addAccount")
    void addAccount() {
        var bankAccountFactory = new BankAccountFactory(new IdCounter());
        var account = bankAccountFactory.create(BankAccountParams.empty());

        var res = accountStorage.addAccount(account);
        assertTrue(res);
        assertTrue(accountStorage.hasAccount(account.getId()));
    }

    @Test
    @DisplayName("Test BankAccountStorage.addAccount if it exists")
    void addAccountIfExists() {
        var bankAccountFactory = new BankAccountFactory(new IdCounter());
        var account = bankAccountFactory.create(BankAccountParams.empty());

        accountStorage.addAccount(account);
        var res = accountStorage.addAccount(account);

        assertFalse(res);
        assertTrue(accountStorage.hasAccount(account.getId()));
    }

    @Test
    @DisplayName("BankAccountStorage.getAccount")
    void getAccount() {
        var account = accountStorage.createAccount(BankAccountParams.empty());
        var res = accountStorage.getAccount(account.getId());

        assertSame(account, res);
    }

    @Test
    @DisplayName("BankAccountStorage.getAccount if not found")
    void getAccountNotFound() {
        var res = accountStorage.getAccount(123);
        assertNull(res);
    }

    @Test
    @DisplayName("BankAccountStorage.getAccounts")
    void getAllAccounts() {
        var account1 = accountStorage.createAccount(BankAccountParams.empty());
        var account2 = accountStorage.createAccount(BankAccountParams.empty());

        var res = accountStorage.getAccounts();
        assertEquals(2, res.size());
        assertTrue(res.contains(account1));
        assertTrue(res.contains(account2));
    }

    @Test
    @DisplayName("BankAccountStorage.hasAccount")
    void hasAccount() {
        var account = accountStorage.createAccount(BankAccountParams.empty());
        assertTrue(accountStorage.hasAccount(account.getId()));
    }

    @Test
    @DisplayName("BankAccountStorage.removeAccount")
    void removeAccount() {
        var account = accountStorage.createAccount(BankAccountParams.empty());
        accountStorage.removeAccount(account.getId());
        assertFalse(accountStorage.hasAccount(account.getId()));
    }

    @Test
    @DisplayName("BankAccountStorage.clear")
    void clearAccounts() {
        accountStorage.createAccount(BankAccountParams.empty());
        accountStorage.createAccount(BankAccountParams.empty());

        accountStorage.clear();

        var res = accountStorage.getAccounts();
        assertEquals(0, res.size());
    }
}
