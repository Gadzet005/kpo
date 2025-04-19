package project.input;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import project.cli.input.StringField;

class StringFieldTest {
    @Test
    @DisplayName("StringField.validate")
    void testValidate() {
        var field = StringField.builder().build();
        var res1 = field.validate("abc");
        var res2 = field.validate("");
        var res3 = field.validate("        \t abss\n    ");

        assertTrue(res1.isValid());
        assertTrue(res2.isValid());
        assertTrue(res3.isValid());
        assertEquals("abc", res1.result());
        assertEquals("", res2.result());
        assertEquals("abss", res3.result());
    }
}
