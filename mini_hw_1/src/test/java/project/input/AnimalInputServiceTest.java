package project.input;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import project.cli.input.AnimalInputService;
import project.utils.CLITest;

public class AnimalInputServiceTest extends CLITest {
    private @InjectMocks AnimalInputService inputService;

    @Test
    @DisplayName("Test valid input")
    public void testValidRun() {
        Mockito.when(reader.nextLine()).thenReturn("10.0").thenReturn("f");

        var result = inputService.execute();

        assertEquals(10.0, result.foodPerDay);
        assertEquals(false, result.isHealthy);
    }

    @Test
    @DisplayName("Test invalid input")
    public void testInvalidRun() {
        Mockito.when(reader.nextLine()).thenReturn("-100").thenReturn("100")
                .thenReturn("invalid").thenReturn("f");

        var result = inputService.execute();

        assertEquals(100, result.foodPerDay);
        assertEquals(false, result.isHealthy);
    }
}
