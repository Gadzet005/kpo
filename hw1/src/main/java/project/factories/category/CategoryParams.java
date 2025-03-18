package project.factories.category;

import project.consts.OperationType;

public record CategoryParams(String name, OperationType type) {
    public static CategoryParams empty() {
        return new CategoryParams("Category", OperationType.INCOME);
    }
}
