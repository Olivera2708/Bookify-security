package rs.ac.uns.ftn.Bookify.service;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.dto.RootDTO;
import rs.ac.uns.ftn.Bookify.model.Subject;
import rs.ac.uns.ftn.Bookify.service.interfaces.ICertificateService;

import java.security.*;

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
        //sacuvaj privatni u jks
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
