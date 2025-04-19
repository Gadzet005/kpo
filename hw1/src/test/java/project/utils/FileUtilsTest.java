package project.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FileUtilsTest {
    @Test
    @DisplayName("FileUtils.getFileName")
    void testGetFileName() {
        var testCases = new Object[][] {
                { "/home/user/documents/example.txt", "example.txt" },
                { "/home/user/documents/example.java", "example.java" },
                { "example.txt", "example.txt" },
                { "example.java", "example.java" },
                { "/example.txt", "example.txt" },
                { "/example.java", "example.java" },
                { "/home/user/documents/example", "example" },
                { "example", "example" }, { "", "" } };

        for (var testCase : testCases) {
            String filePath = (String) testCase[0];
            String expectedFileName = (String) testCase[1];
            assertEquals(expectedFileName, FileUtils.getFileName(filePath),
                    "Input: " + filePath);
        }
    }

    @Test
    @DisplayName("FileUtils.getBaseName")
    void testGetBaseName() {
        var testCases = new Object[][] {
                { "/home/user/documents/example.txt", "example" },
                { "/home/user/documents/example.java", "example" },
                { "example.txt", "example" }, { "example.java", "example" },
                { "/example.txt", "example" }, { "/example.java", "example" },
                { "/home/user/documents/example", "example" },
                { "example", "example" }, { "/.git/file.txt", "file" },
                { "", "" } };

        for (var testCase : testCases) {
            String filePath = (String) testCase[0];
            String expectedBaseName = (String) testCase[1];
            assertEquals(expectedBaseName, FileUtils.getBaseName(filePath),
                    "Input: " + filePath);
        }
    }

    @Test
    @DisplayName("FileUtils.getExtension")
    void testGetExtension() {
        var testCases = new Object[][] {
                { "/home/user/documents/example.txt", "txt" },
                { "/home/user/documents/example.java", "java" },
                { "example.txt", "txt" }, { "example.java", "java" },
                { "/example.txt", "txt" }, { "/example.java", "java" },
                { "/home/user/documents/example", "" }, { "example", "" },
                { "/.git/file.txt", "txt" }, { "", "" } };

        for (var testCase : testCases) {
            String filePath = (String) testCase[0];
            String expectedExtension = (String) testCase[1];
            assertEquals(expectedExtension, FileUtils.getExtension(filePath),
                    "Input: " + filePath);
        }
    }
}
