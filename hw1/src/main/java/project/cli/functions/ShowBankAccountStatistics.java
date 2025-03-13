package project.cli.functions;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.cli.input.DateField;
import project.cli.input.IntField;
import project.commands.CommandError;
import project.commands.CommandWithTimer;
import project.commands.account_stats.*;

@Component
public class ShowBankAccountStatistics implements CLIFunction {
    private static final String SUM_FORMAT = "Sum: %.2f%n";
    private static final String COUNT_FORMAT = "Count: %d%n";
    private static final String AVG_FORMAT = "Avg: %.2f%n";
    private Scanner reader;
    private CommandWithTimer<GetAccountStatsResult, GetAccountStatsParams> getAccountStats;

    @Autowired
    public ShowBankAccountStatistics(Scanner reader,
            GetAccountStats getBankAccountStatistics) {
        this.reader = reader;
        this.getAccountStats = new CommandWithTimer<>(getBankAccountStatistics);
    }

    @Override
    public void execute() {
        var accountId = IntField.builder().minValue(0).name("Account ID")
                .build().execute(reader);
        var startDate = DateField.builder().name("Start Date").nullable(true)
                .description("press enter if there is no start date").build()
                .execute(reader);
        var endDate = DateField.builder().name("End Date").nullable(true)
                .description("press enter if there is no end date").build()
                .execute(reader);

        long duration;
        GetAccountStatsResult result;
        try {
            var res = getAccountStats.execute(
                    new GetAccountStatsParams(accountId, startDate, endDate));
            duration = res.duration();
            result = res.wrapped();
        } catch (CommandError e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Income operations:");
        System.out.printf("\t" + SUM_FORMAT, result.incomeSum());
        System.out.printf("\t" + COUNT_FORMAT, result.incomeCount());
        System.out.printf("\t" + AVG_FORMAT, result.incomeAvg());
        System.out.println();
        System.out.println("Expense operations:");
        System.out.printf("\t" + SUM_FORMAT, result.expenseSum());
        System.out.printf("\t" + COUNT_FORMAT, result.expenseCount());
        System.out.printf("\t" + AVG_FORMAT, result.expenseAvg());
        System.out.println();
        System.out.println("Total:");
        System.out.printf("\t" + SUM_FORMAT, result.sum());
        System.out.printf("\t" + COUNT_FORMAT, result.count());
        System.out.printf("\t" + AVG_FORMAT, result.avg());
        System.out.println();
        System.out.println("Categories:");
        result.categories().forEach(catStats -> {
            System.out.printf("\t %s: %.2f%%%n", catStats.name(),
                    catStats.percent());
            System.out.println(
                    "\t\tType: " + catStats.type().toString().toLowerCase());
            System.out.printf("\t\t" + SUM_FORMAT, catStats.total());
            System.out.printf("\t\t" + COUNT_FORMAT, catStats.count());
            System.out.printf("\t\t" + AVG_FORMAT, catStats.avg());
            System.out.println();
        });

        System.out.println("Done in " + duration + " ms.");
    }
}
