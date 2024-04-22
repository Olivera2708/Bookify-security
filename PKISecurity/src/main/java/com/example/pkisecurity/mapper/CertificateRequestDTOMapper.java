package com.example.pkisecurity.mapper;

import com.example.pkisecurity.dto.CertificateRequestDTO;
import com.example.pkisecurity.model.CertificateRequest;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CertificateRequestDTOMapper {
    private static ModelMapper modelMapper;

    public CertificateRequestDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static CertificateRequest fromDTOtoCertificateRequest(CertificateRequestDTO dto) {
        return modelMapper.map(dto, CertificateRequest.class);
    }

    public static CertificateRequestDTO fromCertificateRequestDTO(CertificateRequest request) {
        return modelMapper.map(request, CertificateRequestDTO.class);
    }
}
