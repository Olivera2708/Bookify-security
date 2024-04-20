package com.example.pkisecurity.utils;

import com.example.pkisecurity.enumerations.Extension;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;
import org.bouncycastle.asn1.x509.KeyUsage;

import java.io.IOException;

public class ExtensionUtils {

    public static int createExtension(Extension extensionType) {
        return switch (extensionType) {
            case DIGITAL_SIGNATURE -> KeyUsage.digitalSignature;
            case KEY_ENCIPHERMENT -> KeyUsage.keyEncipherment;
            case CERTIFICATE_SIGN -> KeyUsage.keyCertSign;
            case CRL_SIGN -> KeyUsage.cRLSign;
            case CA -> -1;
            default -> throw new IllegalArgumentException("Unsupported extension type");
        };
    }

    private static org.bouncycastle.asn1.x509.Extension createExtension(String oid, byte[] value) {
        ASN1ObjectIdentifier oidIdentifier = new ASN1ObjectIdentifier(oid);
        return new org.bouncycastle.asn1.x509.Extension(oidIdentifier, true, value);
    }

    private static org.bouncycastle.asn1.x509.Extension createBasicConstraintsExtension(boolean isCA) {
        try {
            return new org.bouncycastle.asn1.x509.Extension(org.bouncycastle.asn1.x509.Extension.basicConstraints, true, new BasicConstraints(isCA).getEncoded());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}