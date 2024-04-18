package com.example.pkisecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateRequestDTO {
    private Long id;
    private Long userId;
    private Boolean isCA;
    private String publicKey;
    private String privateKey;
}
