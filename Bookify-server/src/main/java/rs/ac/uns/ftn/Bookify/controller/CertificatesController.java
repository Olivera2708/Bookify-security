package rs.ac.uns.ftn.Bookify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import rs.ac.uns.ftn.Bookify.dto.CertificateRequestDTO;
import rs.ac.uns.ftn.Bookify.dto.TransactionResponse;

import java.net.URISyntaxException;
import java.security.cert.CertificateEncodingException;
import java.util.*;

@RestController
@RequestMapping("/api/v1/certificates")
public class CertificatesController {
    @Autowired
    private RestTemplate restTemplate;
    String url = "https://localhost:8081/api/v1/certificate";

//    @GetMapping("/certificates")
//    public ResponseEntity<Collection<BasicCertificateDTO>> getAllCertificates() {
//        return new ResponseEntity<>(restTemplate.exchange(url, HttpMethod.GET, null,
//                new ParameterizedTypeReference<Collection<BasicCertificateDTO>>() {}).getBody(), HttpStatus.OK);
//    }

    @PostMapping("/request/{appName}/{userId}")
    @PreAuthorize("hasAuthority('CERTIFICATE')")
    public ResponseEntity<CertificateRequestDTO> createCertificateRequest(@PathVariable String appName, @PathVariable Long userId) {
        return new ResponseEntity<>(restTemplate.exchange(url + "/request/" + appName + "/" + userId.toString(), HttpMethod.POST, null,
                new ParameterizedTypeReference<CertificateRequestDTO>() {}).getBody(), HttpStatus.OK);
    }

    @GetMapping("/request")
    @PreAuthorize("hasAuthority('CERTIFICATE')")
    public ResponseEntity<TransactionResponse> getCertificate(@RequestParam String email) throws CertificateEncodingException, URISyntaxException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        Map<String, String> form = new HashMap<>();
        form.put("email", email);
//        HttpEntity<Map<String,String>> request = new HttpEntity<>(form, headers);
        return new ResponseEntity<>(restTemplate.getForObject(url + "/request", TransactionResponse.class, form), HttpStatus.OK);
    }

    @GetMapping("/exists/{email}")
    @PreAuthorize("hasAuthority('CERTIFICATE')")
    public ResponseEntity<Boolean> doesValidCertificateExistForEmail(@PathVariable("email") String email) {
        HashMap<String, String> params = new HashMap<>();
        params.put("email", email);
        return new ResponseEntity<>(restTemplate.getForObject(url + "/exists/{email}", Boolean.class, params), HttpStatus.OK);
//        return new ResponseEntity<>(restTemplate.exchange(url + "/exists/" + email, HttpMethod.GET, null,
//                new ParameterizedTypeReference<Boolean>() {}).getBody(), HttpStatus.OK);
    }
//
//        return new ResponseEntity<>(restTemplate.exchange(url + "/request", HttpMethod.GET, email,
//                new ParameterizedTypeReference<TransactionResponse>() {}).getBody(), HttpStatus.OK);

//    @GetMapping("/requests")
//    public ResponseEntity<Collection<CertificateRequestDTO>> getAllCertificateRequests() {
//        return new ResponseEntity<>(restTemplate.exchange(url + "/requests", HttpMethod.GET, null,
//                new ParameterizedTypeReference<Collection<CertificateRequestDTO>>() {}).getBody(), HttpStatus.OK);
//    }

//    @PutMapping("/request/reject/{requestId}")
//    public ResponseEntity<CertificateRequestDTO> rejectCertificateRequest(@PathVariable Long requestId) {
//        return new ResponseEntity<>(restTemplate.exchange(url + "/request/reject/" + requestId.toString(), HttpMethod.PUT, null,
//                new ParameterizedTypeReference<CertificateRequestDTO>() {}).getBody(), HttpStatus.OK);
//    }
//
//    @PutMapping("/request/accept/{requestId}")
//    public ResponseEntity<CertificateRequestDTO> acceptCertificateRequest(@PathVariable Long requestId) {
//        return new ResponseEntity<>(restTemplate.exchange(url + "/request/accept/" + requestId.toString(), HttpMethod.GET, null,
//                new ParameterizedTypeReference<CertificateRequestDTO>() {}).getBody(), HttpStatus.OK);
//    }

//    @PostMapping("/create")
//    public ResponseEntity<CertificateDTO> createCertificate(@RequestBody CertificateDTO certificateDTO) throws URISyntaxException {
//        URI uri = new URI(url + "/create");
//        return new ResponseEntity<>(restTemplate.postForObject(uri, certificateDTO, CertificateDTO.class), HttpStatus.OK);
//    }

//    @PostMapping("/revoke")
//    public ResponseEntity<CertificateRequestDTO> revokeCertificate(@RequestParam("CA") String CA, @RequestParam("serialNumber") String serialNumber, @RequestParam("reason") String reason) throws URISyntaxException {
//        URI uri = new URI(url + "/create");
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        Map<String, String> form = new HashMap<>();
//        form.put("CA", CA);
//        form.put("serialNumber", serialNumber);
//        form.put("reason", reason);
//        HttpEntity<Map<String,String>> request = new HttpEntity<>(form, headers);
//        return new ResponseEntity<>(restTemplate.postForObject(uri, request, CertificateRequestDTO.class), HttpStatus.OK);
//    }

//    @GetMapping("/checkRevocation")
//    public ResponseEntity<Boolean> isRevoked(@RequestParam("serialNumber") String serialNumber) throws URISyntaxException {
//        URI uri = new URI(url + "/checkRevocation");
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        Map<String, String> form = new HashMap<>();
//        form.put("serialNumber", serialNumber);
//        HttpEntity<Map<String,String>> request = new HttpEntity<>(form, headers);
//        return new ResponseEntity<>(restTemplate.getForObject(uri, Boolean.class, form), HttpStatus.OK);
//
//        return new ResponseEntity<>(restTemplate.exchange(url + "/checkRevocation", HttpMethod.GET, null,
//                new ParameterizedTypeReference<Boolean>() {}).getBody(), HttpStatus.OK);
//    }


}
