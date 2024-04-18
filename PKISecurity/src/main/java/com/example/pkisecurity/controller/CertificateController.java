package com.example.pkisecurity.controller;

import com.example.pkisecurity.dto.CertificateDTO;
import com.example.pkisecurity.dto.CertificateRequestDTO;
import com.example.pkisecurity.dto.IssuerDTO;
import com.example.pkisecurity.dto.RootDTO;
import com.example.pkisecurity.mapper.CertificateRequestDTOMapper;
import com.example.pkisecurity.model.CertificateRequest;
import com.example.pkisecurity.service.interfaces.ICertificateRequestService;
import com.example.pkisecurity.service.interfaces.ICertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.KeyPair;
import java.util.Base64;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/certificate")
public class CertificateController {

    @Autowired
    private ICertificateService certificateService;
    @Autowired
    private ICertificateRequestService certificateRequestService;

    @PostMapping("/request/{userId}")
    public ResponseEntity<CertificateRequestDTO> createCertificateRequest(@PathVariable Long userId) {
        CertificateRequest certificateRequest = new CertificateRequest(userId);
        certificateRequestService.save(certificateRequest);
        return new ResponseEntity<>(CertificateRequestDTOMapper.fromCertificateRequestDTO(certificateRequest), HttpStatus.CREATED);
    }

    @GetMapping("/requests")
    public ResponseEntity<Collection<CertificateRequestDTO>> getAllCertificateRequests() {
        Collection<CertificateRequest> certificateRequests = certificateRequestService.getAllRequests();

        Collection<CertificateRequestDTO> certificateRequestsDTO = certificateRequests.stream()
                .map(CertificateRequestDTOMapper::fromCertificateRequestDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(certificateRequestsDTO, HttpStatus.OK);
    }

    @PutMapping("/request/reject/{requestId}")
    public ResponseEntity<CertificateRequestDTO> rejectCertificateRequest(@PathVariable Long requestId) {
        CertificateRequest certificateRequest = certificateRequestService.rejectCertificateRequest(requestId);
        CertificateRequestDTO certificateRequestDTO = CertificateRequestDTOMapper.fromCertificateRequestDTO(certificateRequest);

        return new ResponseEntity<>(certificateRequestDTO, HttpStatus.OK);
    }

    @PutMapping("/request/accept/{requestId}")
    public ResponseEntity<CertificateRequestDTO> acceptCertificateRequest(@PathVariable Long requestId, @RequestBody IssuerDTO issuer) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<CertificateDTO> createCertificate(@RequestBody CertificateDTO certificateDTO){
        certificateService.createCertificate(certificateDTO);
        return new ResponseEntity<>(certificateDTO, HttpStatus.CREATED);
    }
}
