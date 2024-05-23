package rs.ac.uns.ftn.Bookify.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import rs.ac.uns.ftn.Bookify.enumerations.NotificationType;
import rs.ac.uns.ftn.Bookify.enumerations.Privilege;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("GUEST")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Guest extends User {
    @ElementCollection
    @CollectionTable(name = "notification_settings", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "notification_type")
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "is_enabled")
    private Map<NotificationType, Boolean> notificationType;

    @JsonIgnore
    @ManyToMany
    private List<Accommodation> favorites;

    public static List<Privilege> getPrivilege(){
        List<Privilege> privileges = new ArrayList<>();
        privileges.add(Privilege.FAVORITES);
        privileges.add(Privilege.VIEW_ACCOMMODATION_PRICE);
        privileges.add(Privilege.MANAGE_NOTIFICATIONS);
        privileges.add(Privilege.VIEW_GUEST_RESERVATION);
        privileges.add(Privilege.MODIFY_GUEST_RESERVATION);
        privileges.add(Privilege.WRITE_REVIEW_FOR_ACCOMMODATION);
        privileges.add(Privilege.WRITE_REVIEW_FOR_OWNER);
        privileges.add(Privilege.DELETE_REVIEW);
        privileges.add(Privilege.MANAGE_ACCOUNT);
        privileges.add(Privilege.WRITE_REPORT);
        return privileges;
    }
}
