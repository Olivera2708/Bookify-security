package rs.ac.uns.ftn.Bookify.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewReviewDTO {
    @NotEmpty
    private String comment;
    @Min(0)
    @Max(5)
    private int rate;
    @NotNull
    private Long guestId;
}
