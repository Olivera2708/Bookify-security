package rs.ac.uns.ftn.Bookify.service.interfaces;

import rs.ac.uns.ftn.Bookify.dto.CommentDTO;
import rs.ac.uns.ftn.Bookify.dto.RatingDTO;
import rs.ac.uns.ftn.Bookify.model.Review;

import java.util.Collection;
import java.util.List;

public interface IReviewService {
    public RatingDTO getOwnerRating(Long ownerId);
    public RatingDTO getAccommodationRating(Long accommodationId);
    public Collection<CommentDTO> getOwnerComments(Long ownerId);
    public Collection<CommentDTO> getAccommodationComments(Long accommodationId);
    public Review save(Review review);
    public Review getReview(Long id);
    public void deleteReview(Long id);
    public void reportReview(Long id);
    public List<Review> getCreatedReviews();
    public List<Review> getReportedReviews();
    public Review acceptReview(Long reviewId);
    public Review declineReview(Long reviewId);
    public Review removeReview(Long reviewId);
    public Review ignoreReview(Long reviewId);
}
