package project.input;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;

import project.cli.input.ThingInputService;
import project.utils.CLITest;

public class ThingInputServiceTest extends CLITest {
    private @InjectMocks ThingInputService inputService;

    @Test
    @DisplayName("Simple test")
    public void testValidRun() {
        Mockito.when(reader.nextLine()).thenReturn("");
        var result = inputService.execute();
        assertNotNull(result);
    }
}
