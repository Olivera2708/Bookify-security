package rs.ac.uns.ftn.Bookify.config.filters;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;
import rs.ac.uns.ftn.Bookify.config.JWTBasedAuthentication;
import rs.ac.uns.ftn.Bookify.config.utils.JWTUtils;

import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private JWTUtils jwtUtils;
    private UserDetailsService userDetailsService;
    protected final Log LOGGER = LogFactory.getLog(getClass());

    public JWTAuthenticationFilter(JWTUtils jwtUtils, UserDetailsService userDetailsService) {
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String username;
        String authToken = jwtUtils.getToken(request);
        try {
            ValidateToken(authToken);
        } catch (ExpiredJwtException ex) {
            LOGGER.debug("Token expired!");
        }
        filterChain.doFilter(request, response);
    }

    private void ValidateToken(String authToken) {
        String username;
        if (authToken != null) {
            username = jwtUtils.getUsernameFromToken(authToken);
            if (username != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (userDetails != null) {
                    if (jwtUtils.validateToken(authToken, userDetails)) {
                        JWTBasedAuthentication authentication = new JWTBasedAuthentication(userDetails);
                        authentication.setToken(authToken);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        }
    }
}
