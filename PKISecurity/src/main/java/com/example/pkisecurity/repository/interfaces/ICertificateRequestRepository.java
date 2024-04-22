package com.example.pkisecurity.repository.interfaces;

import com.example.pkisecurity.model.CertificateRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICertificateRequestRepository extends JpaRepository<CertificateRequest, Long> {
}
