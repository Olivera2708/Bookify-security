package rs.ac.uns.ftn.Bookify.config;


import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import rs.ac.uns.ftn.Bookify.enumerations.Privilege;
import rs.ac.uns.ftn.Bookify.model.Admin;
import rs.ac.uns.ftn.Bookify.model.Guest;
import rs.ac.uns.ftn.Bookify.model.Owner;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

public class KeycloakJwtAuthenticationConverter implements Converter<Jwt, AbstractAuthenticationToken> {
    @Override
    public AbstractAuthenticationToken convert(@NonNull Jwt source) {
        return new JwtAuthenticationToken(
                source,
                Stream.concat(new JwtGrantedAuthoritiesConverter().convert(source).stream(),
                        extractResourceRoles(source).stream()).collect(toSet()));
    }

//    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
//        String role = jwt.getClaim("role");
//        List<GrantedAuthority> authorities = new ArrayList<>();
//
//        if (role != null && !role.isEmpty())
//            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
//
//        return authorities;
//    }

    private Collection<? extends GrantedAuthority> extractResourceRoles(Jwt jwt) {
        String role = jwt.getClaim("role");
        Collection<Privilege> privileges = switch (role) {
            case "GUEST" -> Guest.getPrivilege();
            case "ADMIN" -> Admin.getPrivilege();
            default -> Owner.getPrivilege();
        };

        return privileges.stream()
                .map(privilege -> new SimpleGrantedAuthority(privilege.name()))
                .collect(Collectors.toSet());
    }
}