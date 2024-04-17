package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.model.CertificateRequest;
import rs.ac.uns.ftn.Bookify.repository.interfaces.ICertificateRequestRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.ICertificateRequestService;

@Service
public class CertificateRequestService implements ICertificateRequestService {


    @Autowired
    ICertificateRequestRepository certificateRequestRepository;
    @Override
    public void save(CertificateRequest certificateRequest) {
        certificateRequestRepository.save(certificateRequest);
    }
}
