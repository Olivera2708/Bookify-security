package rs.ac.uns.ftn.Bookify.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.Bookify.enumerations.AccommodationType;
import rs.ac.uns.ftn.Bookify.enumerations.Filter;
import rs.ac.uns.ftn.Bookify.enumerations.PricePer;
import rs.ac.uns.ftn.Bookify.model.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationDTO {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @Min(0)
    private int minGuest;
    @Min(0)
    private int maxGuest;
    @Min(0)
    private int cancellationDeadline;
    private boolean manual = true;
    private List<Filter> filters;
    @NotNull
    private AccommodationType accommodationType;
    @NotNull
    private PricePer pricePer;
    @NotNull
    private Address address;
}
