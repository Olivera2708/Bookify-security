package rs.ac.uns.ftn.Bookify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.Bookify.enumerations.AccommodationStatusRequest;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Address;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccommodationOwnerDTO {
    private Long id;
    private String name;
    private String accommodationType;
    private float avgRating;
    private AccommodationStatusRequest statusRequest;
    private Address address;
    private Long imageId;

    public AccommodationOwnerDTO(Accommodation accommodation, float avgRating){
        this.id = accommodation.getId();
        this.name = accommodation.getName();
        this.statusRequest = accommodation.getStatus();
        this.accommodationType = accommodation.getAccommodationType().toString();
        this.imageId = accommodation.getImages().iterator().next().getId();
        this.address = accommodation.getAddress();
        this.avgRating = avgRating;
    }

}
