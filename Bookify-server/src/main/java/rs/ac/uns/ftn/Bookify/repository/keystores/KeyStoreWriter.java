package rs.ac.uns.ftn.Bookify.repository.keystores;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;


@Component
public class KeyStoreWriter {

    private KeyStore keyStore;
    private String keyStorePath = "src/main/resources/static/keystore.jks";

    public void createNewKeyStore(char[] password) {
        try {
            keyStore.load(null, password);
            saveKeyStore(password);
        } catch (NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
    }

    public KeyStoreWriter() {
        try {
            keyStore = KeyStore.getInstance("JKS", "SUN");
        } catch (KeyStoreException | NoSuchProviderException e) {
            e.printStackTrace();
        }
    }

    public void loadKeyStore(char[] password) {
        try {
            keyStore.load(new FileInputStream(keyStorePath), password);
        } catch (NoSuchAlgorithmException | CertificateException | IOException e) {
            createNewKeyStore(password);
        }
    }

    public void saveKeyStore(char[] password) {
        try {
            keyStore.store(new FileOutputStream(keyStorePath), password);
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String alias, PrivateKey privateKey, char[] password, Certificate certificate) {
        try {
            keyStore.setKeyEntry(alias, privateKey, password, new Certificate[] {certificate});
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }
}
