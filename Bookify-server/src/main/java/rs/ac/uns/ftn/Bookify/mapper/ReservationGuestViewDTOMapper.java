package rs.ac.uns.ftn.Bookify.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import rs.ac.uns.ftn.Bookify.dto.ReservationGuestViewDTO;
import rs.ac.uns.ftn.Bookify.model.Reservation;

import java.time.format.DateTimeFormatter;

@Component
public class ReservationGuestViewDTOMapper {
    private static ModelMapper modelMapper;

    public ReservationGuestViewDTOMapper(ModelMapper modelMapper) {this.modelMapper = modelMapper;}

    public static ReservationGuestViewDTO toReservationGuestViewDTO(Reservation reservation){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy.");
        ReservationGuestViewDTO r = modelMapper.map(reservation, ReservationGuestViewDTO.class);
        r.setStart(reservation.getStart().format(formatter));
        r.setEnd(reservation.getEnd().format(formatter));
        r.setCancellationDate(reservation.getStart()
                .minusDays(reservation.getAccommodation().getCancellationDeadline()).format(formatter));
        if (!reservation.getAccommodation().getImages().isEmpty())
            r.setImageId(reservation.getAccommodation().getImages().iterator().next().getId());
        return r;
    }

}
