package com.example.pkisecurity.service.interfaces;

import com.example.pkisecurity.model.CertificateRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface ICertificateRequestService {
    void save(CertificateRequest certificateRequest);

    Collection<CertificateRequest> getAllRequests();

    CertificateRequest rejectCertificateRequest(Long requestId);

    CertificateRequest acceptCertificateRequest(Long requestId);
}
