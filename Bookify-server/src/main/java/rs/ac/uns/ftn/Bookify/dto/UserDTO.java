package rs.ac.uns.ftn.Bookify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.Bookify.model.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private boolean blocked;
    private Long imageId;

    public UserDTO(User u) {
        this.id = u.getId();
        this.email = u.getEmail();
        this.firstName = u.getFirstName();
        this.lastName = u.getLastName();
        this.blocked = u.isBlocked();
        if (u.getProfileImage() != null) this.imageId = u.getProfileImage().getId();
    }
}
