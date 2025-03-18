package project.serializers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import project.domains.BankAccount;
import project.serializers.base.Serializer;
import project.serializers.converters.BankAccountFieldConverter;
import project.serializers.exceptions.SerializerException;

class CSVSerializerTest {
    Serializer<BankAccount> bankAccountSerializer = new CSVSerializer<>(
            new BankAccountFieldConverter());

    @Test
    @DisplayName("CSVSerializer.serialize")
    void testSerialize() throws SerializerException {
        var account1 = new BankAccount(1, "test account", 123);
        var account2 = new BankAccount(2, "nnn", 0);
        var csv = bankAccountSerializer.serialize(List.of(account1, account2));
        var lines = csv.split("\n");

        assertEquals(3, lines.length);
        assertTrue(lines[0].contains("id,name,balance"));
        assertTrue(lines[1].contains("1,test account,123"));
        assertTrue(lines[2].contains("2,nnn,0"));
    }

    @Test
    @DisplayName("CSVSerializer.serializer with empty list")
    void testDeserializeEmptyList() throws SerializerException {
        var csv = bankAccountSerializer.serialize(List.of());
        assertEquals("", csv);
    }

    @Test
    @DisplayName("CSVSerializer.deserialize with empty CSV")
    void testDeserializeEmptyCSV() throws SerializerException {
        var accounts = bankAccountSerializer.deserialize("");
        assertEquals(0, accounts.size());
    }

    @Test
    @DisplayName("CSVSerializer.deserialize")
    void testDeserialize() throws SerializerException {
        var csv = "id,name,balance\n1,test account,123\n2,nnn,0";
        var accounts = bankAccountSerializer.deserialize(csv);

        assertEquals(2, accounts.size());
        assertEquals(1, accounts.get(0).getId());
        assertEquals("test account", accounts.get(0).getName());
        assertEquals(123, accounts.get(0).getBalance());
        assertEquals(2, accounts.get(1).getId());
        assertEquals("nnn", accounts.get(1).getName());
        assertEquals(0, accounts.get(1).getBalance());
    }

    @Test()
    @DisplayName("CSVSerializer.deserialize with incorrect format")
    void testDeserializeIncorrectFormat() {
        var testCases = List.of(
                "id,name,balance\n1,test account,123\n2,nnn,0\ninvalid",
                "id,name,balance\ninvalid,test account,123");
        for (var csv : testCases) {
            assertThrows(SerializerException.class,
                    () -> bankAccountSerializer.deserialize(csv));
        }
    }
}
