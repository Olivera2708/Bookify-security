package rs.ac.uns.ftn.Bookify.config.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserJWT {
    private String accessToken;
    private Long expiresIn;
}
