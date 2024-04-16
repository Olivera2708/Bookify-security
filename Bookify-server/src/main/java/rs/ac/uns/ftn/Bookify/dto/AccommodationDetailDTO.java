package rs.ac.uns.ftn.Bookify.dto;

import lombok.*;
import rs.ac.uns.ftn.Bookify.enumerations.Filter;
import rs.ac.uns.ftn.Bookify.enumerations.PricePer;
import rs.ac.uns.ftn.Bookify.model.Address;
import rs.ac.uns.ftn.Bookify.model.Review;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationDetailDTO {
    private Long id;
    private String name;
    private String description;
    private float avgRating;
    private Collection<Review> reviews;
    private Collection<Filter> filters;
    private Address address;
    private OwnerDTO owner;
    private PricePer pricePer;
}
