package project.storages;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.factories.category.CategoryFactory;
import project.factories.category.CategoryParams;
import project.utils.IdCounter;

@SpringBootTest
class CategoryStorageTest {
    @Autowired
    CategoryStorage categoryStorage;

    @BeforeEach
    void setUp() {
        categoryStorage.clear();
    }

    @Test
    @DisplayName("CategoryStorage.createCategory")
    void createCategory() {
        var params = CategoryParams.empty();
        var res = categoryStorage.createCategory(params);
        assertEquals(params.name(), res.getName());
        assertEquals(params.type(), res.getType());
    }

    @Test
    @DisplayName("CategoryStorage.addCategory")
    void addAccount() {
        var caregoryFactory = new CategoryFactory(new IdCounter());
        var cat = caregoryFactory.create(CategoryParams.empty());

        var res = categoryStorage.addCategory(cat);
        assertTrue(res);
        assertTrue(categoryStorage.hasCategory(cat.getId()));
    }

    @Test
    @DisplayName("Test CategoryStorage.addCategory if it exists")
    void addAccountIfExists() {
        var caregoryFactory = new CategoryFactory(new IdCounter());
        var cat = caregoryFactory.create(CategoryParams.empty());

        categoryStorage.addCategory(cat);
        var res = categoryStorage.addCategory(cat);

        assertFalse(res);
        assertTrue(categoryStorage.hasCategory(cat.getId()));
    }

    @Test
    @DisplayName("CategoryStorage.getCategory")
    void getCategory() {
        var cat = categoryStorage.createCategory(CategoryParams.empty());
        var res = categoryStorage.getCategory(cat.getId());

        assertSame(cat, res);
    }

    @Test
    @DisplayName("CategoryStorage.getCategory if not found")
    void getCategoryNotFound() {
        var res = categoryStorage.getCategory(123);
        assertNull(res);
    }

    @Test
    @DisplayName("CategoryStorage.getCategories")
    void getAllCategories() {
        var cat1 = categoryStorage.createCategory(CategoryParams.empty());
        var cat2 = categoryStorage.createCategory(CategoryParams.empty());

        var res = categoryStorage.getCategories();
        assertEquals(2, res.size());
        assertTrue(res.contains(cat1));
        assertTrue(res.contains(cat2));
    }

    @Test
    @DisplayName("CategoryStorage.hasCategory")
    void hasCategory() {
        var cat = categoryStorage.createCategory(CategoryParams.empty());
        assertTrue(categoryStorage.hasCategory(cat.getId()));
    }

    @Test
    @DisplayName("CategoryStorage.removeCategory")
    void removeCategory() {
        var cat = categoryStorage.createCategory(CategoryParams.empty());
        categoryStorage.removeCategory(cat.getId());
        assertFalse(categoryStorage.hasCategory(cat.getId()));
    }

    @Test
    @DisplayName("CategoryStorage.clear")
    void clearCategories() {
        categoryStorage.createCategory(CategoryParams.empty());
        categoryStorage.createCategory(CategoryParams.empty());

        categoryStorage.clear();

        assertEquals(0, categoryStorage.getCategories().size());
    }
}
