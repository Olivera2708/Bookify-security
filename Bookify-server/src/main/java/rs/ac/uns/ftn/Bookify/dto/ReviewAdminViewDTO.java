package rs.ac.uns.ftn.Bookify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.Bookify.enumerations.ReviewType;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewAdminViewDTO {
    private Long id;
    private int rate;
    private String comment;
    private String date;
    private boolean accepted;
    private boolean reported;
    private UserDTO guest;
    private ReviewType reviewType;
}
