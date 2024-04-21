package com.example.pkisecurity.service;

import ch.qos.logback.core.CoreConstants;
import com.example.pkisecurity.dto.BasicCertificateDTO;
import com.example.pkisecurity.dto.CertificateDTO;
import com.example.pkisecurity.dto.SubjectDTO;
import com.example.pkisecurity.enumerations.Extension;
import com.example.pkisecurity.model.Certificate;
import com.example.pkisecurity.model.Issuer;
import com.example.pkisecurity.model.Subject;
import com.example.pkisecurity.service.interfaces.ICertificateService;
import com.example.pkisecurity.utils.CertificateGenerator;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.asn1.x509.ExtensionsGenerator;
import org.bouncycastle.cert.X509CRLEntryHolder;
import org.bouncycastle.cert.X509CRLHolder;
import org.bouncycastle.cert.X509v2CRLBuilder;
import org.bouncycastle.cert.jcajce.*;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.*;
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
                UUID.randomUUID().toString().replace("-", ""),
                certificateDTO.getExtensions());

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

    @Override
    public Boolean verifyCertificate(String alias) {
        X509Certificate certificate = getCertificate(alias);
        if (certificate.getNotAfter().before(new Date()) || isCertificateRevoked(alias)) {
            return false;
        }
        if (alias.equals("root")) return true;
        try {
            String issuerAlias = getIssuerAlias(certificate);
            verifySignature(issuerAlias, certificate);
            return verifyCertificate(issuerAlias);
        } catch (CertificateException | NoSuchAlgorithmException | SignatureException | InvalidKeyException |
                 NoSuchProviderException e) {
            return false;
        }
    }

    private String getIssuerAlias(X509Certificate certificate) throws CertificateEncodingException {
        X500Name issuer = (new JcaX509CertificateHolder(certificate)).getIssuer();
        RDN issuerEmailRDN = issuer.getRDNs(BCStyle.E)[0];
        String issuerEmail = IETFUtils.valueToString(issuerEmailRDN.getFirst().getValue());
        String issuerAlias = getAliasForEmail(issuerEmail);
        return issuerAlias;
    }

    private void verifySignature(String issuerAlias, X509Certificate certificate) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, SignatureException {
        X509Certificate issuerCertificate = getCertificate(issuerAlias);
        certificate.verify(issuerCertificate.getPublicKey());
    }

    private String getAliasForEmail(String email) {
        for (BasicCertificateDTO certificate : getAllCertificates()) {
            if (certificate.getSubject().getEmail().equals(email)) {
                if (certificate.getIssuerEmail().equals(certificate.getSubject().getEmail())) {
                    return "root";
                }
                return certificate.getSubjectCertificateAlias();
            }
        }
        return null;
    }

    @Override
    public void revokeCertificate(String CAalias, String revokingSerialNumber, String reason) {
        X509Certificate CACertificate = getCertificate(CAalias);
        X509Certificate revokingCertificate = getCertificate(revokingSerialNumber);

        PrivateKey pk = getPrivateKey(CACertificate.getSerialNumber().toString());

        X509CRL crl;
        try {
            crl = getCRL();
            extendCRL(pk, crl, revokingCertificate);
            return;
        } catch (Exception e) {
            crl = generateCRL(pk, CACertificate, revokingCertificate.getSerialNumber(), convertReason(reason));
        }
        try (FileOutputStream fos = new FileOutputStream("src/main/resources/static/crl.pem")) {
            fos.write(crl.getEncoded());
        } catch (IOException | CRLException e) {
            throw new RuntimeException(e);
        }
    }

    private CRLReason convertReason(String reason) {
        return switch (reason) {
            case "UNSPECIFIED" -> CRLReason.UNSPECIFIED;
            case "KEY_COMPROMISE" -> CRLReason.KEY_COMPROMISE;
            case "CA_COMPROMISE" -> CRLReason.CA_COMPROMISE;
            case "AFFILIATION_CHANGED" -> CRLReason.AFFILIATION_CHANGED;
            case "SUPERSEDED" -> CRLReason.SUPERSEDED;
            case "CESSATION_OF_OPERATION" -> CRLReason.CESSATION_OF_OPERATION;
            case "CERTIFICATE_HOLD" -> CRLReason.CERTIFICATE_HOLD;
            case "REMOVE_FROM_CRL" -> CRLReason.REMOVE_FROM_CRL;
            case "PRIVILEGE_WITHDRAWN" -> CRLReason.PRIVILEGE_WITHDRAWN;
            default -> CRLReason.AA_COMPROMISE;
        };
    }

    private X509CRL generateCRL(PrivateKey caPrivateKey, X509Certificate caCertificate, BigInteger serialNumber, CRLReason reason) {
        Date now = new Date();
        X500Name issuerName = X500Name.getInstance(caCertificate.getIssuerX500Principal().getEncoded());
        X509v2CRLBuilder crlBuilder = new X509v2CRLBuilder(issuerName, now);
        crlBuilder.setNextUpdate(new Date(now.getTime() + 90 * 24 * 60 * 60 * 1000));

        crlBuilder.addCRLEntry(serialNumber, now, reason.ordinal());

        ContentSigner signer = null;
        try {
            signer = new JcaContentSignerBuilder("SHA256withRSA").build(caPrivateKey);

            X509CRLHolder crlHolder = crlBuilder.build(signer);

            return new JcaX509CRLConverter().setProvider("BC").getCRL(crlHolder);
        } catch (CRLException | OperatorCreationException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isCertificateRevoked(String alias) {
        try {
            X509Certificate x509Cert = getCertificate(alias);
            X509CRL crl = getCRL();
            return crl != null && crl.isRevoked(x509Cert);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void removeCertificateFromCRL(String CAalias, String revokingSerialNumber) {
        X509Certificate CACertificate = getCertificate(CAalias);
        X509Certificate removingCertificate = getCertificate(revokingSerialNumber);

        PrivateKey pk = getPrivateKey(CACertificate.getSerialNumber().toString());

        try {
            X509CRL crl = getCRL();
            removeFromCRL(pk, crl, removingCertificate, CACertificate);
        } catch (Exception e) {
        }

    }

    @Override
    public Boolean doesValidCertificateExistForEmail(String email) {
        for (BasicCertificateDTO certificateDTO : getAllCertificates()){
            if (certificateDTO.getSubject().getEmail().equals(email)){
                if(verifyCertificate(certificateDTO.getSubjectCertificateAlias())){

                }
            }
        }
        return null;
    }

    private static X509CRL getCRL() throws IOException, CRLException {
        FileInputStream crlInputStream = null;
        crlInputStream = new FileInputStream("src/main/resources/static/crl.pem");
        X509CRLHolder crlHolder = new X509CRLHolder(crlInputStream);
        JcaX509CRLConverter converter = new JcaX509CRLConverter();
        return converter.getCRL(crlHolder);

    }

    public static void extendCRL(PrivateKey pk, X509CRL existingCRL, X509Certificate newCertificate) throws Exception {
        X509v2CRLBuilder crlBuilder = new JcaX509v2CRLBuilder(existingCRL);
        crlBuilder.addCRLEntry(newCertificate.getSerialNumber(), existingCRL.getThisUpdate(), CRLReason.PRIVILEGE_WITHDRAWN.ordinal());
        ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256withRSA").build(pk);
        X509CRLHolder extendedCRL = crlBuilder.build(contentSigner);
        byte[] extendedCRLBytes = extendedCRL.getEncoded();
        FileOutputStream outputStream = new FileOutputStream("src/main/resources/static/crl.pem");
        outputStream.write(extendedCRLBytes);
        outputStream.close();
    }

    public void removeFromCRL(PrivateKey pk, X509CRL existingCRL, X509Certificate certificateToRemove, X509Certificate caCertificate) throws Exception {
        JcaX509CRLHolder crlHolder = new JcaX509CRLHolder(existingCRL);
        X500Name issuerName = X500Name.getInstance(caCertificate.getIssuerX500Principal().getEncoded());
        X509v2CRLBuilder crlBuilder = new X509v2CRLBuilder(issuerName, new Date());
        List<X509CRLEntryHolder> revokedCertificates = new ArrayList<>();
        for (Object cert : crlHolder.getRevokedCertificates()) {
            X509CRLEntryHolder cert2 = (X509CRLEntryHolder) cert;
            if (!cert2.getSerialNumber().equals(certificateToRemove.getSerialNumber())) {
                revokedCertificates.add(cert2);
            }
        }

        revokedCertificates.forEach(revokedCert -> {
            crlBuilder.addCRLEntry(revokedCert.getSerialNumber(), existingCRL.getThisUpdate(), CRLReason.PRIVILEGE_WITHDRAWN.ordinal());
        });

        ContentSigner contentSigner = new JcaContentSignerBuilder("SHA256withRSA").build(pk);
        X509CRLHolder newCRL = crlBuilder.build(contentSigner);

        FileOutputStream outputStream = new FileOutputStream("src/main/resources/static/crl.pem", false);
        outputStream.write(newCRL.getEncoded());
        outputStream.close();
    }

    private SubjectDTO getSubjectDTO(X500Name subject) {
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
