package rs.ac.uns.ftn.Bookify.service.interfaces;

import org.springframework.stereotype.Service;

import java.security.KeyPair;

@Service
public interface ICertificateService {
    KeyPair generateKeyPair();

}
