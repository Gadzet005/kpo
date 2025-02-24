package project.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InventoryIdCounterTest {
    @Autowired
    InventoryIdCounter idCounter;

    @Test
    @DisplayName("Get the next ID")
    public void testGetNextId() {
        int id1 = idCounter.getNextId();
        int id2 = idCounter.getNextId();
        int id3 = idCounter.getNextId();

        assertEquals(id1, 0);
        assertEquals(id2, 1);
        assertEquals(id3, 2);
    }

    @Test
    @DisplayName("Reset the counter")
    public void testResetCounter() {
        idCounter.getNextId();
        idCounter.getNextId();
        idCounter.resetCounter();
        int id = idCounter.getNextId();

        assertEquals(id, 0);
    }
}
