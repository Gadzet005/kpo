package project.input;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import project.cli.input.DateField;

class DateFieldTest {
    @Test
    @DisplayName("DateField with valid value")
    void testValid() {
        var testCases = new String[] { "2022-01-01", "2022-02-29",
                "2021-12-31", };

        var field = DateField.builder().build();
        for (var testCase : testCases) {
            var res = field.validate(testCase);

            assertTrue(res.isValid());
        }
    }

    @Test
    @DisplayName("DateField with invalid value")
    void testInvalid() {
        var testCases = new String[] { "abc", "sd2022-01-01abc", "" };

        var field = DateField.builder().build();
        for (var testCase : testCases) {
            var res = field.validate(testCase);

            assertFalse(res.isValid());
        }
    }
}
