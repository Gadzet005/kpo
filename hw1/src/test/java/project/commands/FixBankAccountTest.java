package project.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.commands.fix_bank_account.FixBankAccountCommand;
import project.commands.fix_bank_account.FixBankAccountResult;
import project.consts.OperationType;
import project.domains.BankAccount;
import project.domains.Operation;
import project.factories.bank_account.BankAccountParams;
import project.factories.operation.OperationParams;
import project.storages.HSEBank;

@SpringBootTest
class FixBankAccountTest {
    @Autowired
    HSEBank bank;
    @Autowired
    FixBankAccountCommand command;

    @BeforeEach
    void setUp() {
        bank.getAccountStorage().clear();
        bank.getOperationStorage().clear();
    }

    BankAccount createAccount(double balance) {
        var account = bank.getAccountStorage()
                .createAccount(new BankAccountParams(null));
        account.setBalance(balance);
        return account;
    }

    Operation createOperation(int accountId, OperationType type,
            double amount) {
        var account = bank.getAccountStorage().getAccount(accountId);
        return bank.getOperationStorage().createOperation(
                new OperationParams(type, account, amount, null, null, null));
    }

    @Test
    @DisplayName("FixBankAccount.execute")
    void testExecute() throws CommandError {
        var account = createAccount(100.0);
        createOperation(account.getId(), OperationType.INCOME, 50.0);
        createOperation(account.getId(), OperationType.INCOME, 25.0);

        FixBankAccountResult result = command.execute(account.getId());

        assertEquals(100.0, result.oldBalance());
        assertEquals(75.0, result.newBalance());
    }

    @Test
    @DisplayName("FixBankAccount.execute with non-existent account")
    void testExecuteNonExistentAccount() {
        assertThrows(CommandError.class, () -> command.execute(999));
    }
}
