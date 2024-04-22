package com.example.pkisecurity.enumerations;

import lombok.Getter;

@Getter
public enum Extension {
    DIGITAL_SIGNATURE("Indicates that the public key can be used for digital signatures. This is commonly used in certificates for signing data or messages."),
    KEY_ENCIPHERMENT("Indicates that the public key can be used for encrypting session keys used in secure communication protocols like TLS/SSL."),
    CERTIFICATE_SIGN("Indicates that the public key can be used to verify signatures on certificates. This extension can be used only in CA certificates."),
    CRL_SIGN("Indicates that the public key can be used to verify signatures on certificate revocation lists (CRLs)."),
    CA("Extension specifies whether a certificate is authorized to sign other certificates within a PKI.");

    private final String description;

    Extension(String description) {
        this.description = description;
    }
}
