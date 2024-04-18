package com.example.pkisecurity.dto;

import com.example.pkisecurity.enumerations.CertificateRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateRequestDTO {
    private Long userId;
    private CertificateRequestStatus status;
}
