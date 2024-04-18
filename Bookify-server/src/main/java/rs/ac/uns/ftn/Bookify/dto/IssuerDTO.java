package rs.ac.uns.ftn.Bookify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bouncycastle.asn1.x500.X500Name;

import java.security.PublicKey;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssuerDTO {
    private PublicKey publicKey;
    private X500Name x500Name;
}
