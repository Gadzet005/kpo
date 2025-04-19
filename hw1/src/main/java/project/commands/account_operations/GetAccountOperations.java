package project.commands.account_operations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.commands.Command;
import project.commands.CommandError;
import project.domains.Operation;
import project.storages.HSEBank;

@Component
public class GetAccountOperations implements Command<List<Operation>, Integer> {
    private HSEBank bank;

    @Autowired
    public GetAccountOperations(HSEBank bank) {
        this.bank = bank;
    }

    @Override
    public List<Operation> execute(Integer accountId) throws CommandError {
        if (!bank.getAccountStorage().hasAccount(accountId)) {
            throw new CommandError("Account not found");
        }

        return bank.getOperationStorage().getStream().filterByAccount(accountId)
                .toList();
    }
}
