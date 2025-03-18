package project.factories.bank_account;

import lombok.AllArgsConstructor;
import project.domains.BankAccount;
import project.utils.IdCounter;

@AllArgsConstructor
public class BankAccountFactory {
    private IdCounter counter;

    public BankAccount create(BankAccountParams params) {
        return new BankAccount(counter.getNextId(), params.name(), 0);
    }
}
