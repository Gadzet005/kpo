package project.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.commands.account_stats.GetAccountStats;
import project.commands.account_stats.GetAccountStatsParams;
import project.consts.OperationType;
import project.factories.bank_account.BankAccountParams;
import project.factories.category.CategoryParams;
import project.factories.operation.OperationParams;
import project.storages.HSEBank;

@SpringBootTest
class GetAccountStatsTest {
    @Autowired
    GetAccountStats command;
    @Autowired
    HSEBank bank;

    @Test
    @DisplayName("GetBankAccountStat.execute")
    void testExecute() throws CommandError {
        var account = bank.getAccountStorage()
                .createAccount(BankAccountParams.empty());
        var cat1 = bank.getCategoryStorage().createCategory(
                new CategoryParams("cat1", OperationType.INCOME));
        var cat2 = bank.getCategoryStorage().createCategory(
                new CategoryParams("cat2", OperationType.EXPENSE));

        bank.getOperationStorage()
                .createOperation(OperationParams.builder()
                        .type(OperationType.INCOME).account(account)
                        .category(cat1).amount(200.0).build());
        bank.getOperationStorage()
                .createOperation(OperationParams.builder()
                        .type(OperationType.EXPENSE).account(account)
                        .category(cat2).amount(300.0).build());
        bank.getOperationStorage()
                .createOperation(OperationParams.builder()
                        .type(OperationType.INCOME).account(account)
                        .category(cat1).amount(200.0).build());

        var res = command.execute(
                new GetAccountStatsParams(account.getId(), null, null));

        assertEquals(3, res.count());
        assertEquals(1, res.expenseCount());
        assertEquals(2, res.incomeCount());
        assertEquals(300.0, res.expenseSum());
        assertEquals(400.0, res.incomeSum());
        assertEquals(100.0, res.sum());
        assertEquals(700.0, res.sumAbs());
        assertEquals(200.0, res.incomeAvg());
        assertEquals(300.0, res.expenseAvg());
        assertEquals(700.0 / 3, res.avg());
        assertEquals(2, res.categories().size());
    }

    @Test
    @DisplayName("GetBankAccountStat.execute (account not found)")
    void testExecuteAccountNotFound() {
        assertThrows(CommandError.class, () -> command
                .execute(new GetAccountStatsParams(999, null, null)));
    }
}
