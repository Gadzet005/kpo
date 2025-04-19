package project.storages.utils;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import project.consts.OperationType;
import project.domains.Operation;

public class OperationListStream {
    private Stream<Operation> operations;

    public OperationListStream(Stream<Operation> operations) {
        this.operations = operations;
    }

    public OperationListStream filterByType(OperationType type) {
        operations = operations.filter(op -> op.getType() == type);
        return this;
    }

    public OperationListStream filterByAmountRange(Double min, Double max) {
        operations = operations
                .filter(op -> (min == null || op.getAmount() >= min)
                        && (max == null || op.getAmount() <= max));
        return this;
    }

    public OperationListStream filterByDateRange(Date startDate, Date endDate) {
        operations = operations.filter(op -> (startDate == null
                || op.getDate().after(startDate) || op.getDate() == startDate)
                && (endDate == null || op.getDate().before(endDate))
                || op.getDate() == endDate);
        return this;
    }

    public OperationListStream filterByCategory(int categoryId) {
        operations = operations
                .filter(op -> op.getCategory().getId() == categoryId);
        return this;
    }

    public OperationListStream filterByAccount(int accountId) {
        operations = operations
                .filter(op -> op.getAccount().getId() == accountId);
        return this;
    }

    public List<Operation> toList() {
        return operations.toList();
    }

    public double sum() {
        return operations.mapToDouble(op -> {
            var sign = op.getType() == OperationType.INCOME ? 1.0 : -1.0;
            return sign * op.getAmount();
        }).sum();
    }

    public double sumAbs() {
        return operations.mapToDouble(Operation::getAmount).sum();
    }

    public double average() {
        return operations.mapToDouble(Operation::getAmount).average().orElse(0);
    }

    public int count() {
        return (int) operations.count();
    }

    public OperationListStream sortByDate() {
        return new OperationListStream(operations
                .sorted((op1, op2) -> op1.getDate().compareTo(op2.getDate())));
    }

    public OperationListStream sortByAmount() {
        return new OperationListStream(operations.sorted((op1, op2) -> Double
                .compare(op1.getAmount(), op2.getAmount())));
    }
}
