package project.commands.account_stats;

import java.util.Collection;

import lombok.Builder;
import project.consts.OperationType;

@Builder
public record GetAccountStatsResult(double incomeSum, int incomeCount,
        double expenseSum, int expenseCount,
        Collection<CategoryStats> categories) {
    public record CategoryStats(String name, OperationType type, double total,
            int count, double percent) {
        public double avg() {
            return total / Math.max(count, 1);
        }
    }

    public double incomeAvg() {
        return incomeSum / Math.max(incomeCount, 1);
    }

    public double expenseAvg() {
        return expenseSum / Math.max(expenseCount, 1);
    }

    public double sum() {
        return incomeSum - expenseSum;
    }

    public double sumAbs() {
        return incomeSum + expenseSum;
    }

    public int count() {
        return incomeCount + expenseCount;
    }

    public double avg() {
        return sumAbs() / Math.max(count(), 1);
    }
}