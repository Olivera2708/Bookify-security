package rs.ac.uns.ftn.Bookify.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rs.ac.uns.ftn.Bookify.model.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    Owner findByAccommodations_Id(Long accommodationId);

    @Query("SELECT o FROM Owner o WHERE o.id=:ownerId")
    Owner findOwnerById(@Param("ownerId") Long ownerId);

    @Query("SELECT g FROM Guest g WHERE g.id=:guestId")
    Guest findGuestById(@Param("guestId") Long guestId);

    @Query("SELECT AVG(r.rate) FROM Owner o JOIN o.reviews r WHERE o.id = :ownerId")
    Float getAverageReviewByOwnerId(@Param("ownerId") Long ownerId);

    User findByEmail(String email);

    @Query("select u from User u where u.active.hashToken = :uuid")
    User findByHashToken(String uuid);

    @Modifying
    @Query("delete from User u where u.id= :userId")
    void deleteUser(Long userId);
    @Query("SELECT o FROM Owner o")
    List<Owner> findAllOwners();

    @Query("SELECT o FROM Owner o JOIN o.accommodations a WHERE a.id = :accommodationId")
    Owner getOwner(Long accommodationId);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = "INSERT INTO users_favorites (guest_id, favorites_id) VALUES (:userId, :accommodationId)")
    void addFavoriteToUser(@Param("userId") Long userId, @Param("accommodationId") Long accommodationId);

    @Query(value = "SELECT COUNT(*) FROM users_favorites WHERE guest_id = :guestId AND favorites_id = :accommodationId", nativeQuery = true)
    int checkIfInFavorites(@Param("guestId") Long guestId, @Param("accommodationId") Long accommodationId);

    @Query("SELECT o FROM Owner o WHERE :review MEMBER OF o.reviews")
    Owner getOwnerByReview(Review review);
}
