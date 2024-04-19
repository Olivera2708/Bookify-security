package com.example.pkisecurity.service;

import com.example.pkisecurity.dto.CertificateDTO;
import com.example.pkisecurity.dto.SubjectDTO;
import com.example.pkisecurity.model.Issuer;
import com.example.pkisecurity.model.Subject;
import com.example.pkisecurity.service.interfaces.ICertificateService;
import com.example.pkisecurity.utils.CertificateGenerator;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.Date;

import static com.example.pkisecurity.PkiSecurityApplication.keyStoreReader;
import static com.example.pkisecurity.PkiSecurityApplication.keyStoreWriter;


@Service
public class CertificateService implements ICertificateService {
    private JSONArray keystoreConfigs;
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
    public X500Name createX500Name(SubjectDTO subjectDTO) {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, subjectDTO.getName());
        builder.addRDN(BCStyle.O, subjectDTO.getOrganization());
        builder.addRDN(BCStyle.OU, subjectDTO.getOrganizationUnit());
        builder.addRDN(BCStyle.C, subjectDTO.getCountry());
        builder.addRDN(BCStyle.E, subjectDTO.getEmail());

        return builder.build();
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

        X500Name x500Name = createX500Name(certificateDTO.getSubject());
        KeyPair keyPair = generateKeyPair();
        Subject subject = new Subject(keyPair.getPublic(), x500Name);
        X509Certificate parentCertificate = getCertificate(certificateDTO.getIssuerCertificateAlias());

        Issuer issuer = new Issuer(keyPair.getPrivate(), keyPair.getPublic(), x500Name);
        X509Certificate x509Certificate = CertificateGenerator.generateCertificate(subject, issuer, new Date(), getEndDate(5), "0");

//        saveCertificate(certificateDTO.getEmail(), keyPair.getPrivate(), certificateDTO.getKeyStorePassword().toCharArray(), x509Certificate);
    }

    @Override
    public X509Certificate getCertificate(String alias) {
        return keyStoreReader.readCertificate("src/main/resources/static/"+getKSFile(alias),getKSPass(alias),alias);
    }


    private void setupConfig(){
        String jsonText = null;
        try {
            jsonText = new String(Files.readAllBytes(Paths.get("src/main/resources/static/key-store-password.json")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        keystoreConfigs = new JSONArray(jsonText);
    }

    private String getKSFile(String alias) {
        setupConfig();
        for (int i = 0; i < keystoreConfigs.length(); i++) {
            JSONObject ksConfig = keystoreConfigs.getJSONObject(i);
            JSONArray aliases = ksConfig.getJSONArray("aliases");
            for (int j = 0; j < aliases.length(); j++) {
                if (aliases.getString(j).equals(alias)) {
                    return ksConfig.getString("file-name");
                }
            }
        }
        return null;
    }

    private String getKSPass(String alias) {
        setupConfig();
        for (int i = 0; i < keystoreConfigs.length(); i++) {
            JSONObject ksConfig = keystoreConfigs.getJSONObject(i);
            JSONArray aliases = ksConfig.getJSONArray("aliases");
            for (int j = 0; j < aliases.length(); j++) {
                if (aliases.getString(j).equals(alias)) {
                    return ksConfig.getString("password");
                }
            }
        }
        return null;
    }
}
