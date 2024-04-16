package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.dto.CommentDTO;
import rs.ac.uns.ftn.Bookify.dto.RatingDTO;
import rs.ac.uns.ftn.Bookify.exception.BadRequestException;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.Review;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IReviewRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.IReviewService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class ReviewService implements IReviewService {
    private final IReviewRepository reviewRepository;

    @Autowired
    public ReviewService(IReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public RatingDTO getOwnerRating(Long ownerId) {
        List<Review> reviews = reviewRepository.findByOwnerId(ownerId);
        return getRatingDTO(reviews);
    }

    @Override
    public RatingDTO getAccommodationRating(Long accommodationId) {
        List<Review> reviews = reviewRepository.findByAccommodationId(accommodationId);
        return getRatingDTO(reviews);
    }

    private static RatingDTO getRatingDTO(List<Review> reviews) {
        RatingDTO dto = new RatingDTO();
        for (Review review : reviews) {
            switch (review.getRate()) {
                case 1:
                    dto.setOneStars(dto.getOneStars() + 1);
                    break;
                case 2:
                    dto.setTwoStars(dto.getTwoStars() + 1);
                    break;
                case 3:
                    dto.setThreeStars(dto.getThreeStars() + 1);
                    break;
                case 4:
                    dto.setFourStars(dto.getFourStars() + 1);
                    break;
                case 5:
                    dto.setFiveStars(dto.getFiveStars() + 1);
                    break;
            }
        }
        int sum = 5 * dto.getFiveStars() + 4 * dto.getFourStars() + 3 * dto.getThreeStars() + 2 * dto.getTwoStars() + dto.getOneStars();
        int count = dto.getFiveStars() + dto.getFourStars() + dto.getThreeStars() + dto.getTwoStars() + dto.getOneStars();
        if (count != 0)
            dto.setAvgRating(sum / (double) count);
        return dto;
    }

    @Override
    public Collection<CommentDTO> getOwnerComments(Long ownerId) {
        List<Review> reviews = reviewRepository.findByOwnerId(ownerId);
        Collection<CommentDTO> dtos = new ArrayList<>();
        for (Review review : reviews) {
            Guest guest = review.getGuest();
            String name = guest.getFirstName() + " " + guest.getLastName();
            Long imageId = 0L;
            if (guest.getProfileImage() != null) {
                imageId = guest.getProfileImage().getId();
            }
            dtos.add(new CommentDTO(review.getId(), name, review.getDate(), review.getComment(), review.getRate(), guest.getId(), imageId));
        }
        return dtos;
    }

    @Override
    public Collection<CommentDTO> getAccommodationComments(Long accommodationId) {
        List<Review> reviews = reviewRepository.findByAccommodationId(accommodationId);
        Collection<CommentDTO> dtos = new ArrayList<>();
        for (Review review : reviews) {
            Guest guest = review.getGuest();
            String name = guest.getFirstName() + " " + guest.getLastName();
            Long imageId = 0L;
            if (guest.getProfileImage() != null) {
                imageId = guest.getProfileImage().getId();
            }
            dtos.add(new CommentDTO(review.getId(), name, review.getDate(), review.getComment(), review.getRate(), guest.getId(), imageId));
        }
        return dtos;
    }

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public Review getReview(Long id) {
        return reviewRepository.findById(id).get();
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public void reportReview(Long id) {
        Review review = reviewRepository.findById(id).orElse(null);
        if (review == null)
            throw new BadRequestException("Not found");
        review.setReported(true);
        reviewRepository.save(review);
    }

    @Override
    public List<Review> getCreatedReviews() {
        return reviewRepository.findByAcceptedIsAndReportedIs(false, false);

    }

    @Override
    public List<Review> getReportedReviews() {
        return reviewRepository.findByAcceptedIsAndReportedIs(true, true);
    }

    @Override
    public Review acceptReview(Long reviewId) {
        Review review = this.getReview(reviewId);
        review.setAccepted(true);
        reviewRepository.save(review);
        return review;
    }

    @Override
    public Review declineReview(Long reviewId) {
        return removeReview(reviewId);
    }

    @Override
    public Review removeReview(Long reviewId) {
        Review review = this.getReview(reviewId);
        this.reviewRepository.delete(review);
//        this.reviewRepository.deleteById(reviewId);
        return review;
    }

    @Override
    public Review ignoreReview(Long reviewId) {
        Review review = this.getReview(reviewId);
        review.setReported(false);
        this.reviewRepository.save(review);
        return review;
    }
}
