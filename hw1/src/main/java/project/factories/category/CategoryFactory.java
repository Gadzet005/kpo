package project.factories.category;

import lombok.AllArgsConstructor;
import project.domains.Category;
import project.utils.IdCounter;

@AllArgsConstructor
public class CategoryFactory {
    private IdCounter counter;

    public Category create(CategoryParams params) {
        return new Category(counter.getNextId(), params.name(), params.type());
    }
}
