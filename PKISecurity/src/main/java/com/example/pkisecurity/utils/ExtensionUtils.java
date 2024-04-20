package com.example.pkisecurity.utils;

import com.example.pkisecurity.enumerations.Extension;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x509.BasicConstraints;

import java.io.IOException;

public class ExtensionUtils {

    public static org.bouncycastle.asn1.x509.Extension createExtension(Extension extensionType) {
        return switch (extensionType) {
            case DIGITAL_SIGNATURE -> createExtension("2.5.29.15", new byte[]{0x03, 0x02, 0x05, 0x00});
            case KEY_ENCIPHERMENT -> createExtension("2.5.29.15", new byte[]{0x03, 0x02, 0x04, (byte) 0x80});
            case CERTIFICATE_SIGN -> createExtension("2.5.29.15", new byte[]{0x03, 0x02, 0x06, (byte) 0x80});
            case CRL_SIGN -> createExtension("2.5.29.15", new byte[]{0x03, 0x02, 0x0c, (byte) 0x80});
            case CA -> createBasicConstraintsExtension(true);
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