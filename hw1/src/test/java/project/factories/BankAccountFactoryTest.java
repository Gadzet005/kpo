package project.factories;

import org.junit.jupiter.api.Test;

import project.factories.bank_account.BankAccountFactory;
import project.factories.bank_account.BankAccountParams;
import project.utils.IdCounter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;

class BankAccountFactoryTest {
    @Test
    @DisplayName("BankAccountFactory.create")
    void testCreate() {
        var bankAccountFactory = new BankAccountFactory(new IdCounter());
        var res = bankAccountFactory.create(new BankAccountParams("John Doe"));
        assertEquals("John Doe", res.getName());
    }
}
