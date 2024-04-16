package rs.ac.uns.ftn.Bookify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.Bookify.dto.ReservationDTO;
import rs.ac.uns.ftn.Bookify.enumerations.Status;
import rs.ac.uns.ftn.Bookify.exception.BadRequestException;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.Reservation;
import rs.ac.uns.ftn.Bookify.model.*;
import rs.ac.uns.ftn.Bookify.repository.interfaces.IReservationRepository;
import rs.ac.uns.ftn.Bookify.service.interfaces.IAccommodationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IReservationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IUserService;

import java.time.LocalDate;
import java.util.*;

@Service
public class ReservationService implements IReservationService {

    IReservationRepository reservationRepository;
    IAccommodationService accommodationService;

    @Autowired
    public ReservationService(IReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    @Autowired
    @Lazy
    public void setAccommodationService(IAccommodationService accommodationService) {
        this.accommodationService = accommodationService;
    }

    @Override
    public Reservation get(Long reservationId) {
        return null;
    }

    @Override
    public boolean hasFutureReservationsGuest(Long guestId) {
        List<Reservation> futureReservations = this.reservationRepository.findByGuest_IdAndEndAfterAndStatusNotIn(guestId, LocalDate.now(), EnumSet.of(Status.CANCELED, Status.REJECTED));
        return !futureReservations.isEmpty();
    }

    @Override
    public boolean hasFutureReservationsAccommodation(Accommodation accommodation) {
        List<Reservation> futureReservations = this.reservationRepository.findByAccommodation_IdAndEndAfterAndStatusNotIn(accommodation.getId(), LocalDate.now(), EnumSet.of(Status.CANCELED, Status.REJECTED));
        return !futureReservations.isEmpty();
    }

    @Override
    public boolean hasReservationInRange(Long accommodationId, LocalDate start, LocalDate end) {
        List<Reservation> reservations = reservationRepository.findReservationsByAccommodation_IdAndStartBeforeAndEndAfterAndStatusNotIn(accommodationId, end, start, EnumSet.of(Status.CANCELED, Status.REJECTED));
        return !reservations.isEmpty();
    }

    @Override
    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Override
    public void setAccommodation(Accommodation accommodation, Reservation reservation) {
        reservation.setAccommodation(accommodation);
        reservationRepository.save(reservation);
    }

    @Override
    public void setGuest(Guest guest, Reservation reservation) {
        reservation.setGuest(guest);
        reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getReservations(Long guestId, Long ownerId) {
        return reservationRepository.getReservations(guestId, LocalDate.now(), Status.ACCEPTED, ownerId);
    }

    @Override
    public List<Reservation> getReservationsForAccommodationInLast7Days(Long guestId, Long accommodationId) {
        return reservationRepository.getReservationsForAccommodationInLast7Days(guestId, LocalDate.now().minusDays(7), LocalDate.now(), Status.ACCEPTED, accommodationId);
    }

    @Override
    public List<Reservation> getAllForGuest(Long userId) {
        return reservationRepository.getAllForGuest(userId);
    }

    @Override
    public List<Object[]> getGuestAccommodations(Long userId) {
        return reservationRepository.getIdToNameGuestMap(userId);
    }

    @Override
    public List<Reservation> filterForGuest(Long userId, Long accommodationId, LocalDate startDate, LocalDate endDate, Status[] statuses) {
        return reservationRepository.filterForGuest(userId, accommodationId, startDate, endDate, statuses);
    }

    @Override
    public List<Reservation> getAllForOwner(Long userId) {
        return reservationRepository.getAllForOwner(userId);
    }

    @Override
    public List<Object[]> getOwnerAccommodations(Long userId) {
        return reservationRepository.getIdToNameOwnerMap(userId);
    }

    @Override
    public List<Reservation> filterForOwner(Long userId, Long accommodationId, LocalDate startDate, LocalDate endDate, Status[] statuses) {
        return reservationRepository.filterForOwner(userId, accommodationId, startDate, endDate, statuses);
    }

    @Override
    public void setReservationStatus(Long reservationId, Status status) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isPresent()) {
            Reservation r = reservation.get();
            r.setStatus(status);
            reservationRepository.save(r);
        }
    }

    @Override
    public void delete(Long reservationId) {
        this.reservationRepository.deleteById(reservationId);
    }

    @Override
    public void rejectOverlappingReservations(Long accommodationId, LocalDate startDate, LocalDate endDate) {
        List<Reservation> overlappingReservations = reservationRepository.findReservationsByAccommodation_IdAndStartBeforeAndEndAfterAndStatusNotIn(
                accommodationId,
                endDate,
                startDate,
                EnumSet.of(Status.CANCELED, Status.ACCEPTED, Status.REJECTED));
        for (Reservation reservation : overlappingReservations) {
            reservation.setStatus(Status.REJECTED);
            reservationRepository.save(reservation);
        }
    }

    @Override
    public Reservation accept(Long reservationId) {
        Reservation reservation = getReservation(reservationId);
        reservation.setStatus(Status.ACCEPTED);
        reservationRepository.save(reservation);
        return reservation;
    }

    @Override
    public Reservation reject(Long reservationId) {
        Reservation reservation = getReservation(reservationId);
        reservation.setStatus(Status.REJECTED);
        reservationRepository.save(reservation);
        return reservation;
    }

    private Reservation getReservation(Long reservationId) {
        Optional<Reservation> r = reservationRepository.findById(reservationId);
        if (r.isEmpty()) throw new BadRequestException("Reservation not found");
        Reservation reservation = r.get();
        if (!canRespondToReservationRequest(reservation))
            throw new BadRequestException("The date to respond to this reservation has expired");
        if (!reservation.getStatus().equals(Status.PENDING))
            throw new BadRequestException("Reservation is not in status 'PENDING'");
        return reservation;
    }

    @Override
    public boolean cancelGuestsReservations(Long guestId) {
        List<Reservation> reservations = this.reservationRepository.findAllByGuest_IdAndEndAfter(guestId, LocalDate.now());
        reservations.forEach(reservation -> {
            if (reservation.getStatus() == Status.ACCEPTED
                    && !(LocalDate.now().isAfter(reservation.getStart()) && LocalDate.now().isBefore(reservation.getEnd()))) {
                Availability availability = new Availability();
                availability.setStartDate(reservation.getStart());
                availability.setEndDate(reservation.getEnd());
                accommodationService.addAvailability(reservation.getAccommodation().getId(), availability);
            }
            reservation.setStatus(Status.CANCELED);
            reservationRepository.save(reservation);
        });
        return true;
    }

    @Override
    public Reservation cancelReservation(Long reservationId) {
        Optional<Reservation> reservation = reservationRepository.findById(reservationId);
        if (reservation.isEmpty()) throw new BadRequestException("Reservation not found");
        Reservation r = reservation.get();
        Accommodation accommodation = r.getAccommodation();
        int cancellationDays = accommodation.getCancellationDeadline();
        LocalDate cancellationDate = r.getStart().minusDays(cancellationDays);
        if (LocalDate.now().isAfter(cancellationDate)) throw new BadRequestException("Cancellation date expired");
        r.setStatus(Status.CANCELED);
        Availability availability = new Availability();
        availability.setStartDate(r.getStart());
        availability.setEndDate(r.getEnd());
        accommodationService.addAvailability(accommodation.getId(), availability);
        reservationRepository.save(r);
        return r;
    }

    @Override
    public List<Reservation> getAllGuestReservations(Long guestId) {
        List<Reservation> reservations = this.reservationRepository.getAllForGuest(guestId);
        reservations.removeIf(r -> !r.getStatus().equals(Status.ACCEPTED));
        return reservations;
    }

    private boolean canRespondToReservationRequest(Reservation reservation) {
        return LocalDate.now().isBefore(reservation.getStart());
    }
}
