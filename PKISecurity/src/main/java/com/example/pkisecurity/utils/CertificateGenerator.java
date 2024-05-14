package com.example.pkisecurity.utils;

import com.example.pkisecurity.enumerations.Extension;
import com.example.pkisecurity.model.Issuer;
import com.example.pkisecurity.model.Subject;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import org.bouncycastle.asn1.x509.GeneralName;
import java.util.Date;
import java.util.List;

@Component
public class CertificateGenerator {
    public CertificateGenerator() {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static X509Certificate generateCertificate(Subject subject, Issuer issuer, Date startDate, Date endDate, String serialNumber, List<Extension> extensions, List<String> sanList) {
        try {
            JcaContentSignerBuilder builder = new JcaContentSignerBuilder("SHA256WithRSAEncryption");
            builder = builder.setProvider("BC");

            ContentSigner contentSigner = builder.build(issuer.getPrivateKey());

            X509v3CertificateBuilder certGen = new JcaX509v3CertificateBuilder(issuer.getX500Name(),
                    new BigInteger(serialNumber, 16),
                    startDate,
                    endDate,
                    subject.getX500Name(),
                    subject.getPublicKey());

            // Add SANs only if provided
            if (sanList != null && !sanList.isEmpty()) {
                GeneralNames sanNames = createSANs(sanList);
                certGen.addExtension(org.bouncycastle.asn1.x509.Extension.subjectAlternativeName, false, sanNames);
            }

            // Add other extensions
            if (extensions.contains(Extension.CA)){
                extensions.remove(Extension.CA);
                certGen.addExtension(org.bouncycastle.asn1.x509.Extension.basicConstraints, true, new BasicConstraints(true));
            }
            if(!extensions.isEmpty()){
                int keyUsageFlags = ExtensionUtils.createExtension(extensions.get(0));
                for(int i = 1; i < extensions.size(); i++){
                    keyUsageFlags |= ExtensionUtils.createExtension(extensions.get(i));
                }
                KeyUsage keyUsage = new KeyUsage(keyUsageFlags);
                certGen.addExtension(org.bouncycastle.asn1.x509.Extension.keyUsage, false, keyUsage);
            }

            X509CertificateHolder certHolder = certGen.build(contentSigner);
            JcaX509CertificateConverter certConverter = new JcaX509CertificateConverter();
            certConverter = certConverter.setProvider("BC");

            return certConverter.getCertificate(certHolder);

        } catch (IllegalStateException | OperatorCreationException | IllegalArgumentException | CertificateException e) {
            e.printStackTrace();
        } catch (CertIOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // Helper method to create SANs
    private static GeneralNames createSANs(List<String> sanList) {
        GeneralName[] sanArray = new GeneralName[sanList.size()];
        for (int i = 0; i < sanList.size(); i++) {
            sanArray[i] = new GeneralName(GeneralName.dNSName, sanList.get(i));
        }
        return new GeneralNames(sanArray);
    }
}
