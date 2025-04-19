package project.cli.functions;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.cli.input.IntField;
import project.commands.CommandError;
import project.commands.CommandWithTimer;
import project.commands.fix_bank_account.FixBankAccountCommand;
import project.commands.fix_bank_account.FixBankAccountResult;

@Component
public class FixBankAccount implements CLIFunction {
    private Scanner reader;
    private CommandWithTimer<FixBankAccountResult, Integer> fixBankAccountCommand;

    @Autowired
    public FixBankAccount(Scanner reader,
            FixBankAccountCommand fixBankAccountCommand) {
        this.fixBankAccountCommand = new CommandWithTimer<>(
                fixBankAccountCommand);
        this.reader = reader;
    }

    @Override
    public void execute() {
        var accountId = IntField.builder().name("Account ID").build()
                .execute(reader);

        long duration;
        FixBankAccountResult result;
        try {
            var res = fixBankAccountCommand.execute(accountId);
            duration = res.duration();
            result = res.wrapped();
        } catch (CommandError e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Bank account balance fixed");
        System.out.println("Old balance: " + result.oldBalance());
        System.out.println("New balance: " + result.newBalance());
        System.out.println("Done in " + duration + " ms");
    }
}
