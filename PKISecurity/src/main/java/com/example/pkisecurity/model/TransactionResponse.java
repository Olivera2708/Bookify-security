package com.example.pkisecurity.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.cert.X509Certificate;

@Data
@NoArgsConstructor
public class TransactionResponse {
    byte[] digitalSignature;
    byte[] certificate;
    byte[] publicKey;
}
