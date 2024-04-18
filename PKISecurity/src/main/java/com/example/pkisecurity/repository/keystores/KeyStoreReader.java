package com.example.pkisecurity.repository.keystores;

import com.example.pkisecurity.model.Issuer;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

@Component
public class KeyStoreReader {
    //KeyStore je Java klasa za citanje specijalizovanih datoteka koje se koriste za cuvanje kljuceva
    //Tri tipa entiteta koji se obicno nalaze u ovakvim datotekama su:
    // - Sertifikati koji ukljucuju javni kljuc
    // - Privatni kljucevi
    // - Tajni kljucevi, koji se koriste u simetricnima siframa
    private KeyStore keyStore;
    private String keyStorePath = "src/main/resources/static/keystore.jks";

    public KeyStoreReader() {
        try {
            keyStore = KeyStore.getInstance("JKS", "SUN");
        } catch (KeyStoreException | NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    public Issuer readIssuerFromStore(String alias, char[] password, char[] keyPass) {
        try {
            //Datoteka se ucitava
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStorePath));
            keyStore.load(in, password);

            //Iscitava se sertifikat koji ima dati alias
            Certificate cert = keyStore.getCertificate(alias);

            //Iscitava se privatni kljuc vezan za javni kljuc koji se nalazi na sertifikatu sa datim aliasom
            PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, keyPass);

            X500Name issuerName = new JcaX509CertificateHolder((X509Certificate) cert).getSubject();
            return new Issuer(privateKey, cert.getPublicKey(), issuerName);
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | UnrecoverableKeyException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Ucitava sertifikat is KS fajla
     */
    public Certificate readCertificate(String keyStorePass, String alias) {
        try {
            //kreiramo instancu KeyStore
            KeyStore ks = KeyStore.getInstance("JKS", "SUN");
            //ucitavamo podatke
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStorePath));
            ks.load(in, keyStorePass.toCharArray());

            if(ks.isKeyEntry(alias)) {
                Certificate cert = ks.getCertificate(alias);
                return cert;
            }
        } catch (KeyStoreException | NoSuchProviderException | NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Ucitava privatni kljuc is KS fajla
     */
    public PrivateKey readPrivateKey(String keyStorePass, String alias, String pass) {
        try {
            //kreiramo instancu KeyStore
            KeyStore ks = KeyStore.getInstance("JKS", "SUN");
            //ucitavamo podatke
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(keyStorePath));
            ks.load(in, keyStorePass.toCharArray());

            if(ks.isKeyEntry(alias)) {
                PrivateKey pk = (PrivateKey) ks.getKey(alias, pass.toCharArray());
                return pk;
            }
        } catch (KeyStoreException | NoSuchProviderException | NoSuchAlgorithmException | CertificateException |
                 IOException | UnrecoverableKeyException e) {
            e.printStackTrace();
        }
        return null;
    }
}
