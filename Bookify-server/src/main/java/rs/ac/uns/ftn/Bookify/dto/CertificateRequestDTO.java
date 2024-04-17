package rs.ac.uns.ftn.Bookify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.KeyPair;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateRequestDTO {
    private Long userId;
    private Boolean isCA;
    private KeyPair keyPair;
}
