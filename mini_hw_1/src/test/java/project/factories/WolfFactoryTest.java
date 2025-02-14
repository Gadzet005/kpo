package project.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.factories.animals.WolfFactory;
import project.factories.params.AnimalParams;

@SpringBootTest
public class WolfFactoryTest {
    private @Autowired WolfFactory wolfFactory;

    @Test
    @DisplayName("Create a new wolf")
    void create() {
        var id = 3;
        var foodPerDay = 15;
        var isHealthy = false;
        var wolf = wolfFactory.create(id,
                new AnimalParams(foodPerDay, isHealthy));

        assertEquals(wolf.getId(), id);
        assertEquals(wolf.getName(), "Wolf");
        assertNotNull(wolf.getDescription());
        assertEquals(wolf.isHealthy(), isHealthy);
        assertEquals(wolf.getFoodPerDay(), foodPerDay);
        assertEquals(wolf.isKind(), false);
    }
}
