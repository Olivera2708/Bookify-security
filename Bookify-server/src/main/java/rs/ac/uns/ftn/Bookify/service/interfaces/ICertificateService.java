package rs.ac.uns.ftn.Bookify.service.interfaces;

import org.bouncycastle.asn1.x500.X500Name;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.dto.RootDTO;
import rs.ac.uns.ftn.Bookify.model.CertificateRequest;

import java.security.KeyPair;
import java.util.Collection;

@Service
public interface ICertificateService {
    KeyPair generateKeyPair();

    void createRoot(RootDTO rootDTO);
    X500Name createX500Name(RootDTO rootDTO);
}
