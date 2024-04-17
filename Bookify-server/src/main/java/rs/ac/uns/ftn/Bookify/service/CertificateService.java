package rs.ac.uns.ftn.Bookify.service;

import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.service.interfaces.ICertificateService;

import java.security.*;

@Service
public class CertificateService implements ICertificateService {
    @Override
    public KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
            keyGen.initialize(2048, random);
            return keyGen.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        return null;
    }
}
