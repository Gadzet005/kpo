package file_analysis_service.storages;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class WordCloudCache {
    private final Path cacheLocation;

    public WordCloudCache(String cacheLocation) {
        this.cacheLocation = Path.of(cacheLocation);
    }

    public Optional<byte[]> getWordCloud(int fileId) {
        Path filePath = getWordCloudPath(fileId);
        if (filePath.toFile().exists()) {
            try {
                var content = Files.readAllBytes(filePath);
                return Optional.of(content);
            } catch (IOException e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    public boolean saveWordCloud(int fileId, byte[] content) {
        Path filePath = getWordCloudPath(fileId);
        try {
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, content);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    private Path getWordCloudPath(int fileId) {
        return cacheLocation.resolve("wordcloud_" + fileId + ".png");
    }
}
