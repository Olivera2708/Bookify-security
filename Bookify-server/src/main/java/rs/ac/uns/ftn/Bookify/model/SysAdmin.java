package rs.ac.uns.ftn.Bookify.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@DiscriminatorValue("SYSADMIN")
public class SysAdmin extends User{
}
