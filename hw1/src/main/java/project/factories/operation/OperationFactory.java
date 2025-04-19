package project.factories.operation;

import lombok.AllArgsConstructor;
import project.domains.Operation;
import project.utils.IdCounter;

@AllArgsConstructor
public class OperationFactory {
    private IdCounter counter;

    public Operation create(OperationParams params)
            throws IllegalArgumentException {
        if (params.amount() < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (params.category() != null
                && params.category().getType() != params.type()) {
            throw new IllegalArgumentException(
                    "Invalid category type for operation type");
        }
        return new Operation(counter.getNextId(), params.type(),
                params.amount(), params.date(), params.description(),
                params.account(), params.category());
    }
}
