package project.input.fields;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import project.cli.input.fields.DoubleField;
import project.utils.CLITest;

public class DoubleFieldetst extends CLITest {

    @Test
    @DisplayName("Test valid input")
    public void testValidInput() {
        Mockito.when(reader.nextLine()).thenReturn("3.14");
        var field = DoubleField.builder().build();
        assertEquals(3.14, field.execute(reader));
    }

    @Test
    @DisplayName("Test invalid input")
    public void testInvalidInput() {
        Mockito.when(reader.nextLine()).thenReturn("invalid").thenReturn("10")
                .thenReturn("-1").thenReturn("0").thenReturn("3.14");
        var field = DoubleField.builder().minValue(3.1).maxValue(3.2).build();
        assertEquals(3.14, field.execute(reader));
    }

    @Test
    @DisplayName("Test with default value")
    public void testDefaultValue() {
        Mockito.when(reader.nextLine()).thenReturn("");
        var field = DoubleField.builder().defaultValue(3.14).build();
        assertEquals(3.14, field.execute(reader));
    }
}
