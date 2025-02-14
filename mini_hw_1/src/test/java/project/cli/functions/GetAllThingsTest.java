package project.cli.functions;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import project.interfaces.IInventory;
import project.services.ThingStorage;

@SpringBootTest
public class GetAllThingsTest {
    @Mock
    ThingStorage things;
    @InjectMocks
    GetAllThings getAllThings;
    @Mock
    IInventory thing;

    @Test
    @DisplayName("Test execute method without things")
    public void testExecuteWithoutAnimals() {
        when(things.getThings()).thenReturn(new IInventory[] {});
        getAllThings.execute();
    }

    @Test
    @DisplayName("Test execute method with things")
    public void testExecuteWithAnimals() {
        when(things.getThings()).thenReturn(new IInventory[] { thing, thing });
        getAllThings.execute();
    }
}
