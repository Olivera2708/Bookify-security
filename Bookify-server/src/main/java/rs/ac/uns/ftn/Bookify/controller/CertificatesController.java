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
        return new ResponseEntity<>(restTemplate.getForObject(url + "/request", TransactionResponse.class, form), HttpStatus.OK);
    }

    @GetMapping("/exists/{email}")
    @PreAuthorize("hasAuthority('CERTIFICATE')")
    public ResponseEntity<Boolean> doesValidCertificateExistForEmail(@PathVariable("email") String email) {
        HashMap<String, String> params = new HashMap<>();
        params.put("email", email);
        return new ResponseEntity<>(restTemplate.getForObject(url + "/exists/{email}", Boolean.class, params), HttpStatus.OK);
    }
}
