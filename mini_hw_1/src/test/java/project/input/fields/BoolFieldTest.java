package project.input.fields;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import project.cli.input.fields.BoolField;
import project.utils.CLITest;

public class BoolFieldTest extends CLITest {
    @Test
    @DisplayName("Test valid true input")
    public void testTrueInput() {
        Mockito.when(reader.nextLine()).thenReturn("t");
        var field = BoolField.builder().build();
        assertEquals(true, field.execute(reader));
    }

    @Test
    @DisplayName("Test valid false input")
    public void testFalseInput() {
        Mockito.when(reader.nextLine()).thenReturn("f");
        var field = BoolField.builder().build();
        assertEquals(false, field.execute(reader));
    }

    @Test
    @DisplayName("Test invalid input")
    public void testInvalidInput() {
        Mockito.when(reader.nextLine()).thenReturn("invalid").thenReturn("t");
        var field = BoolField.builder().build();
        assertEquals(true, field.execute(reader));
    }

    @Test
    @DisplayName("Test with no default value")
    public void testWithNoDefaultValue() {
        Mockito.when(reader.nextLine()).thenReturn("");
        var field = BoolField.builder().build();
        assertEquals(true, field.execute(reader));
    }

    @Test
    @DisplayName("Test with default value")
    public void testWithDefaultValue() {
        Mockito.when(reader.nextLine()).thenReturn("");
        var field = BoolField.builder().defaultValue(false).build();
        assertEquals(false, field.execute(reader));
    }
}
