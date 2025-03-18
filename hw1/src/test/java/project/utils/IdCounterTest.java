package project.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class IdCounterTest {
    @Test
    @DisplayName("IdCounter.getNextId")
    void testGetNextId() {
        var counter = new IdCounter();
        var id1 = counter.getNextId();
        var id2 = counter.getNextId();
        assertNotEquals(id1, id2);
    }

    @Test
    @DisplayName("IdCounter.resetCounter")
    void testResetCounter() {
        var counter = new IdCounter();
        int id1 = counter.getNextId();
        counter.getNextId();
        counter.resetCounter();
        int id2 = counter.getNextId();

        assertEquals(id1, id2);
    }
}
