package rs.ac.uns.ftn.Bookify.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.Owner;
import rs.ac.uns.ftn.Bookify.model.Review;

import java.util.List;

public interface IReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Owner o join o.reviews r WHERE o.id=:ownerId and r.accepted = true ")
    List<Review> findByOwnerId(@Param("ownerId") Long ownerId);

    @Query("SELECT r FROM Accommodation a join a.reviews r WHERE a.id= :accommodationId and r.accepted = true ")
    List<Review> findByAccommodationId(@Param("accommodationId") Long accommodationId);

    @Query("SELECT g FROM Guest g WHERE g.id=:guestId")
    Guest findGuestById(@Param("guestId") Long guestId);

    List<Review> findByAcceptedIsAndReportedIs(boolean accepted, boolean reported);

}
