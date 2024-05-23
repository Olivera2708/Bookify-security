package rs.ac.uns.ftn.Bookify.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rs.ac.uns.ftn.Bookify.dto.ReservationDTO;
import rs.ac.uns.ftn.Bookify.dto.ReservationGuestViewDTO;
import rs.ac.uns.ftn.Bookify.dto.ReservationRequestDTO;
import rs.ac.uns.ftn.Bookify.enumerations.Status;
import rs.ac.uns.ftn.Bookify.mapper.ReservationDTOMapper;
import rs.ac.uns.ftn.Bookify.mapper.ReservationGuestViewDTOMapper;
import rs.ac.uns.ftn.Bookify.mapper.ReservationRequestDTOMapper;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.Reservation;
import rs.ac.uns.ftn.Bookify.service.interfaces.IAccommodationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.INotificationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IReservationService;
import rs.ac.uns.ftn.Bookify.service.interfaces.IUserService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/reservations")
public class ReservationController {

    @Autowired
    private IReservationService reservationService;

    @Autowired
    private IAccommodationService accommodationService;

    @Autowired
    private INotificationService notificationService;

    @Autowired
    private IUserService userService;

    @GetMapping(value="/guest", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VIEW_GUEST_RESERVATION')")
    public ResponseEntity<Collection<ReservationDTO>> findReservationsByUserId(@RequestParam Long userId) {
        //return all reservations of one guest
        Collection<Reservation> reservations = reservationService.getAllForGuest(userId);
        Collection<ReservationDTO> reservationDTOS = reservations.stream()
                .map(ReservationDTOMapper::toReservationDTO)
                .collect(Collectors.toList());
        for (ReservationDTO reservation : reservationDTOS){
            reservation.setUser(userService.getOwnerForReservation(reservation.getAccommodationId()));
            reservation.setAvgRating(accommodationService.getAvgRating(reservation.getAccommodationId()));
        }
        return new ResponseEntity<Collection<ReservationDTO>>(reservationDTOS, HttpStatus.OK);
    }

    @GetMapping(value="/guest/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VIEW_GUEST_RESERVATION')")
    public ResponseEntity<Collection<ReservationDTO>> FilterReservationsForGuest(@RequestParam Long userId, @RequestParam Long accommodationId,
                                                                                  @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") Date startDate, @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") Date endDate, @RequestParam Status[] statuses) {
        //return all reservations of one guest
        LocalDate beginL = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endL = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Collection<Reservation> reservations = reservationService.filterForGuest(userId, accommodationId, beginL, endL, statuses);

        Collection<ReservationDTO> reservationDTOS = reservations.stream()
                .map(ReservationDTOMapper::toReservationDTO)
                .collect(Collectors.toList());
        for (ReservationDTO reservation : reservationDTOS){
            reservation.setUser(userService.getOwnerForReservation(reservation.getAccommodationId()));
            reservation.setAvgRating(accommodationService.getAvgRating(reservation.getAccommodationId()));
        }
        return new ResponseEntity<Collection<ReservationDTO>>(reservationDTOS, HttpStatus.OK);
    }

    @GetMapping(value="/accommodations/guest", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VIEW_GUEST_RESERVATION')")
    public ResponseEntity<List<Object[]>> getAccommodationNames(@RequestParam Long userId) {
        //return all accommodation names and ids for guest
        List<Object[]> returns = reservationService.getGuestAccommodations(userId);
        return new ResponseEntity<>(returns, HttpStatus.OK);
    }

    @GetMapping(value="/owner", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VIEW_OWNER_RESERVATION')")
    public ResponseEntity<Collection<ReservationDTO>> findReservationsByOwnerId(@RequestParam Long userId) {
        //return all reservations of one guest
        Collection<Reservation> reservations = reservationService.getAllForOwner(userId);
        Collection<ReservationDTO> reservationDTOS = reservations.stream()
                .map(ReservationDTOMapper::toReservationDTO)
                .collect(Collectors.toList());
        for (ReservationDTO reservation : reservationDTOS){
            reservation.setUser(userService.getGuestForReservation(reservation.getId()));
            reservation.setAvgRating(accommodationService.getAvgRating(reservation.getAccommodationId()));
        }
        return new ResponseEntity<Collection<ReservationDTO>>(reservationDTOS, HttpStatus.OK);
    }

    @GetMapping(value="/owner/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VIEW_OWNER_RESERVATION')")
    public ResponseEntity<Collection<ReservationDTO>> FilterReservationsForOwner(@RequestParam Long userId, @RequestParam Long accommodationId,
                                                                                  @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") Date startDate, @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") Date endDate, @RequestParam Status[] statuses) {
        //return all reservations of one guest
        LocalDate beginL = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endL = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Collection<Reservation> reservations = reservationService.filterForOwner(userId, accommodationId, beginL, endL, statuses);

        Collection<ReservationDTO> reservationDTOS = reservations.stream()
                .map(ReservationDTOMapper::toReservationDTO)
                .collect(Collectors.toList());
        for (ReservationDTO reservation : reservationDTOS){
            reservation.setUser(userService.getGuestForReservation(reservation.getId()));
            reservation.setAvgRating(accommodationService.getAvgRating(reservation.getAccommodationId()));
        }
        return new ResponseEntity<Collection<ReservationDTO>>(reservationDTOS, HttpStatus.OK);
    }

    @GetMapping(value="/accommodations/owner", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VIEW_OWNER_RESERVATION')")
    public ResponseEntity<List<Object[]>> getAccommodationNamesOwner(@RequestParam Long userId) {
        //return all accommodation names and ids for guest
        List<Object[]> returns = reservationService.getOwnerAccommodations(userId);
        return new ResponseEntity<>(returns, HttpStatus.OK);
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('MODIFY_GUEST_RESERVATION')")
    public ResponseEntity<ReservationDTO> insert(@Valid @RequestBody ReservationRequestDTO reservationRequestDTO, @RequestParam Long accommodationId, @RequestParam Long guestId) {
        //insert new reservation request
        Reservation reservation = ReservationRequestDTOMapper.fromReservationRequestDTOToReservation(reservationRequestDTO);
        Reservation ra = reservationService.save(reservation);

        Accommodation accommodation = accommodationService.getAccommodation(accommodationId);
        reservationService.setAccommodation(accommodation, ra);
        Guest guest = (Guest) userService.get(guestId);
        reservationService.setGuest(guest, ra);
        accommodationService.acceptReservationIfAutomaticConformation(ra);
        notificationService.createNotificationOwnerNewReservation(ra);
        return new ResponseEntity<>(new ReservationDTO(), HttpStatus.CREATED);
    }

    @PutMapping(value = "/delete/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('MODIFY_GUEST_RESERVATION')")
    public ResponseEntity<String> cancelReservation(@PathVariable Long reservationId) {
        //change status into canceled
        this.reservationService.delete(reservationId);
        return new ResponseEntity<String>(String.format("Accommodation %d canceled", reservationId), HttpStatus.OK);
    }

    @PutMapping(value = "/accept/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('MODIFY_OWNER_RESERVATION')")
    public ResponseEntity<ReservationDTO> acceptReservation(@PathVariable Long reservationId) {
        //change status into accepted
        Reservation r = reservationService.accept(reservationId);
        accommodationService.acceptReservationForAccommodation(r);
        ReservationDTO reservation = ReservationDTOMapper.toReservationDTO(r);
        reservation.setUser(userService.getGuestForReservation(reservation.getId()));
        reservation.setAvgRating(accommodationService.getAvgRating(reservation.getAccommodationId()));
        return new ResponseEntity<ReservationDTO>(reservation, HttpStatus.OK);
    }

    @PutMapping(value = "/reject/{reservationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('MODIFY_OWNER_RESERVATION')")
    public ResponseEntity<ReservationDTO> rejectReservation(@PathVariable Long reservationId) {
        //change status into rejected
        Reservation r = reservationService.reject(reservationId);
        ReservationDTO reservation = ReservationDTOMapper.toReservationDTO(r);
        reservation.setUser(userService.getGuestForReservation(reservation.getId()));
        reservation.setAvgRating(accommodationService.getAvgRating(reservation.getAccommodationId()));
        notificationService.createNotificationGuestRequestResponse(r);
        return new ResponseEntity<ReservationDTO>(reservation, HttpStatus.OK);
    }

    @GetMapping(value = "/guest/{guestId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('VIEW_GUEST_RESERVATION')")
    public ResponseEntity<Collection<ReservationGuestViewDTO>> getReservationsByGuestId(@PathVariable Long guestId){
        List<ReservationGuestViewDTO> response = new ArrayList<>();
        reservationService.getAllGuestReservations(guestId).forEach(r -> {
            ReservationGuestViewDTO reservation = ReservationGuestViewDTOMapper.toReservationGuestViewDTO(r);
            reservation.setUser(userService.getGuestForReservation(reservation.getId()));
            reservation.setAvgRating(accommodationService.getAvgRating(reservation.getAccommodationId()));
            response.add(reservation);
        });
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping(value = "/cancel/{reservationId}")
    @PreAuthorize("hasAuthority('MODIFY_GUEST_RESERVATION')")
    public ResponseEntity<ReservationGuestViewDTO> cancelReservationGuest(@PathVariable Long reservationId){
        Reservation r = reservationService.cancelReservation(reservationId);
        ReservationGuestViewDTO reservation = ReservationGuestViewDTOMapper.toReservationGuestViewDTO(r);
        reservation.setUser(userService.getGuestForReservation(reservation.getId()));
        reservation.setAvgRating(accommodationService.getAvgRating(reservation.getAccommodationId()));
        notificationService.createNotificationOwnerReservationCancellation(r);
        return new ResponseEntity<>(reservation, HttpStatus.OK);
    }

}
