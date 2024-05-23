package rs.ac.uns.ftn.Bookify.model;

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
@DiscriminatorValue("OWNER")
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id=?")
@Where(clause = "deleted = false")
public class Owner extends User {

    @ElementCollection
    @MapKeyEnumerated(EnumType.STRING)
    @CollectionTable(name = "notification_settings", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "notification_type")
    @Column(name = "is_enabled")
    private Map<NotificationType, Boolean> notificationType;

    @OneToMany
    private List<Accommodation> accommodations;

    @OneToMany
    private List<Review> reviews;

    public static List<Privilege> getPrivilege(){
        List<Privilege> privileges = new ArrayList<>();
        privileges.add(Privilege.VIEW_OWNER_ACCOMMODATIONS);
        privileges.add(Privilege.MANAGE_ACCOMMODATIONS);
        privileges.add(Privilege.VIEW_CHARTS);
        privileges.add(Privilege.MANAGE_NOTIFICATIONS);
        privileges.add(Privilege.MODIFY_OWNER_RESERVATION);
        privileges.add(Privilege.WRITE_REVIEW_FOR_OWNER);
        privileges.add(Privilege.REPORT_REVIEW);
        privileges.add(Privilege.WRITE_REPORT);
        privileges.add(Privilege.MANAGE_ACCOUNT);
        privileges.add(Privilege.VIEW_OWNER_RESERVATION);
        privileges.add(Privilege.CERTIFICATE);
        return privileges;
    }
}