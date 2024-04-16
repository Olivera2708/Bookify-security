package rs.ac.uns.ftn.Bookify.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.Bookify.dto.AccommodationInsertDTO;
import rs.ac.uns.ftn.Bookify.dto.UserRegisteredDTO;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.Owner;
import rs.ac.uns.ftn.Bookify.model.User;

@Component
public class UserRegisteredDTOMapper {
    private static ModelMapper modelMapper;

    public UserRegisteredDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static Owner fromDTOtoOwner(UserRegisteredDTO dto) {
        return modelMapper.map(dto, Owner.class);
    }

    public static Guest fromDTOtoGuest(UserRegisteredDTO dto) {
        return modelMapper.map(dto, Guest.class);
    }

    public static UserRegisteredDTO fromUserDTO(User dto) {
        return modelMapper.map(dto, UserRegisteredDTO.class);
    }
}
