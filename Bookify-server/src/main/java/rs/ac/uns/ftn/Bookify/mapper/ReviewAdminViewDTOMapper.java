package rs.ac.uns.ftn.Bookify.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.Bookify.dto.ReviewAdminViewDTO;
import rs.ac.uns.ftn.Bookify.model.Review;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Component
public class ReviewAdminViewDTOMapper {

    private static ModelMapper modelMapper;

    public ReviewAdminViewDTOMapper(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    public static ReviewAdminViewDTO toReviewDTO(Review review){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        ReviewAdminViewDTO r = modelMapper.map(review, ReviewAdminViewDTO.class);
        LocalDate date = LocalDate.ofInstant(review.getDate().toInstant(), ZoneId.systemDefault());
        r.setDate(date.format(formatter));
        return r;
    }

}
