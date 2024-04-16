package rs.ac.uns.ftn.Bookify.mapper;

import org.modelmapper.ModelMapper;
import rs.ac.uns.ftn.Bookify.dto.NewReviewDTO;
import rs.ac.uns.ftn.Bookify.dto.ReviewDTO;
import rs.ac.uns.ftn.Bookify.enumerations.ReviewType;
import rs.ac.uns.ftn.Bookify.model.Owner;
import rs.ac.uns.ftn.Bookify.model.Review;

import java.util.Date;

public class NewReviewDTOMapper {
    private static ModelMapper modelMapper;

    public NewReviewDTOMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public static Review fromDTOtoReview(NewReviewDTO dto) {
        Review review = new Review();
        review.setComment(dto.getComment());
        review.setRate(dto.getRate());
        review.setReviewType(ReviewType.OWNER);
        review.setDate(new Date());
        review.setAccepted(false);
        review.setReported(false);
        return review;
    }

    public static NewReviewDTO fromReviewtoDTO(Review dto) {
        return modelMapper.map(dto, NewReviewDTO.class);
    }

}
