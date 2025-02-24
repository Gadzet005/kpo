package project.input.fields;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import project.cli.input.fields.ChoiceField;
import project.utils.CLITest;

public class ChoiceFieldTest extends CLITest {
    @Test
    @DisplayName("Test valid input")
    public void testValidInput() {
        Mockito.when(reader.nextLine()).thenReturn("2");
        var choiceField = ChoiceField.builder()
                .choices(new String[] { "field1", "field2" }).build();

        var validationResult = choiceField.execute(reader);

        assertEquals("field2", validationResult);
    }

    @Test
    @DisplayName("Test invalid input")
    public void testInvalidInput() {
        Mockito.when(reader.nextLine()).thenReturn("invalid").thenReturn("10")
                .thenReturn("-1").thenReturn("0").thenReturn("3")
                .thenReturn("1").thenReturn("2");
        var choiceField = ChoiceField.builder()
                .choices(new String[] { "field1", "field2" }).build();

        var validationResult = choiceField.execute(reader);

        assertEquals("field1", validationResult);
    }

    @Test
    @DisplayName("Test with default value")
    public void testDefaultValue() {
        Mockito.when(reader.nextLine()).thenReturn("");
        var choiceField = ChoiceField.builder()
                .choices(new String[] { "field1", "field2" }).build();

        var validationResult = choiceField.execute(reader);

        assertEquals("field1", validationResult);
    }
}
