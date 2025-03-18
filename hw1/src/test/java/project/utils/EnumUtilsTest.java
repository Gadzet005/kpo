package project.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EnumUtilsTest {
    enum e1 {
        A, B, C
    }

    enum e2 {
    }

    @Test
    @DisplayName("EnumUtils.getNames")
    void testGetNames() {
        var names = EnumUtils.getNames(e1.class);
        assertEquals(3, names.length);
        assertEquals("A", names[0]);
        assertEquals("B", names[1]);
        assertEquals("C", names[2]);
    }

    @Test
    @DisplayName("Test EnumUtils.getNames with empty enum")
    void testGetNamesEmptyEnum() {
        var names = EnumUtils.getNames(e2.class);
        assertEquals(0, names.length);
    }
}
