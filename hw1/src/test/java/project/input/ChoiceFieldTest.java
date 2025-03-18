package project.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import project.cli.input.ChoiceField;

class ChoiceFieldTest {
    @Test
    @DisplayName("ChoiceField with valid input")
    void testValidInput() {
        var choices = new String[] { "a", "b", "c" };
        var testCases = new String[][] { { "1", "a" }, { "2", "b" },
                { "3", "c" }, };

        var field = ChoiceField.builder().choices(choices).build();
        for (var testCase : testCases) {
            var expectedResult = testCase[1];
            var res = field.validate(testCase[0]);

            assertTrue(res.isValid());
            assertEquals(expectedResult, res.result());
        }
    }

    @Test
    @DisplayName("ChoiceField with invalid input")
    void testInvalidInput() {
        var choices = new String[] { "a", "b", "c" };
        var field = ChoiceField.builder().choices(choices).build();

        var testCases = new Object[] { "0", "4", "d", "" };

        for (var testCase : testCases) {
            var res = field.validate(testCase.toString());
            assertFalse(res.isValid());
        }
    }
}
