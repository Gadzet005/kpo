package project.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.commands.export_data.ExportDataCommand;
import project.commands.export_data.ExportDataParams;
import project.consts.ImportExportTarget;
import project.consts.ImportExportType;
import project.consts.OperationType;
import project.domains.BankAccount;
import project.domains.Category;
import project.domains.Operation;
import project.storages.HSEBank;

@SpringBootTest
class ExportDataTest {
    @Autowired
    ExportDataCommand command;
    @Autowired
    HSEBank bank;

    @BeforeEach
    void setUp() {
        bank.getAccountStorage().clear();
        bank.getCategoryStorage().clear();
        bank.getOperationStorage().clear();
    }

    Date getDate(int year, int month, int day) {
        return Date.from(LocalDate.of(year, month, day)
                .atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    BankAccount getAccount(int id) {
        return new BankAccount(id, "Test Account", 0);
    }

    Category getCategory(int id) {
        return new Category(id, "Test Category", OperationType.EXPENSE);
    }

    @Test
    @DisplayName("Test export bank accounts")
    void testExportBankAccounts() throws CommandError {
        var expected = """
                id,name,balance
                1,John Doe,1500.75
                2,Jane Smith,2500.5""";

        bank.getAccountStorage()
                .addAccount(new BankAccount(1, "John Doe", 1500.75));
        bank.getAccountStorage()
                .addAccount(new BankAccount(2, "Jane Smith", 2500.5));

        var actual = command.execute(new ExportDataParams(
                ImportExportTarget.BANK_ACCOUNTS, ImportExportType.CSV));

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test export categories")
    void testExportCategories() throws CommandError {
        var expected = """
                id,name,type
                1,Groceries,EXPENSE
                2,Salary,INCOME""";

        bank.getCategoryStorage().addCategory(
                new Category(1, "Groceries", OperationType.EXPENSE));
        bank.getCategoryStorage()
                .addCategory(new Category(2, "Salary", OperationType.INCOME));

        var actual = command.execute(new ExportDataParams(
                ImportExportTarget.CATEGORIES, ImportExportType.CSV));

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test export operations")
    void testExportOperations() throws CommandError {
        var expected = """
                id,type,amount,date,description,categoryId,accountId
                1,EXPENSE,50.0,01-01-2023,"Grocery shopping",1,1
                2,INCOME,1200.0,01-01-2023,"Monthly salary",2,2""";

        var account1 = getAccount(1);
        var account2 = getAccount(2);
        var category1 = getCategory(1);
        var category2 = getCategory(2);
        var date = getDate(2023, 1, 1);

        bank.getOperationStorage()
                .addOperation(new Operation(1, OperationType.EXPENSE, 50.00,
                        date, "\"Grocery shopping\"", account1, category1));
        bank.getOperationStorage()
                .addOperation(new Operation(2, OperationType.INCOME, 1200.00,
                        date, "\"Monthly salary\"", account2, category2));

        var actual = command.execute(new ExportDataParams(
                ImportExportTarget.OPERATIONS, ImportExportType.CSV));

        assertEquals(expected, actual);
    }
}
