package com.example.pkisecurity.model;

import lombok.*;
import org.bouncycastle.asn1.x500.X500Name;

import java.security.PrivateKey;
import java.security.PublicKey;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Issuer {
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private X500Name x500Name;
}
