package com.example.pkisecurity.dto;

import com.example.pkisecurity.enumerations.Extension;
import com.example.pkisecurity.model.Subject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CertificateDTO {
    private IssuerDTO issuerDTO;
    private Subject subject;
    private List<Extension> extensions;
    private Date issued;
    private Date expires;
}
