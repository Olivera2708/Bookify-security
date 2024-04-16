package rs.ac.uns.ftn.Bookify.dto;
import lombok.*;
import rs.ac.uns.ftn.Bookify.enumerations.AccommodationType;
import rs.ac.uns.ftn.Bookify.enumerations.PricePer;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Address;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationBasicDTO {
    private Long id;
    private String name;
    private Address address;
    private float avgRating;
    private float totalPrice;
    public PricePer pricePer;
    public float priceOne;
    private Long imageId;
    private AccommodationType type;
    private String description;

}

