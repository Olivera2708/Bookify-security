package rs.ac.uns.ftn.Bookify.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.Bookify.dto.AccommodationDTO;
import rs.ac.uns.ftn.Bookify.model.Accommodation;

@Component
public class AccommodationDTOMapper {
    private static ModelMapper modelMapper;

    public AccommodationDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static Accommodation fromDTOtoAccommodation(AccommodationDTO dto) {
        return modelMapper.map(dto, Accommodation.class);
    }

    public static AccommodationDTO fromAccommodationtoDTO(Accommodation dto) {
        return modelMapper.map(dto, AccommodationDTO.class);
    }
}
