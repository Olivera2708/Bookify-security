package rs.ac.uns.ftn.Bookify.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequestDTO {
    private Date created;
    @FutureOrPresent
    private Date start;
    @FutureOrPresent
    private Date end;
    @Min(1)
    private int guestNumber;
    @Min(1)
    private double price;
}
