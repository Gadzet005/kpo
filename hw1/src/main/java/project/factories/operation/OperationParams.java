package project.factories.operation;

import java.util.Date;

import lombok.Builder;
import project.consts.OperationType;
import project.domains.BankAccount;
import project.domains.Category;

@Builder
public record OperationParams(OperationType type, BankAccount account,
        double amount, Date date, String description, Category category) {
    public static class OperationParamsBuilder {
        OperationParamsBuilder() {
            this.type(OperationType.INCOME);
            this.amount(0.0);
            this.date(new Date());
            this.description("no description");
            this.category(null);
            this.account(null);
        }
    }

    public static OperationParams empty() {
        return OperationParams.builder().build();
    }
}
