package rs.ac.uns.ftn.Bookify.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransactionResponse {
    byte[] digitalSignature;
    byte[] certificate;
    byte[] publicKey;
}
