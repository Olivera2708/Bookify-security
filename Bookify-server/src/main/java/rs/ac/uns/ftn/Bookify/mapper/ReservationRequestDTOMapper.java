package rs.ac.uns.ftn.Bookify.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.Bookify.dto.ReservationRequestDTO;
import rs.ac.uns.ftn.Bookify.enumerations.Status;
import rs.ac.uns.ftn.Bookify.model.Reservation;

import java.time.LocalDate;
import java.time.ZoneId;

@Component
public class ReservationRequestDTOMapper {

    private static ModelMapper modelMapper;

    public ReservationRequestDTOMapper(ModelMapper modelMapper) {this.modelMapper = modelMapper;}

    public static Reservation fromReservationRequestDTOToReservation(ReservationRequestDTO reservationRequestDTO) {
        Reservation r = modelMapper.map(reservationRequestDTO, Reservation.class);
        r.setStatus(Status.PENDING);
        r.setStart(reservationRequestDTO.getStart().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        r.setEnd(reservationRequestDTO.getEnd().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        r.setCreated(reservationRequestDTO.getCreated().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        return r;
    }
}
