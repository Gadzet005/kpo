package project.commands.account_operations;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.commands.Command;
import project.domains.Operation;
import project.storages.HSEBank;

@Component
public class GetBankAccountOperations
        implements Command<Collection<Operation>, Integer> {
    private HSEBank bank;

    @Autowired
    public GetBankAccountOperations(HSEBank bank) {
        this.bank = bank;
    }

    @Override
    public Collection<Operation> execute(Integer accountId) {
        return bank.getOperationStorage().getStream().filterByAccount(accountId)
                .toList();
    }
}
