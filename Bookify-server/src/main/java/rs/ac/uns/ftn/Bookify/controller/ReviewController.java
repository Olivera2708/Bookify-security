package rs.ac.uns.ftn.Bookify.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.Bookify.dto.*;
import rs.ac.uns.ftn.Bookify.enumerations.ReviewType;
import rs.ac.uns.ftn.Bookify.mapper.NewReviewDTOMapper;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.mapper.ReviewAdminViewDTOMapper;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.Owner;
import rs.ac.uns.ftn.Bookify.model.Review;
import rs.ac.uns.ftn.Bookify.service.interfaces.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    @Autowired
    private IReviewService reviewService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IReservationService reservationService;

    @Autowired
    private INotificationService notificationService;

    @Autowired
    private IAccommodationService accommodationService;

    @GetMapping(value = "/created", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('MANAGE_REVIEWS')")
    public ResponseEntity<Collection<ReviewAdminViewDTO>> getAllCreatedReviews(){
        //returns all created reviews
        List<ReviewAdminViewDTO> response = new ArrayList<>();
        this.reviewService.getCreatedReviews().forEach(review -> {
            response.add(ReviewAdminViewDTOMapper.toReviewDTO(review));
        });
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/reported", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('MANAGE_REVIEWS')")
    public ResponseEntity<Collection<ReviewAdminViewDTO>> getAllReportedReviews(){
        //returns all reported reviews
        List<ReviewAdminViewDTO> response = new ArrayList<>();
        this.reviewService.getReportedReviews().forEach(review -> {
            response.add(ReviewAdminViewDTOMapper.toReviewDTO(review));
        });
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping(value = "/accommodation/{accommodationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<CommentDTO>> getReviewsForAccommodation(@PathVariable Long accommodationId) {
        //returns reviews for accommodation
        Collection<CommentDTO> dtos = reviewService.getAccommodationComments(accommodationId);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/owner/{ownerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<CommentDTO>> getReviewsForOwner(@PathVariable Long ownerId) {
        //returns reviews for owner
        Collection<CommentDTO> dtos = reviewService.getOwnerComments(ownerId);
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @GetMapping(value = "/owner/{ownerId}/rating", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RatingDTO> getOwnerRating(@PathVariable Long ownerId) {
        RatingDTO dto = reviewService.getOwnerRating(ownerId);
        return new ResponseEntity<RatingDTO>(dto, HttpStatus.OK);
    }

    @GetMapping(value = "/accommodation/{accommodationId}/rating", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RatingDTO> getAccommodationRating(@PathVariable Long accommodationId) {
        RatingDTO dto = reviewService.getAccommodationRating(accommodationId);
        return new ResponseEntity<RatingDTO>(dto, HttpStatus.OK);
    }

    @PostMapping(value = "/new-accommodation/{accommodationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('WRITE_REVIEW_FOR_ACCOMMODATION')")
    public ResponseEntity<NewReviewDTO> newReviewAccommodation(@PathVariable Long accommodationId, @Valid @RequestBody NewReviewDTO newReview) {
        //insert new review for accommodation

        Review review = NewReviewDTOMapper.fromDTOtoReview(newReview);
        Guest guest = userService.getGuest(newReview.getGuestId());
        review.setGuest(guest);
        review.setReviewType(ReviewType.ACCOMMODATION);
        if (reservationService.getReservationsForAccommodationInLast7Days(guest.getId(), accommodationId).isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Review insertedReview = reviewService.save(review);
        Accommodation accommodation = accommodationService.getAccommodation(accommodationId);
        accommodation.getReviews().add(insertedReview);
        accommodationService.saveAccommodation(accommodation);
        notificationService.createNotificationOwnerAccommodationGotNewRating(guest, accommodation);
        return new ResponseEntity<>(newReview, HttpStatus.OK);
    }

    @PostMapping(value = "/new-owner/{ownerId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('WRITE_REVIEW_FOR_OWNER')")
    public ResponseEntity<NewReviewDTO> newReviewOwner(@PathVariable Long ownerId, @Valid @RequestBody NewReviewDTO newReview) {
        //insert new review for owner

        Review review = NewReviewDTOMapper.fromDTOtoReview(newReview);
        Guest guest = userService.getGuest(newReview.getGuestId());
        review.setGuest(guest);
        review.setReviewType(ReviewType.OWNER);
        if (reservationService.getReservations(guest.getId(), ownerId).isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Review insertedReview = reviewService.save(review);
        Owner owner = userService.getOwner(ownerId);
        owner.getReviews().add(insertedReview);
        userService.saveOwner(owner);
        notificationService.createNotificationOwnerGotNewRating(guest, owner);
        return new ResponseEntity<>(newReview, HttpStatus.OK);
    }

    @PutMapping(value="/decline/{reviewId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('MANAGE_REVIEWS')")
    public ResponseEntity<ReviewAdminViewDTO> declineReview(@PathVariable Long reviewId) {
        //change to rejected
        Review review = reviewService.getReview(reviewId);
        if(review.getReviewType().equals(ReviewType.ACCOMMODATION)){
            accommodationService.removeReview(review);
        } else {
            userService.removeOwnerReview(review);
        }
        review = reviewService.declineReview(reviewId);
        ReviewAdminViewDTO response = ReviewAdminViewDTOMapper.toReviewDTO(review);
        return new ResponseEntity<ReviewAdminViewDTO>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/accept/{reviewId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('MANAGE_REVIEWS')")
    public ResponseEntity<ReviewAdminViewDTO> acceptReview(@PathVariable Long reviewId) {
        //change to accepted
        Review review = reviewService.acceptReview(reviewId);
        ReviewAdminViewDTO response = ReviewAdminViewDTOMapper.toReviewDTO(review);
        return new ResponseEntity<ReviewAdminViewDTO>(response, HttpStatus.OK);
    }
    @PutMapping(value="/ignore/{reviewId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('MANAGE_REVIEWS')")
    public ResponseEntity<ReviewAdminViewDTO> ignoreReview(@PathVariable Long reviewId) {
        //change to accepted
        Review review = reviewService.ignoreReview(reviewId);
        ReviewAdminViewDTO response = ReviewAdminViewDTOMapper.toReviewDTO(review);
        return new ResponseEntity<ReviewAdminViewDTO>(response, HttpStatus.OK);
    }
    @DeleteMapping(value="/remove/{reviewId}")
    @PreAuthorize("hasAuthority('MANAGE_REVIEWS')")
    public ResponseEntity<ReviewAdminViewDTO> removeReview(@PathVariable Long reviewId) {
        //change to accepted
        Review review = reviewService.getReview(reviewId);
        if(review.getReviewType().equals(ReviewType.ACCOMMODATION)){
            accommodationService.removeReview(review);
        } else {
            userService.removeOwnerReview(review);
        }
        review = reviewService.removeReview(reviewId);
        ReviewAdminViewDTO response = ReviewAdminViewDTOMapper.toReviewDTO(review);
        return new ResponseEntity<ReviewAdminViewDTO>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/report/{reviewId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('REPORT_REVIEW')")
    public ResponseEntity<Long> reportReview(@PathVariable Long reviewId) {
        //change to report
        reviewService.reportReview(reviewId);
        return new ResponseEntity<Long>(reviewId, HttpStatus.OK);
    }

    @DeleteMapping("/accommodation-delete/{accommodationId}/{reviewId}")
    @PreAuthorize("hasAuthority('DELETE_REVIEW')")
    public ResponseEntity<ReviewDTO> deleteAccommodationReview(@PathVariable Long reviewId, @PathVariable Long accommodationId) {
        //delete review for accommodation
        Review review = reviewService.getReview(reviewId);
        Accommodation accommodation = accommodationService.getAccommodation(accommodationId);
        accommodation.getReviews().remove(review);
//        accommodation.setDeleted(false);
        accommodationService.saveAccommodation(accommodation);
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<ReviewDTO>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/owner-delete/{ownerId}/{reviewId}")
    @PreAuthorize("hasAuthority('DELETE_REVIEW')")
    public ResponseEntity<ReviewDTO> deleteOwnerReview(@PathVariable Long reviewId, @PathVariable Long ownerId) {
        //delete review for owner
        Review review = reviewService.getReview(reviewId);
        Owner owner = userService.getOwner(ownerId);
        owner.getReviews().remove(review);
//        owner.setDeleted(false);
        userService.saveOwner(owner);
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<ReviewDTO>(HttpStatus.NO_CONTENT);
    }
}
