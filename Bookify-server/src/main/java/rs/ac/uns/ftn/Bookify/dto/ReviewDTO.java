package rs.ac.uns.ftn.Bookify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.Bookify.enumerations.ReviewType;
import rs.ac.uns.ftn.Bookify.model.Guest;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    private Long id;
    private int rate;
    private String comment;
    private Date date;
    private boolean accepted;
    private boolean reported;
    private Long guestId;
    private ReviewType reviewType;
}
