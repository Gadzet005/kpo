package project.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.domains.animals.Herbo;
import project.factories.animals.RabbitFactory;
import project.factories.params.HerboParams;

@SpringBootTest
public class RabbitFactoryTest {
    private @Autowired RabbitFactory rabbitFactory;

    @Test
    @DisplayName("Create a new rabbit")
    void create() {
        var id = 1;
        var foodPerDay = 10.0;
        var isHealthy = true;
        var kindness = 9.0;
        var rabbit = rabbitFactory.create(id,
                new HerboParams(foodPerDay, isHealthy, kindness));

        assertEquals(rabbit.getId(), id);
        assertEquals(rabbit.getName(), "Rabbit");
        assertNotNull(rabbit.getDescription());
        assertEquals(rabbit.isHealthy(), isHealthy);
        assertEquals(((Herbo) rabbit).getKindness(), kindness);
        assertEquals(rabbit.getFoodPerDay(), foodPerDay);
        assertEquals(rabbit.isKind(), true);
    }
}
