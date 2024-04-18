package com.example.pkisecurity.service;

import com.example.pkisecurity.enumerations.CertificateRequestStatus;
import com.example.pkisecurity.model.CertificateRequest;
import com.example.pkisecurity.repository.interfaces.ICertificateRequestRepository;
import com.example.pkisecurity.service.interfaces.ICertificateRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CertificateRequestService implements ICertificateRequestService {

    @Autowired
    ICertificateRequestRepository certificateRequestRepository;

    @Override
    public void save(CertificateRequest certificateRequest) {
        certificateRequestRepository.save(certificateRequest);
    }

    @Override
    public Collection<CertificateRequest> getAllRequests() {
        return certificateRequestRepository.findAll();
    }

    @Override
    public CertificateRequest rejectCertificateRequest(Long requestId) {
        CertificateRequest certificateRequest = certificateRequestRepository.findById(requestId).get();
        certificateRequest.setStatus(CertificateRequestStatus.REJECTED);
        certificateRequestRepository.save(certificateRequest);
        return certificateRequest;
    }
}
