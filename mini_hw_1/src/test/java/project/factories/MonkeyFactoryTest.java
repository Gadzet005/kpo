package project.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.factories.animals.MonkeyFactory;
import project.factories.params.AnimalParams;

@SpringBootTest
public class MonkeyFactoryTest {
    private @Autowired MonkeyFactory monkeyFactory;

    @Test
    @DisplayName("Create a new monkey")
    void create() {
        var id = 1;
        var foodPerDay = 10.0;
        var isHealthy = true;
        var monkey = monkeyFactory.create(id,
                new AnimalParams(foodPerDay, isHealthy));

        assertEquals(monkey.getId(), id);
        assertEquals(monkey.getName(), "Monkey");
        assertNotNull(monkey.getDescription());
        assertEquals(monkey.isHealthy(), isHealthy);
        assertEquals(monkey.isKind(), false);
        assertEquals(monkey.getFoodPerDay(), foodPerDay);
    }
}
