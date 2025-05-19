package file_storing_service.storages;

import file_storing_service.entities.FileMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileMetaStorage extends JpaRepository<FileMeta, Integer> {
    Optional<FileMeta> findByHash(String hash);

    Optional<FileMeta> findByLocation(String location);
}