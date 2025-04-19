package project.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.commands.import_data.ImportDataCommand;
import project.commands.import_data.ImportDataParams;
import project.consts.ImportExportTarget;
import project.consts.ImportExportType;
import project.storages.HSEBank;

@SpringBootTest
class ImportDataTest {
    @Autowired
    HSEBank bank;
    @Autowired
    ImportDataCommand command;

    @BeforeEach
    void setUp() {
        bank.getAccountStorage().clear();
        bank.getCategoryStorage().clear();
        bank.getOperationStorage().clear();
    }

    @Test
    @DisplayName("Import bank accounts")
    void testImportBankAccounts() throws CommandError {
        var importData = """
                id,name,balance
                1,John Doe,1500.75
                2,Jane Smith,2500.50""";

        Integer res;
        res = command.execute(new ImportDataParams(importData,
                ImportExportTarget.BANK_ACCOUNTS, ImportExportType.CSV));

        assertEquals(2, res);
        assertEquals(2, bank.getAccountStorage().getAccounts().size());
    }

    @Test
    @DisplayName("Import categories")
    void testImportCategories() throws CommandError {
        var importData = """
                id,name,type
                1,Groceries,EXPENSE
                2,Salary,INCOME""";

        Integer res = command.execute(new ImportDataParams(importData,
                ImportExportTarget.CATEGORIES, ImportExportType.CSV));

        assertEquals(2, res);
        assertEquals(2, bank.getCategoryStorage().getCategories().size());
    }

    @Test
    @DisplayName("Import operations")
    void testImportOperations() throws CommandError {
        var importData = """
                id,type,amount,date,description,accountId,categoryId
                1,EXPENSE,50.00,2023-01-01,"Grocery shopping",1,1
                2,INCOME,1200.00,2023-01-02,"Monthly salary",2,2""";

        Integer res = command.execute(new ImportDataParams(importData,
                ImportExportTarget.OPERATIONS, ImportExportType.CSV));

        assertEquals(2, res);
        assertEquals(2, bank.getOperationStorage().getOperations().size());
    }
}
