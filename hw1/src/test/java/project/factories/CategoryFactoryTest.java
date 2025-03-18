package project.factories;

import org.junit.jupiter.api.Test;

import project.consts.OperationType;
import project.factories.category.CategoryFactory;
import project.factories.category.CategoryParams;
import project.utils.IdCounter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;

class CategoryFactoryTest {
    @Test
    @DisplayName("CategoryFactory.create")
    void testCreate() {
        var categoryFactory = new CategoryFactory(new IdCounter());
        var res = categoryFactory.create(
                new CategoryParams("Electronics", OperationType.EXPENSE));
        assertEquals("Electronics", res.getName());
        assertEquals(OperationType.EXPENSE, res.getType());
    }
}
