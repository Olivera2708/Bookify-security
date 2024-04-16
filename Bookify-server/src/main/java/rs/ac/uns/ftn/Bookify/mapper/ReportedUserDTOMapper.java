package rs.ac.uns.ftn.Bookify.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.Bookify.dto.ReportedUserDTO;
import rs.ac.uns.ftn.Bookify.dto.UserBasicDTO;
import rs.ac.uns.ftn.Bookify.model.ReportedUser;
import rs.ac.uns.ftn.Bookify.model.User;

@Component
public class ReportedUserDTOMapper {
    private static ModelMapper modelMapper;

    public ReportedUserDTOMapper(ModelMapper modelMapper) {
        ReportedUserDTOMapper.modelMapper = modelMapper;
    }

    public static ReportedUser fromDTOtoUser(ReportedUserDTO dto) {
        return modelMapper.map(dto, ReportedUser.class);
    }

    public static ReportedUserDTO fromUsertoDTO(ReportedUser dto) {
        return modelMapper.map(dto, ReportedUserDTO.class);
    }

}
