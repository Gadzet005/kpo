package project.factories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import project.consts.OperationType;
import project.domains.BankAccount;
import project.domains.Category;
import project.factories.operation.OperationFactory;
import project.factories.operation.OperationParams;
import project.utils.IdCounter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.DisplayName;

@ExtendWith(MockitoExtension.class)
class OperationFactoryTest {
    @Mock
    Category category;
    @Mock
    BankAccount account;

    OperationFactory operationFactory = new OperationFactory(new IdCounter());

    @Test
    @DisplayName("OperationFactory.create")
    void testCreate() {
        when(category.getType()).thenReturn(OperationType.EXPENSE);
        var params = OperationParams.builder().type(OperationType.EXPENSE)
                .account(account).amount(100.0).description("food")
                .date(new Date()).category(category).build();
        var res = operationFactory.create(params);

        assertEquals(params.type(), res.getType());
        assertEquals(params.amount(), res.getAmount());
        assertEquals(params.description(), res.getDescription());
        assertEquals(params.date(), res.getDate());
        assertSame(params.account(), res.getAccount());
        assertSame(params.category(), res.getCategory());
    }

    @Test()
    @DisplayName("OperationFactory.create with negative amount")
    void testCreateNegativeAmount() {
        var params = OperationParams.builder().account(account)
                .category(category).amount(-100).build();
        assertThrows(IllegalArgumentException.class,
                () -> operationFactory.create(params));
    }

    @Test()
    @DisplayName("OperationFactory.create with type != category.type")
    void testCreateInvalidType() {
        when(category.getType()).thenReturn(OperationType.INCOME);
        var params = OperationParams.builder().type(OperationType.EXPENSE)
                .category(category).build();
        assertThrows(IllegalArgumentException.class,
                () -> operationFactory.create(params));
    }
}
