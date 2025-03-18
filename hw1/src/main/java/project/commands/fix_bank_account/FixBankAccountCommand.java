package project.commands.fix_bank_account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.commands.Command;
import project.commands.CommandError;
import project.storages.HSEBank;

@Component
public class FixBankAccountCommand
        implements Command<FixBankAccountResult, Integer> {
    private HSEBank bank;

    @Autowired
    public FixBankAccountCommand(HSEBank bank) {
        this.bank = bank;
    }

    @Override
    public FixBankAccountResult execute(Integer accountId) throws CommandError {
        var account = bank.getAccountStorage().getAccount(accountId);
        if (account == null) {
            throw new CommandError("Account not found");
        }
        var oldBalance = account.getBalance();
        var newBalance = bank.getOperationStorage().getStream()
                .filterByAccount(accountId).sum();
        account.setBalance(newBalance);
        return new FixBankAccountResult(oldBalance, newBalance);
    }
}
