package rs.ac.uns.ftn.Bookify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.Bookify.enumerations.CertificateRequestStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateRequestDTO {
    private Long id;
    private Long userId;
    private CertificateRequestStatus status;
    private String appName;
}
