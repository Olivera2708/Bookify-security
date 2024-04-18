package com.example.pkisecurity.service.interfaces;

import com.example.pkisecurity.dto.CertificateDTO;
import org.springframework.stereotype.Service;

import java.security.KeyPair;

@Service
public interface ICertificateService {
    KeyPair generateKeyPair();
    void createCertificate(CertificateDTO certificateDTO);
}
