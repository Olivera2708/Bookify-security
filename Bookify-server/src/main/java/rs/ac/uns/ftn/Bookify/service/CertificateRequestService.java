package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.enumerations.CertificateRequestStatus;
import rs.ac.uns.ftn.Bookify.model.CertificateRequest;
import rs.ac.uns.ftn.Bookify.repository.interfaces.ICertificateRequestRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.ICertificateRequestService;

import java.util.Collection;
import java.util.List;

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
