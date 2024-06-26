package com.example.pkisecurity.service.interfaces;

import com.example.pkisecurity.dto.BasicCertificateDTO;
import com.example.pkisecurity.dto.CertificateDTO;
import com.example.pkisecurity.dto.SubjectDTO;
import com.example.pkisecurity.model.Certificate;
import com.example.pkisecurity.model.Issuer;
import com.example.pkisecurity.model.TransactionResponse;
import org.bouncycastle.asn1.x500.X500Name;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.cert.CRLReason;
import java.security.cert.X509CRL;
import java.security.cert.X509Certificate;
import java.util.Collection;
import java.util.List;

@Service
public interface ICertificateService {
    KeyPair generateKeyPair();

    X500Name createX500Name(SubjectDTO certificateDTO);

    void createCertificate(CertificateDTO certificateDTO);

    X509Certificate getCertificate(String alias);

    Issuer getNextIssuer(String alias);

    Collection<BasicCertificateDTO> getAllCertificates();

    Boolean verifyCertificate(String alias);

    void revokeCertificate(String CA, String serialNumber, String reason);
    public boolean isCertificateRevoked(String alias);
    public void removeCertificateFromCRL(String CAalias, String revokingSerialNumber);
    Boolean doesValidCertificateExistForEmail(String email);

    boolean doesValidCertificateExistForCertificateSubject(String serialNumber);

    public TransactionResponse getTransactionResponse(String email);
    public void createCertificateHTTPS(CertificateDTO certificateDTO, List<String> sanList);
}
