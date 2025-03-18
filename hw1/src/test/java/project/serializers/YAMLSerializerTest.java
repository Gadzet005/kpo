package project.serializers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import project.consts.OperationType;
import project.domains.Category;
import project.serializers.base.Serializer;
import project.serializers.converters.CategoryFieldConverter;
import project.serializers.exceptions.SerializerException;

class YAMLSerializerTest {
    Serializer<Category> categorySerializer = new YAMLSerializer<>(
            new CategoryFieldConverter());

    @Test
    @DisplayName("YAMLSerializer.serialize")
    void testSerialize() throws SerializerException {
        var cat1 = new Category(1, "cat1", OperationType.INCOME);
        var cat2 = new Category(0, "cat2", OperationType.EXPENSE);
        var categories = List.of(cat1, cat2);
        var yaml = categorySerializer.serialize(categories);

        var cats = categorySerializer.deserialize(yaml);
        assertEquals(2, cats.size());
        assertEquals(1, cats.get(0).getId());
        assertEquals("cat1", cats.get(0).getName());
        assertEquals(OperationType.INCOME, cats.get(0).getType());
        assertEquals(0, cats.get(1).getId());
        assertEquals("cat2", cats.get(1).getName());
        assertEquals(OperationType.EXPENSE, cats.get(1).getType());
    }

    @Test
    @DisplayName("YAMLSerializer.deserialize")
    void testDeserialize() throws SerializerException {
        var yaml = """
                - id: 1
                  name: cat1
                  type: INCOME
                - id: 0
                  name: cat2
                  type: EXPENSE
                """;
        var cats = categorySerializer.deserialize(yaml);

        assertEquals(2, cats.size());
        assertEquals(1, cats.get(0).getId());
        assertEquals("cat1", cats.get(0).getName());
        assertEquals(OperationType.INCOME, cats.get(0).getType());
        assertEquals(0, cats.get(1).getId());
        assertEquals("cat2", cats.get(1).getName());
        assertEquals(OperationType.EXPENSE, cats.get(1).getType());
    }
}
