package rs.ac.uns.ftn.Bookify.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RootDTO {
    private String name;
    private String email;
    private String country;
    private String organization;
    private String organizationUnit;
    private String keyStorePassword;
}
