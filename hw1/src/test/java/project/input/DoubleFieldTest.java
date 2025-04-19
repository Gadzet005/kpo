package project.input;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import project.cli.input.DoubleField;

class DoubleFieldTest {
    @Test
    @DisplayName("DoubleField.validate with valid value")
    void testValid() {
        var testCases = new Object[][] { { "123.456", 123.456 },
                { "-456.789", -456.789 }, { "0.0", 0.0 },
                { "1.23456e308", 1.23456e308 },
                { "-1.23456e-308", -1.23456e-308 }, };

        var field = DoubleField.builder().build();

        for (var testCase : testCases) {
            var str = (String) testCase[0];
            var expectedResult = (Double) testCase[1];
            var res = field.validate(str);

            assertTrue(res.isValid());
            assertEquals(expectedResult, res.result());
        }
    }

    @Test
    @DisplayName("DoubleField.validate with invalid value")
    void testInvalid() {
        var testCases = new String[] { "abc", "123abc", "abc123", "123.456.789",
                "-123.456.789", "123,456.789", "-123,456.789", "0x123",
                "0b101010" };

        var field = DoubleField.builder().build();

        for (var str : testCases) {
            var res = field.validate(str);

            assertFalse(res.isValid());
        }
    }

    @Test
    @DisplayName("DoubleField.validate with (min, max)")
    void testMinMax() {
        var field = DoubleField.builder().minValue(1.0).maxValue(100.0).build();

        var res1 = field.validate("1.0");
        var res2 = field.validate("100.0");
        var res3 = field.validate("0.9");
        var res4 = field.validate("100.1");

        assertTrue(res1.isValid());
        assertTrue(res2.isValid());
        assertFalse(res3.isValid());
        assertFalse(res4.isValid());
    }
}
