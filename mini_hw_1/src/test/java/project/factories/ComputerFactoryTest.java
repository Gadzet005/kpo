package project.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.factories.params.EmptyParams;
import project.factories.things.ComputerFactory;

@SpringBootTest
public class ComputerFactoryTest {
    private @Autowired ComputerFactory computerFactory;

    @Test
    @DisplayName("Create a new computer")
    void create() {
        var id = 1;
        var computer = computerFactory.create(id, EmptyParams.DEFAULT);

        assertEquals(computer.getId(), id);
        assertEquals(computer.getName(), "Computer");
        assertNotNull(computer.getDescription());
    }
}
