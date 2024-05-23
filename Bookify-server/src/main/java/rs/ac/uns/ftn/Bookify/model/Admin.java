package rs.ac.uns.ftn.Bookify.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import rs.ac.uns.ftn.Bookify.enumerations.Privilege;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    public static List<Privilege> getPrivilege(){
        List<Privilege> privileges = new ArrayList<>();
        privileges.add(Privilege.MANAGE_ACCOMMODATION_REQUEST);
        privileges.add(Privilege.MANAGE_REVIEWS);
        privileges.add(Privilege.MANAGE_USERS);
        privileges.add(Privilege.MANAGE_ACCOUNT);
        return privileges;
    }
}
