package project.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.consts.OperationType;
import project.domains.Category;
import project.serializers.converters.CategoryFieldConverter;
import project.serializers.converters.base.FieldList;
import project.serializers.exceptions.ConvertException;

@SpringBootTest
class CategoryConverterTest {
    @Autowired
    CategoryFieldConverter converter;

    @Test
    @DisplayName("Test convert")
    void testConvert() {
        var category = new Category(1, "test category", OperationType.INCOME);
        var fields = converter.convert(category);

        assertEquals(category.getId(), fields.get("id"));
        assertEquals(category.getName(), fields.get("name"));
        assertEquals(category.getType().name(), fields.get("type"));
    }

    @Test
    @DisplayName("Test convertBack")
    void testConvertBack() throws ConvertException {
        var fields = new FieldList();
        fields.add("id", 1);
        fields.add("name", "test category");
        fields.add("type", OperationType.INCOME.name());

        var category = converter.convertBack(fields);

        assertEquals(fields.get("id"), category.getId());
        assertEquals(fields.get("name"), category.getName());
        assertEquals(fields.get("type"), category.getType().name());
    }
}
