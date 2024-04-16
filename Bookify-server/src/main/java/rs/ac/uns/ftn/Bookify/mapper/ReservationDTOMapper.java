package rs.ac.uns.ftn.Bookify.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.Bookify.dto.AccommodationBasicDTO;
import rs.ac.uns.ftn.Bookify.dto.ReservationDTO;
import rs.ac.uns.ftn.Bookify.dto.ReservationRequestDTO;
import rs.ac.uns.ftn.Bookify.enumerations.Status;
import rs.ac.uns.ftn.Bookify.model.Reservation;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

@Component
public class ReservationDTOMapper {

    private static ModelMapper modelMapper;

    public ReservationDTOMapper(ModelMapper modelMapper) {this.modelMapper = modelMapper;}

    public static ReservationDTO toReservationDTO(Reservation reservation) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        ReservationDTO r = modelMapper.map(reservation, ReservationDTO.class);
        r.setStart(reservation.getStart().format(formatter));
        r.setEnd(reservation.getEnd().format(formatter));
        if (!reservation.getAccommodation().getImages().isEmpty())
            r.setImageId(reservation.getAccommodation().getImages().iterator().next().getId());
        return r;
    }
}
