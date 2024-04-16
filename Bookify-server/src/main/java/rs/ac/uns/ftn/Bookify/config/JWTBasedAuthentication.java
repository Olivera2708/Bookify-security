package rs.ac.uns.ftn.Bookify.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class JWTBasedAuthentication extends AbstractAuthenticationToken {

    private String token;
    private final UserDetails principle;
    public JWTBasedAuthentication(UserDetails principle){
        super(principle.getAuthorities());
        this.principle = principle;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken(){
        return this.token;
    }
    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return principle;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }
}
