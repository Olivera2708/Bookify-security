package com.example.pkisecurity.service;

import com.example.pkisecurity.dto.CertificateDTO;
import com.example.pkisecurity.service.interfaces.ICertificateService;
import org.springframework.stereotype.Service;

import java.security.*;
import java.util.Calendar;
import java.util.Date;

import static com.example.pkisecurity.PkiSecurityApplication.keyStoreWriter;


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

    private void saveCertificate(String alias, PrivateKey privateKey, char[] password, java.security.cert.Certificate certificate){
        keyStoreWriter.loadKeyStore(password);
        keyStoreWriter.write(alias, privateKey, password, certificate);
        keyStoreWriter.saveKeyStore(password);
    }

    private Date getEndDate(int years){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime();
    }

    @Override
    public void createCertificate(CertificateDTO certificateDTO) {
//        X500Name x500Name = createX500Name(rootDTO);
//        KeyPair keyPair = generateKeyPair();
//        Subject subject = new Subject(keyPair.getPublic(), x500Name);
//        Issuer issuer = new Issuer(keyPair.getPrivate(), keyPair.getPublic(), x500Name);
//        X509Certificate x509Certificate = CertificateGenerator.generateCertificate(subject, issuer, new Date(), getEndDate(5), "0");
//        saveCertificate(rootDTO.getEmail(), keyPair.getPrivate(), rootDTO.getKeyStorePassword().toCharArray(), x509Certificate);
    }
}
