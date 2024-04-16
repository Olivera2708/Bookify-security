package rs.ac.uns.ftn.Bookify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.Bookify.enumerations.AccommodationStatusRequest;
import rs.ac.uns.ftn.Bookify.model.Accommodation;
import rs.ac.uns.ftn.Bookify.model.Owner;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccommodationRequestDTO {
    private Long id;
    private String name;
    private UserDTO owner;
    private AccommodationStatusRequest statusRequest;
    private Long imageId;

    public AccommodationRequestDTO(Owner owner, Accommodation accommodation){
        this.id = accommodation.getId();
        this.name = accommodation.getName();
        this.owner = new UserDTO(owner);
        this.statusRequest = accommodation.getStatus();
        this.imageId = accommodation.getImages().iterator().next().getId();
    }
}
