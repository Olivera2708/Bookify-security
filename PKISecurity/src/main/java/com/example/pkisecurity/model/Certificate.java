package com.example.pkisecurity.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.security.cert.X509Certificate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class Certificate {

    private Subject subject;
    private Issuer issuer;
    private String serialNumber;
    private Date issued;
    private Date expires;

    private X509Certificate x509Certificate;
}