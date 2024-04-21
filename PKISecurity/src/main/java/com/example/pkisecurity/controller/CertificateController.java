package com.example.pkisecurity.controller;

import com.example.pkisecurity.dto.*;
import com.example.pkisecurity.mapper.CertificateRequestDTOMapper;
import com.example.pkisecurity.model.CertificateRequest;
import com.example.pkisecurity.service.interfaces.ICertificateRequestService;
import com.example.pkisecurity.service.interfaces.ICertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.cert.CRLReason;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/certificate")
public class CertificateController {

    @Autowired
    private ICertificateService certificateService;
    @Autowired
    private ICertificateRequestService certificateRequestService;

    @PostMapping("/request/{appName}/{userId}")
    public ResponseEntity<CertificateRequestDTO> createCertificateRequest(@PathVariable String appName, @PathVariable Long userId) {
        CertificateRequest certificateRequest = new CertificateRequest(userId, appName);
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

    @GetMapping
    public ResponseEntity<Collection<BasicCertificateDTO>> getAllCertificates() {
        Collection<BasicCertificateDTO> basicCertificates = certificateService.getAllCertificates();
        return new ResponseEntity<>(basicCertificates, HttpStatus.OK);
    }

    @PostMapping("/revoke")
    public ResponseEntity<CertificateRequestDTO> revokeCertificate(@RequestParam("CA") String CA, @RequestParam("serialNumber") String serialNumber, @RequestParam("reason") String reason) {
        certificateService.revokeCertificate(CA, serialNumber, reason);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/checkRevocation")
    public ResponseEntity<Boolean> isRevoked(@RequestParam("serialNumber") String serialNumber) {
        boolean val = certificateService.isCertificateRevoked(serialNumber);
        return new ResponseEntity<>(val, HttpStatus.OK);
    }

    @PutMapping("/restore")
    public ResponseEntity<Boolean> activate(@RequestParam("CA") String CA, @RequestParam("serialNumber") String serialNumber) {
        certificateService.removeCertificateFromCRL(CA, serialNumber);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<Boolean> activate(@RequestParam("alias") String alias) {
        return new ResponseEntity<>(certificateService.verifyCertificate(alias), HttpStatus.OK);
    }

    @GetMapping("/test-read")
    public ResponseEntity readTest(){
//        CertificateDTO certificateDTO123 = new CertificateDTO("root", new SubjectDTO("bookify","bookify.team3@gmail.com", "RS", "BOOKIFY", "BOOKIFY-HEAD"),new ArrayList<>(),new Date(), new Date());
//        certificateService.createCertificate(certificateDTO123);
//        CertificateDTO certificateDTO = new CertificateDTO("137771131808395199342628611820242162821", new SubjectDTO("bookify2","bookify2.team3@gmail.com", "RS", "BOOKIFY", "BOOKIFY-HEAD"),new ArrayList<>(),new Date(), new Date());
//        certificateService.createCertificate(certificateDTO);
        CertificateDTO certificateDTO1 = new CertificateDTO("137771131808395199342628611820242162821", new SubjectDTO("bookify3","bookify3.team3@gmail.com", "RS", "BOOKIFY", "BOOKIFY-HEAD"),new ArrayList<>(),new Date(), new Date());
        certificateService.createCertificate(certificateDTO1);
        CertificateDTO certificateDTO2 = new CertificateDTO("137771131808395199342628611820242162821", new SubjectDTO("bookify4","bookify4.team3@gmail.com", "RS", "BOOKIFY", "BOOKIFY-HEAD"),new ArrayList<>(),new Date(), new Date());
        certificateService.createCertificate(certificateDTO2);
        return new ResponseEntity(HttpStatus.OK);
    }
}
