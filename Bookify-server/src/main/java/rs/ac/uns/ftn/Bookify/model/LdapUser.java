package rs.ac.uns.ftn.Bookify.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entry(objectClasses = {"inetOrgPerson"})
public class LdapUser {
    @Id
    private String uid;

    @Attribute(name = "cn")
    private String cn;

    @Attribute(name = "sn")
    private String sn;

    @Attribute(name = "mail")
    private String email;

    @Attribute(name = "givenName")
    private String firstName;

    @Attribute(name = "displayName")
    private String lastName;

    @Attribute(name = "mobile")
    private String phone;

    @Attribute(name = "employeeType")
    private String role;

    public LdapUser(User user){
        setUid(user.getEmail());
        setCn(user.getEmail());
        setSn(user.getEmail());
        setEmail(user.getEmail());
        setFirstName(user.getFirstName());
        setLastName(user.getLastName());
        setPhone(user.getPhone());
    }
}
