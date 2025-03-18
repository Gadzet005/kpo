package project.commands.account_stats;

import java.util.ArrayList;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import project.commands.Command;
import project.commands.CommandError;
import project.consts.OperationType;
import project.storages.HSEBank;
import project.storages.utils.OperationListStream;

@Component
public class GetAccountStats
        implements Command<GetAccountStatsResult, GetAccountStatsParams> {
    private HSEBank bank;

    @Autowired
    GetAccountStats(HSEBank bank) {
        this.bank = bank;
    }

    public GetAccountStatsResult execute(GetAccountStatsParams params)
            throws CommandError {
        var builder = GetAccountStatsResult.builder();

        if (!bank.getAccountStorage().hasAccount(params.accountId())) {
            throw new CommandError("Account not found");
        }

        Supplier<OperationListStream> opStream = () -> bank
                .getOperationStorage().getStream()
                .filterByAccount(params.accountId())
                .filterByDateRange(params.start(), params.end());
        Supplier<OperationListStream> incomeStream = () -> opStream.get()
                .filterByType(OperationType.INCOME);
        Supplier<OperationListStream> expenseStream = () -> opStream.get()
                .filterByType(OperationType.EXPENSE);

        var incomeSum = incomeStream.get().sumAbs();
        var expenseSum = expenseStream.get().sumAbs();
        var totalAbs = incomeSum + expenseSum;

        builder.incomeSum(incomeSum);
        builder.incomeCount(incomeStream.get().count());
        builder.expenseSum(expenseSum);
        builder.expenseCount(expenseStream.get().count());

        var categories = bank.getCategoryStorage().getCategories();
        var catStatList = new ArrayList<GetAccountStatsResult.CategoryStats>();
        for (var category : categories) {
            Supplier<OperationListStream> catStream = () -> opStream.get()
                    .filterByCategory(category.getId());
            var count = catStream.get().count();
            if (count == 0) {
                continue;
            }

            var total = catStream.get().sumAbs();
            var percent = total / totalAbs * 100.0;
            catStatList.add(
                    new GetAccountStatsResult.CategoryStats(category.getName(),
                            category.getType(), total, count, percent));
        }

        catStatList.sort((a, b) -> Double.compare(b.percent(), a.percent()));
        builder.categories(catStatList);

        return builder.build();
    }
}
