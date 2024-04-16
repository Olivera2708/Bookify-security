package rs.ac.uns.ftn.Bookify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.Bookify.model.Accommodation;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationsChartDTO {
    private Accommodation accommodation;
    private int numberOfReservations;
    private double profiteOfAccommodation;
}
