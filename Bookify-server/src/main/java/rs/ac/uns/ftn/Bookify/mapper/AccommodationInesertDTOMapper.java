package rs.ac.uns.ftn.Bookify.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.Bookify.dto.AccommodationInsertDTO;
import rs.ac.uns.ftn.Bookify.model.Accommodation;

@Component
public class AccommodationInesertDTOMapper {
    private static ModelMapper modelMapper;

    public AccommodationInesertDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static Accommodation fromDTOtoAccommodation(AccommodationInsertDTO dto) {
        return modelMapper.map(dto, Accommodation.class);
    }

    public static AccommodationInsertDTO fromAccommodationtoDTO(Accommodation dto) {
        return modelMapper.map(dto, AccommodationInsertDTO.class);
    }
}
