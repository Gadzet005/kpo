package file_storing_service.services;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import common_lib.dataobjects.SaveInfo;
import file_storing_service.entities.FileMeta;
import file_storing_service.storages.FileMetaStorage;
import file_storing_service.storages.FileStorage;
import file_storing_service.utils.HashUtils;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileService {
    private FileStorage fileStorage;
    private FileMetaStorage fileMetaStorage;

    @Autowired
    public FileService(FileStorage fileStorage, FileMetaStorage fileMetaStorage) {
        this.fileStorage = fileStorage;
        this.fileMetaStorage = fileMetaStorage;
    }

    public SaveInfo getOrCreateFile(String content) {
        var id = getFileByContent(content);
        if (id != null) {
            return new SaveInfo(id, false);
        }
        id = createFile(content);
        return new SaveInfo(id, true);
    }

    public Integer getFileByContent(String content) {
        var location = fileStorage.findFileByContent(content);
        if (!location.isPresent()) {
            return null;
        }

        var meta = fileMetaStorage.findByLocation(location.get());
        if (!meta.isPresent()) {
            return null;
        }
        return meta.get().getId();
    }

    public Integer createFile(String content) {
        var hash = HashUtils.sha256(content);

        try {
            var location = fileStorage.createFile(content);
            var meta = FileMeta.builder().hash(hash).contentType("text").size(content.length()).location(location)
                    .build();
            meta = fileMetaStorage.saveAndFlush(meta);

            return meta.getId();
        } catch (IOException e) {
            log.error("Error creating file", e);
            return null;
        }
    }

    public Optional<String> getFileContent(Integer fileId) {
        var meta = fileMetaStorage.findById(fileId);
        if (!meta.isPresent()) {
            return Optional.empty();
        }
        return fileStorage.getFileContent(meta.get().getLocation());
    }
}