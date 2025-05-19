package file_storing_service.storages;

import file_storing_service.utils.HashUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;
import java.util.UUID;

@Slf4j
public class FileStorage {
    private final Path storageLocation;

    public FileStorage(String storagePath) throws IOException {
        this.storageLocation = Paths.get(storagePath);
        Files.createDirectories(storageLocation);
        log.info("Хранилище файлов инициализировано по пути: {}", storageLocation.toAbsolutePath());
    }

    public String createFile(String content) throws IOException {
        String hash = HashUtils.sha256(content);
        String location = newFile(hash);
        var filePath = getFilePath(location);

        Files.writeString(filePath, content, StandardOpenOption.WRITE);
        log.info("Файл сохранен: hash={}, путь={}", hash, location);

        return location;
    }

    public Optional<String> findFileByContent(String content) {
        String hash = HashUtils.sha256(content);
        Path hashDir = storageLocation.resolve(hash);

        if (!Files.exists(hashDir)) {
            return Optional.empty();
        }

        try {
            var files = Files.list(hashDir).filter(Files::isRegularFile).map(Path::getFileName).toList();
            for (var file : files) {
                var filePath = hashDir.resolve(file);
                var fileContent = Files.readString(filePath);
                if (fileContent.equals(content)) {
                    String relativePath = hashDir.getFileName() + "/" + file.getFileName();
                    return Optional.of(relativePath);
                }
            }
        } catch (IOException e) {
            log.error("Ошибка при поиске файла", e);
        }

        return Optional.empty();
    }

    public Optional<String> getFileContent(String location) {
        Path filePath = getFilePath(location);
        if (Files.exists(filePath)) {
            try {
                return Optional.of(Files.readString(filePath));
            } catch (IOException e) {
                log.error("Ошибка при чтении файла", e);
            }
        }
        return Optional.empty();
    }

    private String newFile(String hash) throws IOException {
        Path hashDir = storageLocation.resolve(hash);
        if (!Files.exists(hashDir)) {
            Files.createDirectories(hashDir);
        }

        while (true) {
            String location = hash + "/" + UUID.randomUUID().toString();
            Path filePath = getFilePath(location);

            try {
                Files.createFile(filePath);
                return location;
            } catch (java.nio.file.FileAlreadyExistsException e) {
                // Ignore and try again
            }
        }
    }

    private Path getFilePath(String location) {
        return storageLocation.resolve(location);
    }
}