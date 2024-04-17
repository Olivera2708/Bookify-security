package rs.ac.uns.ftn.Bookify.service.interfaces;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.model.CertificateRequest;

@Service
public interface ICertificateRequestService {
    void save(CertificateRequest certificateRequest);
}
