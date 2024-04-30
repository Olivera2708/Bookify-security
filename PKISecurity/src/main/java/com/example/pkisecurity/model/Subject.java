package com.example.pkisecurity.model;

import lombok.*;
import org.bouncycastle.asn1.x500.X500Name;

import java.security.PublicKey;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Subject {
    private PublicKey publicKey;
    private X500Name x500Name;
}
