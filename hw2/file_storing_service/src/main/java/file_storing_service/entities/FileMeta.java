package file_storing_service.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "file_meta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FileMeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "size")
    private Integer size;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "hash")
    private String hash;

    @Column(name = "location", unique = true)
    private String location;
}