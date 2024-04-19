package com.example.pkisecurity.repository.keystores;


import com.example.pkisecurity.model.Issuer;
import com.example.pkisecurity.repository.json.JSONParser;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import static com.example.pkisecurity.repository.json.JSONParser.getPrivateKey;

@Component
public class KeyStoreReader {
    //KeyStore je Java klasa za citanje specijalizovanih datoteka koje se koriste za cuvanje kljuceva
    //Tri tipa entiteta koji se obicno nalaze u ovakvim datotekama su:
    // - Sertifikati koji ukljucuju javni kljuc
    // - Privatni kljucevi
    // - Tajni kljucevi, koji se koriste u simetricnima siframa
    private KeyStore keyStore;

    public KeyStoreReader() {
        try {
            keyStore = KeyStore.getInstance("JKS", "SUN");
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    /**
     * Zadatak ove funkcije jeste da ucita podatke o izdavaocu i odgovarajuci privatni kljuc.
     * Ovi podaci se mogu iskoristiti da se novi sertifikati izdaju.
     *
     * @param keyStoreFile - datoteka odakle se citaju podaci
     * @param alias - alias putem kog se identifikuje sertifikat izdavaoca
     * @param password - lozinka koja je neophodna da se otvori key store
     * @param keyPass - lozinka koja je neophodna da se izvuce privatni kljuc
     * @return - podatke o izdavaocu i odgovarajuci privatni kljuc
     */
    public Issuer getNextIssuer(String keyStoreFile, String alias, char[] password, char[] keyPass) {
        try {
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            keyStore.load(in, password);

            X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);

            X500Name issuerName = new JcaX509CertificateHolder(cert).getSubject();

            RDN emailRdn = issuerName.getRDNs(BCStyle.EmailAddress)[0];
            String email = IETFUtils.valueToString(emailRdn.getFirst().getValue());

            return new Issuer(getPrivateKey(email), cert.getPublicKey(), issuerName);
        } catch (NoSuchAlgorithmException | CertificateException | KeyStoreException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }




    /**
     * Ucitava sertifikat is KS fajla
     */
    public X509Certificate readCertificate(String keyStoreFile, String keyStorePass, String alias) {
        try {
            KeyStore ks = KeyStore.getInstance("JKS", "SUN");

            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));
            ks.load(in, keyStorePass.toCharArray());

            if(ks.isCertificateEntry(alias)) {
                return (X509Certificate) ks.getCertificate(alias);
            }
        } catch (KeyStoreException | NoSuchProviderException | NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Reads all certificates from the specified keystore file.
     *
     * @param keyStoreFile the path to the keystore file.
     * @param keyStorePass the password for the keystore.
     * @return a list of X509Certificates contained within the keystore.
     */
    public List<X509Certificate> readAllCertificates(String keyStoreFile, String keyStorePass) {
        List<X509Certificate> certificates = new ArrayList<>();
        try {
            KeyStore ks = KeyStore.getInstance("JKS");
            try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile))) {
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

    /**
     * Ucitava privatni kljuc is KS fajla
     */
    public PrivateKey readPrivateKey(String keyStoreFile, String keyStorePass, String alias, String pass) {
        try {
            KeyStore ks = KeyStore.getInstance("JKS", "SUN");

            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStoreFile));

            ks.load(in, keyStorePass.toCharArray());

            if(ks.isKeyEntry(alias)) {
                PrivateKey pk = (PrivateKey) ks.getKey(alias, pass.toCharArray());
                return pk;
            }
        } catch (NoSuchProviderException | UnrecoverableKeyException | KeyStoreException | CertificateException |
                 IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}