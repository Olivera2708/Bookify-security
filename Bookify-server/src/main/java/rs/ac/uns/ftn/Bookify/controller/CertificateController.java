package rs.ac.uns.ftn.Bookify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.Bookify.dto.CertificateRequestDTO;
import rs.ac.uns.ftn.Bookify.dto.IssuerDTO;
import rs.ac.uns.ftn.Bookify.dto.RootDTO;
import rs.ac.uns.ftn.Bookify.mapper.CertificateRequestDTOMapper;
import rs.ac.uns.ftn.Bookify.model.Certificate;
import rs.ac.uns.ftn.Bookify.model.CertificateRequest;
import rs.ac.uns.ftn.Bookify.model.Issuer;
import rs.ac.uns.ftn.Bookify.model.Subject;
import rs.ac.uns.ftn.Bookify.service.interfaces.ICertificateRequestService;
import rs.ac.uns.ftn.Bookify.service.interfaces.ICertificateService;

import java.security.KeyPair;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/certificate")
public class CertificateController {

    @Autowired
    private ICertificateService certificateService;
    @Autowired
    private ICertificateRequestService certificateRequestService;

    @PostMapping("/request")
//    @PreAuthorize("hasAnyAuthority('ROLE_OWNER','ROLE_ADMIN','ROLE_SYSADMIN')")
    public ResponseEntity<CertificateRequestDTO> createCertificateRequest(@RequestBody CertificateRequestDTO certificateRequestDTO) {
        if (certificateRequestDTO.getPublicKey() == null || certificateRequestDTO.getPrivateKey() == null) {
            KeyPair keyPair = certificateService.generateKeyPair();
            String publicKeyStr = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
            String privateKeyStr = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
            certificateRequestDTO.setPublicKey(publicKeyStr);
            certificateRequestDTO.setPrivateKey(privateKeyStr);
        }

        certificateRequestService.save(CertificateRequestDTOMapper.fromDTOtoCertificateRequest(certificateRequestDTO));
        return new ResponseEntity<>(certificateRequestDTO, HttpStatus.CREATED);
    }

    @GetMapping("/requests")
//    @PreAuthorize("hasAnyAuthority('ROLE_SYSADMIN')")
    public ResponseEntity<Collection<CertificateRequestDTO>> getAllCertificateRequests() {
        Collection<CertificateRequest> certificateRequests = certificateRequestService.getAllRequests();

        Collection<CertificateRequestDTO> certificateRequestsDTO = certificateRequests.stream()
                .map(CertificateRequestDTOMapper::fromCertificateRequestDTO)
                .collect(Collectors.toList());

        return new ResponseEntity<>(certificateRequestsDTO, HttpStatus.OK);
    }

    @PutMapping("/request/reject/{requestId}")
//  @PreAuthorize("hasAnyAuthority('ROLE_SYSADMIN')")
    public ResponseEntity<CertificateRequestDTO> rejectCertificateRequest(@PathVariable Long requestId) {
        CertificateRequest certificateRequest = certificateRequestService.rejectCertificateRequest(requestId);
        CertificateRequestDTO certificateRequestDTO = CertificateRequestDTOMapper.fromCertificateRequestDTO(certificateRequest);

        return new ResponseEntity<>(certificateRequestDTO, HttpStatus.OK);
    }

    @PostMapping("/root")
    public ResponseEntity<CertificateDTO> createRoot(@RequestBody RootDTO rootDTO){
        certificateService.createRoot(rootDTO);
        return new ResponseEntity<CertificateDTO>(HttpStatus.CREATED);
    }

    @GetMapping("/request/possibleIssuers")
//  @PreAuthorize("hasAnyAuthority('ROLE_SYSADMIN')")
    public ResponseEntity<Collection<IssuerDTO>> certificatePossibleIssuers(@PathVariable Long requestId, @RequestBody IssuerDTO issuer) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/request/accept/{requestId}")
//  @PreAuthorize("hasAnyAuthority('ROLE_SYSADMIN')")
    public ResponseEntity<CertificateRequestDTO> acceptCertificateRequest(@PathVariable Long requestId, @RequestBody IssuerDTO issuer) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
