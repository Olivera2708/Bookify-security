package rs.ac.uns.ftn.Bookify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.Bookify.enumerations.Status;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private Long id;
    private LocalDate created;
    private String start;
    private String end;
    private int guestNumber;
    private double price;
    private Status status;
    private UserReservationDTO user;
    private Long accommodationId;
    private String accommodationName;
    private double avgRating;
    private Long imageId;
}
