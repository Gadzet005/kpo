package project.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;

import project.cli.input.AnimalInputService;
import project.cli.input.HerboInputService;
import project.utils.CLITest;

public class HerboInputServiceTest extends CLITest {
    private @InjectMocks @Spy AnimalInputService animalInputService;
    private @InjectMocks HerboInputService inputService;

    @Test
    @DisplayName("Test valid input")
    public void testValidRun() {
        Mockito.when(reader.nextLine()).thenReturn("10.0").thenReturn("f")
                .thenReturn("5.55");

        var result = inputService.execute();

        assertEquals(10.0, result.foodPerDay);
        assertEquals(false, result.isHealthy);
        assertEquals(5.55, result.kindness);
    }

    @Test
    @DisplayName("Test invalid input")
    public void testInvalidRun() {
        Mockito.when(reader.nextLine()).thenReturn("-100").thenReturn("100")
                .thenReturn("invalid").thenReturn("f").thenReturn("-432.435")
                .thenReturn("0");

        var result = inputService.execute();

        assertEquals(100, result.foodPerDay);
        assertEquals(false, result.isHealthy);
        assertEquals(0, result.kindness);
    }
}
