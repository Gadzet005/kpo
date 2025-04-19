package project.cli.functions;

import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.cli.input.IntField;
import project.commands.CommandWithTimer;
import project.commands.account_operations.GetAccountOperations;
import project.domains.Operation;

@Component
public class ShowBankAccountOperations implements CLIFunction {
    private Scanner reader;
    private CommandWithTimer<List<Operation>, Integer> getBankAccountOperations;

    @Autowired
    public ShowBankAccountOperations(Scanner reader,
            GetAccountOperations getBankAccountOperations) {
        this.reader = reader;
        this.getBankAccountOperations = new CommandWithTimer<>(
                getBankAccountOperations);
    }

    @Override
    public void execute() {
        var accountId = IntField.builder().minValue(0).name("Account ID")
                .build().execute(reader);

        long duration;
        List<Operation> operations;
        try {
            var res = getBankAccountOperations.execute(accountId);
            duration = res.duration();
            operations = res.wrapped();
        } catch (Exception e) {
            System.out.println("Error fetching operations: " + e.getMessage());
            return;
        }

        System.out.println("Operations:");
        for (var operation : operations) {
            System.out.printf("%s - %.2f%n", operation.getDescription(),
                    operation.getAmount());
        }
        System.out.println();
        System.out.println("Done in " + duration + " ms.");
        System.out.println();
    }
}
