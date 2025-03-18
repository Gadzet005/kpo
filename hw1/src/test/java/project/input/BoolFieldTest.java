package project.input;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import project.cli.input.BoolField;

class BoolFieldTest {
    @Test
    @DisplayName("BoolField.validate True")
    void testValidInputs() {
        var field = BoolField.builder().build();

        var res1 = field.validate("t");
        var res2 = field.validate("T");

        assertTrue(res1.isValid());
        assertTrue(res1.result());
        assertTrue(res2.isValid());
        assertTrue(res2.result());
    }

    @Test
    @DisplayName("BoolField.validate False")
    void testInvalidInputs() {
        var field = BoolField.builder().build();

        var res1 = field.validate("f");
        var res2 = field.validate("F");

        assertTrue(res1.isValid());
        assertFalse(res1.result());
        assertTrue(res2.isValid());
        assertFalse(res2.result());
    }

    @Test
    @DisplayName("BoolField.validate Invalid")
    void testInvalidInput() {
        var field = BoolField.builder().build();

        var res = field.validate("invalid");

        assertFalse(res.isValid());
    }
}
