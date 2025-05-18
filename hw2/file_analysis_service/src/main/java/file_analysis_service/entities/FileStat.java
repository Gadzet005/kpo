package file_analysis_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "file_stat_cache")
@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileStat {
    @Id
    @Column(name = "file_id")
    private int fileId;

    @Column(name = "paragraphs")
    private int paragraphs;

    @Column(name = "words")
    private int words;

    @Column(name = "symbols")
    private int symbols;
}
