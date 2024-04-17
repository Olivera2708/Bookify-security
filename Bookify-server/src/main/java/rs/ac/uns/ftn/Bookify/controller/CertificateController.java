package rs.ac.uns.ftn.Bookify.controller;

import org.modelmapper.internal.bytebuddy.implementation.auxiliary.AuxiliaryType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.Bookify.dto.CertificateRequestDTO;
import rs.ac.uns.ftn.Bookify.dto.NotificationDTO;
import rs.ac.uns.ftn.Bookify.mapper.CertificateRequestDTOMapper;
import rs.ac.uns.ftn.Bookify.service.interfaces.ICertificateRequestService;
import rs.ac.uns.ftn.Bookify.service.interfaces.ICertificateService;
import rs.ac.uns.ftn.Bookify.service.interfaces.INotificationService;

import java.security.Key;
import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api/v1/certificate")
public class CertificateController {

    @Autowired
    private ICertificateService certificateService;
    @Autowired
    private ICertificateRequestService certificateRequestService;

    @PostMapping("/request-certificate")
    @PreAuthorize("hasAnyAuthority('ROLE_OWNER','ROLE_ADMIN','ROLE_SYSADMIN')")
    public ResponseEntity<CertificateRequestDTO> requestCertificate(@RequestBody CertificateRequestDTO certificateRequestDTO) {
        if (certificateRequestDTO.getKeyPair() == null)
            certificateRequestDTO.setKeyPair(certificateService.generateKeyPair());
        certificateRequestService.save(CertificateRequestDTOMapper.fromDTOtoCertificateRequest(certificateRequestDTO));
        return new ResponseEntity<>(certificateRequestDTO, HttpStatus.OK);
    }

    @GetMapping("/keypair")
    @PreAuthorize("hasAnyAuthority('ROLE_OWNER','ROLE_ADMIN','ROLE_SYSADMIN')")
    public ResponseEntity<KeyPair> generateKeyPair () {
        KeyPair keyPair = certificateService.generateKeyPair();
        return new ResponseEntity<>(keyPair, HttpStatus.OK);
    }
}
