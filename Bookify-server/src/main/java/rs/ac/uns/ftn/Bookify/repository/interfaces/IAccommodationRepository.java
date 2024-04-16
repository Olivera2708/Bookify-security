package rs.ac.uns.ftn.Bookify.repository.interfaces;
import jakarta.persistence.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.Bookify.enumerations.AccommodationStatusRequest;
import rs.ac.uns.ftn.Bookify.model.*;
import java.time.LocalDate;
import java.util.Collection;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IAccommodationRepository extends JpaRepository<Accommodation, Long> {
    @Query("SELECT a FROM Accommodation a " +
            "JOIN a.address ad " +
            "JOIN a.availability av " +
            "WHERE a.maxGuest >= :persons " +
            "AND a.minGuest <= :persons " +
            "AND a.status = 'APPROVED'" +
            "AND (av.startDate <= :begin " +
            "AND av.endDate >= :begin " +
            "AND av.startDate <= :end " +
            "AND av.endDate >= :end) " +
            "AND (LOWER(ad.city) LIKE LOWER(CONCAT('%', :location, '%')) " +
            "OR LOWER(ad.address) LIKE LOWER(CONCAT('%', :location, '%')) " +
            "OR LOWER(a.name) LIKE LOWER(CONCAT('%', :location, '%')) " +
            "OR LOWER(ad.country) LIKE LOWER(CONCAT('%', :location, '%'))) ")
    Collection<Accommodation> findByLocationAndGuestRange(
            @Param("location") String location,
            @Param("persons") int persons,
            @Param("begin") LocalDate begin,
            @Param("end") LocalDate end);

    @Query("select a.priceList from Accommodation a join a.priceList where a.id=?1")
    public Collection<PricelistItem> getPriceListItems(Long accommodationId);

    @Query("select pl from Accommodation a " +
            "join a.priceList pl " +
            "where a.id = :accommodationId " +
            "and ((pl.startDate <= :startDate " +
            "and pl.endDate >= :startDate) " +
            "or (pl.startDate <= :endDate " +
            "and pl.endDate >= :endDate) " +
            "or (pl.startDate >= :startDate " +
            "and pl.endDate <= :endDate))")
    public Collection<PricelistItem> getPriceListItemsOverlapsWith(
            @Param("accommodationId") Long accommodationId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("select av from Accommodation a " +
            "join a.availability av " +
            "where a.id = :accommodationId " +
            "and ((av.startDate <= :startDate " +
            "and av.endDate >= :startDate) " +
            "or (av.startDate <= :endDate " +
            "and av.endDate >= :endDate) " +
            "or (av.startDate >= :startDate " +
            "and av.endDate <= :endDate))")
    public Collection<Availability> getAvailabilityItemsOverlapsWith(
            @Param("accommodationId") Long accommodationId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Query("select a.availability from Accommodation a  join a.availability where a.id=?1")
    public Collection<Availability> getAvailabilities(Long accommodationId);

    @Query("SELECT COUNT(a) FROM Accommodation a " +
            "JOIN a.address ad " +
            "JOIN a.availability av " +
            "WHERE a.maxGuest >= :persons " +
            "AND a.minGuest <= :persons " +
            "AND av.startDate <= :begin " +
            "AND av.endDate >= :begin " +
            "AND av.startDate <= :end " +
            "AND av.endDate >= :end " +
            "AND a.status = 'APPROVED'" +
            "AND (LOWER(ad.city) LIKE LOWER(CONCAT('%', :location, '%')) " +
            "OR LOWER(ad.address) LIKE LOWER(CONCAT('%', :location, '%')) " +
            "OR LOWER(a.name) LIKE LOWER(CONCAT('%', :location, '%')) " +
            "OR LOWER(ad.country) LIKE LOWER(CONCAT('%', :location, '%'))) ")
    long countByLocationAndGuestRange(
            @Param("location") String location,
            @Param("persons") int persons,
            @Param("begin") LocalDate begin,
            @Param("end") LocalDate end);

    @Query("SELECT p.price " +
            "FROM Accommodation a " +
            "JOIN a.priceList p " +
            "WHERE p.startDate <= :date AND p.endDate >= :date " +
            "AND a.id = :accommodationId")
    Optional<Double> findPriceForDay(
            @Param("date") LocalDate date,
            @Param("accommodationId") Long accommodationId);

    @Query("SELECT AVG(r.rate) FROM Accommodation a JOIN a.reviews r WHERE a.id = :accommodationId AND r.accepted=true")
    Float getAverageReviewByAccommodationId(@Param("accommodationId") Long accommodationId);

    @Query("SELECT o.accommodations FROM Owner o WHERE o.id =:ownerId ")
    List<Accommodation> getOwnerAccommodation(Long ownerId);

    @Query("SELECT COUNT(a) FROM Accommodation a " +
            "JOIN a.availability av " +
            "WHERE av.startDate <= :begin " +
            "AND av.endDate >= :begin " +
            "AND av.startDate <= :end " +
            "AND av.endDate >= :end " +
            "AND a.status = 'APPROVED'" +
            "AND a.id = :accommodationId")
    long checkIfAccommodationAvailable(@Param("accommodationId") Long accommodationId,
                                          @Param("begin") LocalDate begin,
                                          @Param("end") LocalDate end);

    @Query("SELECT COUNT(a) FROM Accommodation a " +
            "WHERE a.id = :accommodationId " +
            "AND a.minGuest <= :persons " +
            "AND a.maxGuest >= :persons")
    long checkPersons(@Param("accommodationId") Long accommodationId,
                                       @Param("persons") int persons);

    @Query("SELECT a from Accommodation a join a.images img WHERE img.id = :imageId")
    Accommodation getAccommodationByImageId(Long imageId);

    @Query(value = "SELECT ac.id, ac.name, ac.price_per, r.guest_number, (case when r.start < :begin then :begin else r.start end) as startDate, (case when r.end > :end then :end else r.end end) as endDate " +
            "from users_accommodations a " +
            "join reservations r on a.accommodations_id=r.accommodation_id " +
            "join accommodations ac on ac.id = a.accommodations_id " +
            "where owner_id = :ownerId " +
            "and ((r.start >= :begin and r.end <= :end) " +
            "or (r.start <= :begin and r.end >= :begin and r.end <= :end) " +
            "or (r.end >= :end and r.start >= :begin and r.start <= :end) " +
            "or (r.start <= :begin and r.end >= :end)) " +
            "and r.status = 'ACCEPTED' ", nativeQuery = true)
    List<Tuple> getOverallReport(@Param("ownerId") Long ownerId,
                                 @Param("begin") LocalDate begin,
                                 @Param("end") LocalDate end);

    @Query(value = "SELECT a.id, a.name " +
            "from users_accommodations ua " +
            "join accommodations a on a.id = ua.accommodations_id " +
            "where owner_id = :ownerId", nativeQuery = true)
    List<Tuple> getAccommodationNames(@Param("ownerId") Long ownerId);

    @Query(value = "SELECT ac.price_per, r.guest_number, (CASE WHEN r.start < :date THEN :date ELSE r.start END) AS startDate, (CASE WHEN r.end > LAST_DAY(:date) THEN LAST_DAY(:date) ELSE r.end END) AS endDate " +
            "FROM users_accommodations a " +
            "JOIN reservations r ON a.accommodations_id = r.accommodation_id " +
            "JOIN accommodations ac ON ac.id = a.accommodations_id " +
            "WHERE owner_id = :ownerId " +
            "AND ((r.start >= :date AND r.end <= LAST_DAY(:date)) " +
            "OR (r.start <= :date AND r.end >= :date AND r.end <= LAST_DAY(:date)) " +
            "OR (r.end >= LAST_DAY(:date) AND r.start >= :date AND r.start <= LAST_DAY(:date)) " +
            "OR (r.start <= :date AND r.end >= LAST_DAY(:date))) " +
            "AND ac.id = :accommodationId " +
            "AND r.status = 'ACCEPTED' ", nativeQuery = true)
    List<Tuple> getAccommodationReport(@Param("ownerId") Long ownerId,
                                       @Param("accommodationId") Long accommodationId,
                                       @Param("date") LocalDate date);

    @Query(value = "SELECT a.* " +
            "FROM accommodations a " +
            "JOIN reservations r ON a.id = r.accommodation_id " +
            "WHERE r.status = 'ACCEPTED' " +
            "GROUP BY a.id, a.name, a.address " +
            "ORDER BY COUNT(r.id) DESC " +
            "LIMIT :results", nativeQuery = true)
    List<Accommodation> getTopAccommodations(int results);

    Accommodation findByReviewsContains(Review review);
}
