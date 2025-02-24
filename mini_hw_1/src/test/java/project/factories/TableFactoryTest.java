package project.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import project.factories.params.EmptyParams;
import project.factories.things.TableFactory;

@SpringBootTest
public class TableFactoryTest {
    private @Autowired TableFactory tableFactory;

    @Test
    @DisplayName("Create a new table")
    void create() {
        var id = 1;
        var table = tableFactory.create(id, EmptyParams.DEFAULT);

        assertEquals(table.getId(), id);
        assertEquals(table.getName(), "Table");
        assertNotNull(table.getDescription());
    }
}
