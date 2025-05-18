package file_analysis_service.storages;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import file_analysis_service.entities.FileStat;

@Repository
public interface FileStatCache extends JpaRepository<FileStat, Integer> {
}
