package com.example.pkisecurity.repository.keystores;


import com.example.pkisecurity.model.Issuer;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static com.example.pkisecurity.repository.json.JSONParserPrivateKey.getPrivateKey;

@Component
public class KeyStoreReader {
    private KeyStore keyStore;

    @Value("${SECURITY_PATH}")
    private String path;

    public KeyStoreReader() {
        try {
            keyStore = KeyStore.getInstance("JKS", "SUN");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    public Issuer getNextIssuer(String keyStoreFile, char[] password, String alias) {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(path + keyStoreFile));
            keyStore.load(in, password);

            X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);

            X500Name issuerName = new JcaX509CertificateHolder(cert).getSubject();

            return new Issuer(getPrivateKey(cert.getSerialNumber().toString()), cert.getPublicKey(), issuerName);
        } catch (NoSuchAlgorithmException | CertificateException | KeyStoreException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public X509Certificate readCertificate(String keyStoreFile, String keyStorePass, String alias) {
        try {
            KeyStore ks = KeyStore.getInstance("JKS", "SUN");

            BufferedInputStream in = new BufferedInputStream(new FileInputStream(path + keyStoreFile));
            ks.load(in, keyStorePass.toCharArray());

            if (ks.isCertificateEntry(alias)) {
                return (X509Certificate) ks.getCertificate(alias);
            }
        } catch (KeyStoreException | NoSuchProviderException | NoSuchAlgorithmException | CertificateException |
                 IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<X509Certificate> readAllCertificates(String keyStoreFile, String keyStorePass) {
        List<X509Certificate> certificates = new ArrayList<>();
        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(path + keyStoreFile))) {
                ks.load(in, keyStorePass.toCharArray());
            }

            Enumeration<String> aliases = ks.aliases();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();

                if (ks.isCertificateEntry(alias)) {
                    X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
                    certificates.add(cert);
                }
            }
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
        return certificates;
    }
}