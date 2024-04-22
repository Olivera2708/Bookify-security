package com.example.pkisecurity.model;

import lombok.*;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Certificate {

    private Subject subject;
    private Issuer issuer;
    private String serialNumber;
    private Date issued;
    private Date expires;

    private X509Certificate x509Certificate;

    public Certificate(Subject subject, Issuer issuer, Date issued, Date expires, X509Certificate x509Certificate) {
        this.subject = subject;
        this.issuer = issuer;
        this.serialNumber = UUID.randomUUID().toString().replace("-", "");
        this.issued = issued;
        this.expires = expires;
        this.x509Certificate = x509Certificate;
    }
}