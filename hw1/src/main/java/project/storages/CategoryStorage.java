package project.storages;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import project.domains.Category;
import project.factories.category.CategoryFactory;
import project.factories.category.CategoryParams;
import project.utils.IdCounter;

@Component
public class CategoryStorage {
    private IdCounter counter = new IdCounter();
    private HashMap<Integer, Category> categories = new HashMap<>();
    private CategoryFactory factory = new CategoryFactory(counter);

    public Category createCategory(CategoryParams params) {
        var category = factory.create(params);
        categories.put(category.getId(), category);
        return category;
    }

    public boolean addCategory(Category category) {
        if (hasCategory(category.getId())) {
            return false;
        }
        categories.put(category.getId(), category);
        return true;
    }

    public Category getCategory(int id) {
        return categories.get(id);
    }

    public List<Category> getCategories() {
        return categories.values().stream().toList();
    }

    public boolean hasCategory(int id) {
        return categories.containsKey(id);
    }

    public Category removeCategory(int id) {
        return categories.remove(id);
    }

    public void clear() {
        categories.clear();
        counter.resetCounter();
    }
}
