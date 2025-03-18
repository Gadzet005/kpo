package project.serializers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import project.consts.OperationType;
import project.domains.Category;
import project.serializers.base.Serializer;
import project.serializers.converters.CategoryFieldConverter;
import project.serializers.exceptions.SerializerException;

class JSONSerializerTest {
    Serializer<Category> categorySerializer = new JSONSerializer<>(
            new CategoryFieldConverter());

    @Test
    @DisplayName("JSONSerializer.serialize")
    void testSerialize() throws SerializerException {
        var category1 = new Category(1, "cat1", OperationType.INCOME);
        var category2 = new Category(4, "cat2", OperationType.EXPENSE);

        var categories = List.of(category1, category2);
        var json = categorySerializer.serialize(categories);

        var cats = categorySerializer.deserialize(json);
        assertEquals(2, cats.size());
        assertEquals(1, cats.get(0).getId());
        assertEquals("cat1", cats.get(0).getName());
        assertEquals(OperationType.INCOME, cats.get(0).getType());
        assertEquals(4, cats.get(1).getId());
        assertEquals("cat2", cats.get(1).getName());
        assertEquals(OperationType.EXPENSE, cats.get(1).getType());
    }

    @Test
    @DisplayName("JSONSerializer.deserialize")
    void testDeserialize() throws SerializerException {
        var json = """
                [{"id":1.0,"name":"cat1","type":"INCOME"},
                {"id":4.0,"name":"cat2","type":"EXPENSE"}]""";
        var categories = categorySerializer.deserialize(json);

        assertEquals(2, categories.size());
        assertEquals(1, categories.get(0).getId());
        assertEquals("cat1", categories.get(0).getName());
        assertEquals(OperationType.INCOME, categories.get(0).getType());
        assertEquals(4, categories.get(1).getId());
        assertEquals("cat2", categories.get(1).getName());
        assertEquals(OperationType.EXPENSE, categories.get(1).getType());
    }

    @Test
    @DisplayName("JSONSerializer.deserialize with incorrect format")
    void testDeserializeIncorrectFormat() {
        var testCases = List.of("[invalid!]",
                "{\"id\":invalid!,\"name\":\"cat1\",\"type\":\"INCOME\"}");

        for (var json : testCases) {
            assertThrows(SerializerException.class,
                    () -> categorySerializer.deserialize(json));
        }
    }
}
