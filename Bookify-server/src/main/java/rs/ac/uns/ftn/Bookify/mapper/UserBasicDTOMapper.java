package rs.ac.uns.ftn.Bookify.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.Bookify.dto.UserBasicDTO;
import rs.ac.uns.ftn.Bookify.model.User;

@Component
public class UserBasicDTOMapper {
    private static ModelMapper modelMapper;

    public UserBasicDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static User fromDTOtoOwner(UserBasicDTO dto) {
        return modelMapper.map(dto, User.class);
    }

    public static UserBasicDTO fromOwnertoDTO(User dto) {
        return modelMapper.map(dto, UserBasicDTO.class);
    }
}
