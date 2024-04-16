package rs.ac.uns.ftn.Bookify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.core.io.FileSystemResource;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileSystemResourcesDTO {
    private FileSystemResource file;
    private Long id;
}
