package project.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.commands.account_operations.GetAccountOperations;
import project.factories.bank_account.BankAccountParams;
import project.factories.operation.OperationParams;
import project.storages.HSEBank;

@SpringBootTest
class GetAccountOperationsTest {
    @Autowired
    GetAccountOperations command;
    @Autowired
    HSEBank bank;

    @Test
    @DisplayName("GetBankAccountOperations.execute")
    void testExecute() throws CommandError {
        var account = bank.getAccountStorage()
                .createAccount(BankAccountParams.empty());
        var op1 = bank.getOperationStorage().createOperation(
                OperationParams.builder().account(account).build());
        var op2 = bank.getOperationStorage().createOperation(
                OperationParams.builder().account(account).build());

        var res = command.execute(account.getId());
        assertEquals(2, res.size());
        assertTrue(res.contains(op1));
        assertTrue(res.contains(op2));
    }

    @Test
    @DisplayName("GetBankAccountOperations.execute (account not found)")
    void testExecuteAccountNotFound() {
        assertThrows(CommandError.class, () -> command.execute(999));
    }
}
