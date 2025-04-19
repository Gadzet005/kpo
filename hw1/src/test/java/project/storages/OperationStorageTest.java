package project.storages;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.factories.operation.OperationFactory;
import project.factories.operation.OperationParams;
import project.utils.IdCounter;

@SpringBootTest
class OperationStorageTest {
    @Autowired
    OperationStorage operationStorage;

    @BeforeEach
    void setUp() {
        operationStorage.clear();
    }

    @Test
    @DisplayName("OperationStorage.createOperation")
    void createOperation() {
        var params = OperationParams.empty();
        var op = operationStorage.createOperation(params);

        assertEquals(params.type(), op.getType());
        assertSame(params.account(), op.getAccount());
        assertEquals(params.amount(), op.getAmount());
        assertEquals(params.description(), op.getDescription());
        assertSame(params.category(), op.getCategory());
        assertEquals(params.date(), op.getDate());
    }

    @Test
    @DisplayName("OperationStorage.addOperation")
    void addOperation() {
        var factory = new OperationFactory(new IdCounter());
        var op = factory.create(OperationParams.empty());
        var res = operationStorage.addOperation(op);

        assertTrue(res);
        assertTrue(operationStorage.hasOperation(op.getId()));
    }

    @Test
    @DisplayName("OperationStorage.addOperation if it exists")
    void addOperationIfExists() {
        var factory = new OperationFactory(new IdCounter());
        var op = factory.create(OperationParams.empty());

        operationStorage.addOperation(op);
        var res = operationStorage.addOperation(op);

        assertFalse(res);
        assertTrue(operationStorage.hasOperation(op.getId()));
    }

    @Test
    @DisplayName("OperationStorage.getOperation")
    void getOperation() {
        var op = operationStorage.createOperation(OperationParams.empty());
        var res = operationStorage.getOperation(op.getId());

        assertSame(op, res);
    }

    @Test
    @DisplayName("OperationStorage.getOperations if not found")
    void getOperationsNotFound() {
        var res = operationStorage.getOperation(123);
        assertNull(res);
    }

    @Test
    @DisplayName("OperationStorage.getOperations")
    void getAllOperations() {
        var op1 = operationStorage.createOperation(OperationParams.empty());
        var op2 = operationStorage.createOperation(OperationParams.empty());

        var res = operationStorage.getOperations();
        assertEquals(2, res.size());
        assertTrue(res.contains(op1));
        assertTrue(res.contains(op2));
    }

    @Test
    @DisplayName("OperationStorage.getStream")
    void getStream() {
        var op1 = operationStorage.createOperation(OperationParams.empty());
        var op2 = operationStorage.createOperation(OperationParams.empty());

        var res = operationStorage.getStream().toList();
        assertEquals(2, res.size());
        assertTrue(res.contains(op1));
        assertTrue(res.contains(op2));
    }

    @Test
    @DisplayName("OperationStorage.hasOperation")
    void hasOperation() {
        var op = operationStorage.createOperation(OperationParams.empty());
        assertTrue(operationStorage.hasOperation(op.getId()));
    }

    @Test
    @DisplayName("OperationStorage.removeOperation")
    void removeOperation() {
        var op = operationStorage.createOperation(OperationParams.empty());
        operationStorage.removeOperation(op.getId());

        assertFalse(operationStorage.hasOperation(op.getId()));
    }

    @Test
    @DisplayName("OperationStorage.clear")
    void clearOperations() {
        var op = operationStorage.createOperation(OperationParams.empty());
        operationStorage.clear();

        assertFalse(operationStorage.hasOperation(op.getId()));
    }
}
