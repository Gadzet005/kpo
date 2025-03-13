package project.factories.operation;

import java.util.Date;

import project.consts.OperationType;
import project.domains.BankAccount;
import project.domains.Category;

public record OperationParams(OperationType type, BankAccount account,
        double amount, Date date, String description, Category category) {
}
