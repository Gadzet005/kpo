package project.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.domains.BankAccount;
import project.serializers.converters.BankAccountFieldConverter;
import project.serializers.converters.base.FieldList;
import project.serializers.exceptions.ConvertException;

@SpringBootTest
class BankAccountConverterTest {
    @Autowired
    BankAccountFieldConverter converter;

    @Test
    @DisplayName("Test convert")
    void testConvert() {
        var account = new BankAccount(1, "test account", 123);
        var fields = converter.convert(account);

        assertEquals(account.getId(), fields.get("id"));
        assertEquals(account.getName(), fields.get("name"));
        assertEquals(account.getBalance(), fields.get("balance"));
    }

    @Test
    @DisplayName("Test convertBack")
    void testConvertBack() throws ConvertException {
        var fields = new FieldList();
        fields.add("id", 1);
        fields.add("name", "test account");
        fields.add("balance", 123.0);

        var account = converter.convertBack(fields);

        assertEquals(fields.get("id"), account.getId());
        assertEquals(fields.get("name"), account.getName());
        assertEquals(fields.get("balance"), account.getBalance());
    }
}
