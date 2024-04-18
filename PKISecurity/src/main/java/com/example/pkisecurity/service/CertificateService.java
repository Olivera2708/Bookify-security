package com.example.pkisecurity.service;

import com.example.pkisecurity.dto.RootDTO;
import com.example.pkisecurity.model.Issuer;
import com.example.pkisecurity.model.Subject;
import com.example.pkisecurity.service.interfaces.ICertificateService;
import com.example.pkisecurity.utils.CertificateGenerator;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.cert.X509Certificate;
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

    @Override
    public void createRoot(RootDTO rootDTO) {
        X500Name x500Name = createX500Name(rootDTO);
        KeyPair keyPair = generateKeyPair();
        Subject subject = new Subject(keyPair.getPublic(), x500Name);
        Issuer issuer = new Issuer(keyPair.getPrivate(), keyPair.getPublic(), x500Name);
        X509Certificate x509Certificate = CertificateGenerator.generateCertificate(subject, issuer, new Date(), getEndDate(5), "0");
        saveCertificate(rootDTO.getEmail(), keyPair.getPrivate(), rootDTO.getKeyStorePassword().toCharArray(), x509Certificate);
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
    public X500Name createX500Name(RootDTO rootDTO) {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, rootDTO.getName());
        builder.addRDN(BCStyle.O, rootDTO.getOrganization());
        builder.addRDN(BCStyle.OU, rootDTO.getOrganizationUnit());
        builder.addRDN(BCStyle.C, rootDTO.getCountry());
        builder.addRDN(BCStyle.E, rootDTO.getEmail());

        return builder.build();
    }
}
