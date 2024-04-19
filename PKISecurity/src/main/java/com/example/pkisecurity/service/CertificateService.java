package com.example.pkisecurity.service;

import com.example.pkisecurity.dto.BasicCertificateDTO;
import com.example.pkisecurity.dto.CertificateDTO;
import com.example.pkisecurity.dto.SubjectDTO;
import com.example.pkisecurity.enumerations.Extension;
import com.example.pkisecurity.model.Issuer;
import com.example.pkisecurity.model.Subject;
import com.example.pkisecurity.service.interfaces.ICertificateService;
import com.example.pkisecurity.utils.CertificateGenerator;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.security.*;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.*;

import static com.example.pkisecurity.PkiSecurityApplication.keyStoreReader;
import static com.example.pkisecurity.PkiSecurityApplication.keyStoreWriter;
import static com.example.pkisecurity.repository.json.JSONParserPrivateKey.*;
import static com.example.pkisecurity.repository.json.JSONParserKeyStore.*;


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

    private void saveCertificate(String alias, char[] password, java.security.cert.Certificate certificate, String nextKSName) {
        keyStoreWriter.loadKeyStore(nextKSName, password);
        keyStoreWriter.write(alias, certificate);
        keyStoreWriter.saveKeyStore(nextKSName, password);
        saveKeyStorePassword(alias, nextKSName, new String(password));
    }

    public Date getEndDate(int years) {
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

        Issuer issuer = getNextIssuer(certificateDTO.getIssuerCertificateAlias());

        X509Certificate x509Certificate = CertificateGenerator.generateCertificate(subject,
                issuer,
                certificateDTO.getIssued(),
                certificateDTO.getExpires(),
                UUID.randomUUID().toString().replace("-", ""));

        String nextKSName = getKeyStoreName(certificateDTO.getIssuerCertificateAlias(), subject);
        char[] ksPass = getPassword(nextKSName);

        saveCertificate(x509Certificate.getSerialNumber().toString(), ksPass, x509Certificate, nextKSName);
        saveSubjectPrivateKey(x509Certificate.getSerialNumber().toString(), keyPair.getPrivate());
    }

    private String getKeyStoreName(String issuersAlias, Subject subject) {
        String file = getKSFile(issuersAlias);
        if (file.equals("root.jks")) {
            RDN cnRdn = subject.getX500Name().getRDNs(BCStyle.CN)[0];
            String cn = IETFUtils.valueToString(cnRdn.getFirst().getValue());
            return cn + ".jks";
        }
        return file;
    }

    private char[] getPassword(String filename) {
        if (doesFileExistInJSON(filename))
            return getKSPassByFileName(filename).toCharArray();
        return UUID.randomUUID().toString().replace("-", "").toCharArray();
    }

    @Override
    public X509Certificate getCertificate(String alias) {
        return keyStoreReader.readCertificate(getKSFile(alias), Objects.requireNonNull(getKSPass(alias)), alias);
    }

    @Override
    public Issuer getNextIssuer(String alias) {
        return keyStoreReader.getNextIssuer(getKSFile(alias), Objects.requireNonNull(getKSPass(alias)).toCharArray(), alias);
    }

    @Override
    public Collection<BasicCertificateDTO> getAllCertificates() {
        Map<String, String> allKeyStores = getAllFileNames();
        List<BasicCertificateDTO> certificates = new ArrayList<>();
        for (String fileName : allKeyStores.keySet()) {
            List<X509Certificate> x509certificates = keyStoreReader.readAllCertificates(fileName, allKeyStores.get(fileName));
            for (X509Certificate certificate : x509certificates) {
                try {
                    X500Name issuer = (new JcaX509CertificateHolder(certificate)).getIssuer();
                    X500Name subject = (new JcaX509CertificateHolder(certificate)).getSubject();
                    Date expires = (new JcaX509CertificateHolder(certificate)).getNotAfter();

                    RDN issuerEmailRDN = issuer.getRDNs(BCStyle.E)[0];
                    String issuerEmail = IETFUtils.valueToString(issuerEmailRDN.getFirst().getValue());
                    String subjectCertificateAlias = certificate.getSerialNumber().toString();
                    SubjectDTO subjectDTO = getSubjectDTO(subject);

                    List<Extension> extensions = getExtensions(certificate);

                    certificates.add(new BasicCertificateDTO(issuerEmail, subjectCertificateAlias, subjectDTO, extensions, expires));
                } catch (CertificateEncodingException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return certificates;
    }

    private SubjectDTO getSubjectDTO(X500Name subject){
        RDN subjectNameRDN = subject.getRDNs(BCStyle.CN)[0];
        RDN subjectEmailRDN = subject.getRDNs(BCStyle.E)[0];
        RDN countryRDN = subject.getRDNs(BCStyle.C)[0];
        RDN organizationRDN = subject.getRDNs(BCStyle.O)[0];
        RDN organizationUnitRDN = subject.getRDNs(BCStyle.OU)[0];
        String subjectName = IETFUtils.valueToString(subjectNameRDN.getFirst().getValue());
        String subjectEmail = IETFUtils.valueToString(subjectEmailRDN.getFirst().getValue());
        String country = IETFUtils.valueToString(countryRDN.getFirst().getValue());
        String organization = IETFUtils.valueToString(organizationRDN.getFirst().getValue());
        String organizationUnit = IETFUtils.valueToString(organizationUnitRDN.getFirst().getValue());

        return new SubjectDTO(subjectName, subjectEmail, country, organization, organizationUnit);
    }

    private List<Extension> getExtensions(X509Certificate certificate) {
        List<Extension> extensions = new ArrayList<>();
        boolean[] keyUsage = certificate.getKeyUsage();
        if (keyUsage != null) {
            if (keyUsage[0]) extensions.add(Extension.DIGITAL_SIGNATURE);
            if (keyUsage[2]) extensions.add(Extension.KEY_ENCIPHERMENT);
            if (keyUsage[5]) extensions.add(Extension.CERTIFICATE_SIGN);
            if (keyUsage[6]) extensions.add(Extension.CRL_SIGN);
        }
        int basicConstraints = certificate.getBasicConstraints();
        if (basicConstraints != -1)
            extensions.add(Extension.CA);

        return extensions;
    }
}
