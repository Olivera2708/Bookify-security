package rs.ac.uns.ftn.Bookify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatingDTO {
    private double avgRating;
    private int fiveStars;
    private int fourStars;
    private int threeStars;
    private int twoStars;
    private int oneStars;
}
