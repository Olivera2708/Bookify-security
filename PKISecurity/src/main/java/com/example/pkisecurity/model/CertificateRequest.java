package com.example.pkisecurity.model;

import com.example.pkisecurity.enumerations.CertificateRequestStatus;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CertificateRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private CertificateRequestStatus status = CertificateRequestStatus.PENDING;

    public CertificateRequest(Long userId){
        this.userId = userId;
    }
}
