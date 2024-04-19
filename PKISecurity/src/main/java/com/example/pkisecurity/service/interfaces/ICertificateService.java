package com.example.pkisecurity.service.interfaces;

import com.example.pkisecurity.dto.CertificateDTO;
import com.example.pkisecurity.dto.SubjectDTO;
import com.example.pkisecurity.model.Issuer;
import org.bouncycastle.asn1.x500.X500Name;
import org.springframework.stereotype.Service;

import java.security.Certificate;
import java.security.KeyPair;
import java.security.cert.X509Certificate;

@Service
public interface ICertificateService {
    KeyPair generateKeyPair();

    X500Name createX500Name(SubjectDTO certificateDTO);

    void createCertificate(CertificateDTO certificateDTO);

    X509Certificate getCertificate(String alias);

    Issuer getNextIssuer(String alias);
}
