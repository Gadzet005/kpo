package project.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.consts.OperationType;
import project.factories.bank_account.BankAccountParams;
import project.factories.category.CategoryParams;
import project.factories.operation.OperationParams;
import project.serializers.converters.OperationFieldConverter;
import project.serializers.converters.base.FieldList;
import project.serializers.exceptions.ConvertException;
import project.storages.HSEBank;

@SpringBootTest
class OperationConverterTest {
    @Autowired
    OperationFieldConverter converter;
    @Autowired
    HSEBank bank;

    DateFormat dateFormat = new SimpleDateFormat(
            OperationFieldConverter.DATE_FORMAT_STRING);

    @Test
    @DisplayName("test convert")
    void testConvert() {
        var category = bank.getCategoryStorage()
                .createCategory(CategoryParams.empty());
        var account = bank.getAccountStorage()
                .createAccount(BankAccountParams.empty());
        var operation = bank.getOperationStorage()
                .createOperation(OperationParams.builder().account(account)
                        .category(category).build());
        var fields = converter.convert(operation);

        assertEquals(operation.getId(), fields.get("id"));
        assertEquals(operation.getType().name(), fields.get("type"));
        assertEquals(account.getId(), fields.get("accountId"));
        assertEquals(category.getId(), fields.get("categoryId"));
        assertEquals(operation.getAmount(), fields.get("amount"));
        assertEquals(operation.getDescription(), fields.get("description"));
    }

    @Test
    @DisplayName("test convertBack")
    void testConvertBack() throws ConvertException {
        var category = bank.getCategoryStorage()
                .createCategory(CategoryParams.empty());
        var account = bank.getAccountStorage()
                .createAccount(BankAccountParams.empty());

        var fields = new FieldList();
        fields.add("id", 1);
        fields.add("type", OperationType.INCOME.name());
        fields.add("accountId", account.getId());
        fields.add("categoryId", category.getId());
        fields.add("amount", 100.0);
        fields.add("date", "01-01-2025");
        fields.add("description", "Test operation");

        var operation = converter.convertBack(fields);

        assertEquals(fields.get("id"), operation.getId());
        assertEquals(fields.get("type"), operation.getType().name());
        assertEquals(fields.get("accountId"), operation.getAccount().getId());
        assertEquals(fields.get("categoryId"), operation.getCategory().getId());
        assertEquals(fields.get("amount"), operation.getAmount());
        assertEquals(fields.get("date"),
                dateFormat.format(operation.getDate()));
    }
}
