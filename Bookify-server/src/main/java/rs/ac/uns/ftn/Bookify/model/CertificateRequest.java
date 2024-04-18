package rs.ac.uns.ftn.Bookify.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rs.ac.uns.ftn.Bookify.enumerations.CertificateRequestStatus;

import java.security.KeyPair;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CertificateRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Boolean isCA;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String publicKey;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String privateKey;
    private CertificateRequestStatus status = CertificateRequestStatus.PENDING;
}
