package project.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.factories.animals.TigerFactory;
import project.factories.params.AnimalParams;

@SpringBootTest
public class TigerFactoryTest {
    private @Autowired TigerFactory tigerFactory;

    @Test
    @DisplayName("Create a new tiger")
    void create() {
        var id = 3;
        var foodPerDay = 15;
        var isHealthy = false;
        var tiger = tigerFactory.create(id,
                new AnimalParams(foodPerDay, isHealthy));

        assertEquals(tiger.getId(), id);
        assertEquals(tiger.getName(), "Tiger");
        assertNotNull(tiger.getDescription());
        assertEquals(tiger.isHealthy(), isHealthy);
        assertEquals(tiger.getFoodPerDay(), foodPerDay);
        assertEquals(tiger.isKind(), false);
    }
}
