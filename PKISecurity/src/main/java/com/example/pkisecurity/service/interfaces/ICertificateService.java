package com.example.pkisecurity.service.interfaces;

import com.example.pkisecurity.dto.RootDTO;
import org.bouncycastle.asn1.x500.X500Name;
import org.springframework.stereotype.Service;

import java.security.KeyPair;

@Service
public interface ICertificateService {
    KeyPair generateKeyPair();

    void createRoot(RootDTO rootDTO);
    X500Name createX500Name(RootDTO rootDTO);
}
