package com.example.pkisecurity.controller;

import com.example.pkisecurity.dto.*;
import com.example.pkisecurity.enumerations.Extension;
import com.example.pkisecurity.mapper.CertificateRequestDTOMapper;
import com.example.pkisecurity.model.CertificateRequest;
import com.example.pkisecurity.model.TransactionResponse;
import com.example.pkisecurity.service.interfaces.ICertificateRequestService;
import com.example.pkisecurity.service.interfaces.ICertificateService;
import org.bouncycastle.jcajce.provider.asymmetric.X509;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.cert.CRLReason;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "https://localhost:4200")
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

    @GetMapping("/request")
    public ResponseEntity<TransactionResponse> getCertificate(@RequestParam String email) throws CertificateEncodingException {
        TransactionResponse response = certificateService.getTransactionResponse(email);
        return new ResponseEntity<>(response, HttpStatus.OK);
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
    public ResponseEntity<CertificateRequestDTO> acceptCertificateRequest(@PathVariable Long requestId) {
        CertificateRequest certificateRequest = certificateRequestService.acceptCertificateRequest(requestId);
        CertificateRequestDTO certificateRequestDTO = CertificateRequestDTOMapper.fromCertificateRequestDTO(certificateRequest);

        return new ResponseEntity<>(certificateRequestDTO, HttpStatus.OK);
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

        if(!certificateService.doesValidCertificateExistForCertificateSubject(serialNumber)){
            certificateService.removeCertificateFromCRL(CA, serialNumber);
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/exists/{email}")
    public ResponseEntity<Boolean> doesValidCertificateExistForEmail(@PathVariable("email") String email) {
        return new ResponseEntity<>(certificateService.doesValidCertificateExistForEmail(email), HttpStatus.OK);
    }


    @GetMapping("/verify")
    public ResponseEntity<Boolean> activate(@RequestParam("alias") String alias) {
        return new ResponseEntity<>(certificateService.verifyCertificate(alias), HttpStatus.OK);
    }

    @PostMapping("/https")
    public ResponseEntity<Boolean> createCertificate() {
        String issuerCertificateAlias = "238458583729625732815324261166972421788";
        SubjectDTO subject = new SubjectDTO("HTTPS", "bookify@example.com", "RS", "Bookify", "Bookify secure");
        Date issued = new Date();
        Date expires = new Date(issued.getTime() + 365 * 24 * 60 * 60 * 1000 * 4);
        List<Extension> extensions = new ArrayList<>();
        extensions.add(Extension.KEY_ENCIPHERMENT);
        extensions.add(Extension.DIGITAL_SIGNATURE);
        CertificateDTO certificateDTO = new CertificateDTO(issuerCertificateAlias, subject, extensions, issued, expires);
        certificateService.createCertificate(certificateDTO);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
