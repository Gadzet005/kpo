package project.utils;

public class FileUtils {
    private FileUtils() {
    }

    public static String getFileName(String filePath) {
        int lastIndex = filePath.lastIndexOf("/");
        if (lastIndex >= 0) {
            return filePath.substring(lastIndex + 1);
        }
        return filePath;
    }

    public static String getBaseName(String filePath) {
        String fileName = getFileName(filePath);
        int lastIndex = fileName.lastIndexOf(".");
        if (lastIndex >= 0) {
            return fileName.substring(0, lastIndex);
        }
        return fileName;
    }

    public static String getExtension(String filePath) {
        String fileName = getFileName(filePath);
        int lastIndex = fileName.lastIndexOf(".");
        if (lastIndex >= 0) {
            return fileName.substring(lastIndex + 1);
        }
        return "";
    }
}
