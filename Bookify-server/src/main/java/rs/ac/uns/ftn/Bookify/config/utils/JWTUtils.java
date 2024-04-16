package rs.ac.uns.ftn.Bookify.config.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTUtils {
    // mogu se eksternalizovati ovi podaci u application.properties
    @Value("bookify")
    private String APP_NAME;

    @Value("tvojamama")
    private String SECRET;

    @Value("1800000")
    private int EXPIRES_IN_WEB;

    @Value("5000000")
    private int EXPIRES_IN_MOBILE;

    @Value("Authorization")
    private String AUTH_HEADER;

//    private static final String AUDIENCE_TABLET = "tablet";
    private static final String AUDIENCE_MOBILE = "mobile";
    private static final String AUDIENCE_WEB = "web";

    private final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    public String generateToken(String username, Long id, String role, String userAgent) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("id", id);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(APP_NAME)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(generateExpirationDate(userAgent))
                .setAudience(generateAudience(userAgent))
                .signWith(SIGNATURE_ALGORITHM, SECRET).compact();
    }

    private String generateAudience(String userAgent) {
        // TO-DO implement for mobile devices
        if(userAgent.contains("Mobile")){
            return AUDIENCE_MOBILE;
        }
        return AUDIENCE_WEB;
    }

    private Date generateExpirationDate(String userAgent) {
        if(userAgent.contains("Mobile")){
            return new Date(new Date().getTime() + EXPIRES_IN_MOBILE);
        }

        return new Date(new Date().getTime() + EXPIRES_IN_WEB);
    }

    public String getToken(HttpServletRequest request) {
        String authHeader = getAuthHeaderFromHeader(request);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            username = claims.getSubject();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Date getIssuedAtDateFromToken(String token) {
        Date issueAt;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            issueAt = claims.getIssuedAt();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            issueAt = null;
        }
        return issueAt;
    }

    public String getAudienceFromToken(String token) {
        String audience;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            audience = claims.getAudience();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            audience = null;
        }
        return audience;
    }

    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }

    private Claims getAllClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException ex) {
            throw ex;
        } catch (Exception e) {
            claims = null;
        }
        return claims;

    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = this.getUsernameFromToken(token);
        final Date created = this.getIssuedAtDateFromToken(token);
        return (username != null
                && username.equals(userDetails.getUsername()));
    }

    public int getExpiredIn(String userAgent) {

        return userAgent.contains("Mobile") ? EXPIRES_IN_MOBILE : EXPIRES_IN_WEB;
    }

    public String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader(AUTH_HEADER);
    }

}
