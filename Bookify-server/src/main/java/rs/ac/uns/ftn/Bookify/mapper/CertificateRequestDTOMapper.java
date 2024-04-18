package rs.ac.uns.ftn.Bookify.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.Bookify.dto.AccommodationDTO;
import rs.ac.uns.ftn.Bookify.dto.CertificateRequestDTO;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.CertificateRequest;

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
