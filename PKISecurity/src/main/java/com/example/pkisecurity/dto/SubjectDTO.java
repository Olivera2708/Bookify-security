package com.example.pkisecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubjectDTO {
    private String name;
    private String email;
    private String country;
    private String organization;
    private String organizationUnit;
}
