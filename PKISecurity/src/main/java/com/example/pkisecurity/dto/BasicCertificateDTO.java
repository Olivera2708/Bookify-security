package com.example.pkisecurity.dto;

import com.example.pkisecurity.enumerations.Extension;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasicCertificateDTO {
    private String issuerEmail;
    private String subjectCertificateAlias;
    private SubjectDTO subject;
    private List<Extension> extensions;
    private Date expires;
}
