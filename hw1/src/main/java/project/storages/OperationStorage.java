package project.storages;

import java.util.Collection;
import java.util.HashMap;
import org.springframework.stereotype.Component;

import project.domains.Operation;
import project.factories.operation.OperationFactory;
import project.factories.operation.OperationParams;
import project.storages.utils.OperationListStream;
import project.utils.IdCounter;

@Component
public class OperationStorage {
    private IdCounter counter;
    private HashMap<Integer, Operation> operations = new HashMap<>();
    private OperationFactory factory = new OperationFactory(counter);

    public Operation createOperation(OperationParams params) {
        var operation = factory.create(params);
        operations.put(operation.getId(), operation);
        return operation;
    }

    public boolean addOperation(Operation operation) {
        if (hasOperation(operation.getId())) {
            return false;
        }
        operations.put(operation.getId(), operation);
        return true;
    }

    public Operation getOperation(int id) {
        return operations.get(id);
    }

    public Collection<Operation> getOperations() {
        return operations.values();
    }

    public OperationListStream getStream() {
        return new OperationListStream(operations.values().stream());
    }

    public boolean hasOperation(int id) {
        return operations.containsKey(id);
    }

    public Operation removeOperation(int id) {
        return operations.remove(id);
    }
}
