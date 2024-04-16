package rs.ac.uns.ftn.Bookify.repository.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.Bookify.dto.ReservationDTO;
import rs.ac.uns.ftn.Bookify.enumerations.Status;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.Reservation;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IReservationRepository extends JpaRepository<Reservation, Long> {

    public List<Reservation> findByGuest_IdAndEndAfterAndStatusNotIn(Long guest, LocalDate today, Set<Status> statuses);
    public List<Reservation> findByAccommodation_IdAndEndAfterAndStatusNotIn(Long accommodationId, LocalDate today, Set<Status> statuses);
    @Query("SELECT r FROM Reservation r WHERE r.accommodation.id = :accommodationId " +
            "AND r.start <= :end " +
            "AND r.end >= :start " +
            "AND r.status NOT IN :statuses")
    public List<Reservation> findReservationsByAccommodation_IdAndStartBeforeAndEndAfterAndStatusNotIn(Long accommodationId, LocalDate end, LocalDate start, Set<Status> statuses);

    @Query("SELECT r FROM Reservation r WHERE r.guest.id = :guestId " +
            "AND r.end <= :date " +
            "AND r.status = :status " +
            "AND r.accommodation IN (SELECT o.accommodations FROM Owner o WHERE o.id = :ownerId)")
    public List<Reservation> getReservations(Long guestId, LocalDate date, Status status, Long ownerId);

    @Query("SELECT r FROM Reservation r WHERE r.guest.id = :guestId " +
            "AND r.end >= :startDate " +
            "AND r.end <= :endDate " +
            "AND r.status = :status " +
            "AND r.accommodation.id = :accommodationId")
    public List<Reservation> getReservationsForAccommodationInLast7Days(Long guestId, LocalDate startDate, LocalDate endDate, Status status, Long accommodationId);

    @Query("SELECT r FROM Reservation r WHERE r.guest.id = :guestId")
    public List<Reservation> getAllForGuest(Long guestId);

    @Query("SELECT DISTINCT a.id as id, a.name as name FROM Reservation r JOIN r.accommodation a WHERE r.guest.id = :userId")
    List<Object[]> getIdToNameGuestMap(Long userId);

    @Query("SELECT r FROM Reservation r " +
            "WHERE r.guest.id = :userId " +
            "AND r.accommodation.id = :accommodationId " +
            "AND r.start >= :startDate " +
            "AND r.end <= :endDate AND " +
            "r.status IN :statuses")
    List<Reservation> filterForGuest(Long userId, Long accommodationId, LocalDate startDate, LocalDate endDate, Status[] statuses);

    @Query("SELECT r " +
            "FROM Owner o " +
            "JOIN o.accommodations a "+
            "JOIN Reservation r ON r.accommodation.id = a.id " +
            "WHERE o.id = :ownerId")
    public List<Reservation> getAllForOwner(Long ownerId);


    @Query("SELECT DISTINCT a.id as id, a.name as name " +
            "FROM Owner o " +
            "JOIN o.accommodations a " +
            "JOIN Reservation r ON r.accommodation.id = a.id " +
            "WHERE o.id = :userId")
    List<Object[]> getIdToNameOwnerMap(Long userId);

    @Query("SELECT r " +
            "FROM Owner o " +
            "JOIN o.accommodations a "+
            "JOIN Reservation r ON r.accommodation.id = a.id " +
            "WHERE o.id = :userId " +
            "AND r.accommodation.id = :accommodationId " +
            "AND r.start >= :startDate " +
            "AND r.end <= :endDate AND " +
            "r.status IN :statuses")
    List<Reservation> filterForOwner(Long userId, Long accommodationId, LocalDate startDate, LocalDate endDate, Status[] statuses);

    @Query("SELECT count(r) FROM Reservation r WHERE r.guest.id = :userId AND r.status = 'CANCELED'")
    int getCancellationTimes(Long userId);

    List<Reservation> findAllByGuest_IdAndEndAfter(Long guestId, LocalDate date);
}
