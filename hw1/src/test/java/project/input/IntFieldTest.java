package project.input;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import project.cli.input.IntField;

class IntFieldTest {
    @Test
    @DisplayName("IntField.validate with valid value")
    void testValidValue() {
        var testCases = new Object[][] { { "123", 123 }, { "456", 456 },
                { "0", 0 }, { "-123", -123 }, { "1", 1 }, { "-1", -1 },
                { "100000", 100000 }, };

        var field = IntField.builder().build();

        for (var testCase : testCases) {
            var str = (String) testCase[0];
            var expectedResult = (Integer) testCase[1];
            var res = field.validate(str);

            assertTrue(res.isValid());
            assertEquals(expectedResult, res.result());
        }
    }

    @Test
    @DisplayName("IntField.validate with invalid value")
    void testInvalidValue() {
        var testCases = new String[] { "abc", "123abc", "abc123", "123.456",
                "-123.456", "123,456", "-123,456", "0x123", "0b101010" };

        var field = IntField.builder().build();

        for (var str : testCases) {
            var res = field.validate(str);

            assertFalse(res.isValid());
        }
    }

    @Test
    @DisplayName("IntField.validate with (min, max)")
    void testMinMax() {
        var field = IntField.builder().minValue(10).maxValue(100).build();

        var res1 = field.validate("10");
        var res2 = field.validate("100");
        var res3 = field.validate("9");
        var res4 = field.validate("101");

        assertTrue(res1.isValid());
        assertTrue(res2.isValid());
        assertFalse(res3.isValid());
        assertFalse(res4.isValid());
    }
}
