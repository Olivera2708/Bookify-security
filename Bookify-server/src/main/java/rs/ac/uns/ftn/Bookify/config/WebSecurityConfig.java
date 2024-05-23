package rs.ac.uns.ftn.Bookify.config;

import jakarta.servlet.DispatcherType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import rs.ac.uns.ftn.Bookify.config.filters.XSSFilter;
import rs.ac.uns.ftn.Bookify.config.utils.JWTUtils;
import rs.ac.uns.ftn.Bookify.service.CustomUserDetailService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private JWTUtils jwtUtils;

    @Bean
    public WebMvcConfigurer CORSConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("https://localhost:4200").allowedMethods("GET", "PUT", "POST", "DELETE");
            }
        };
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(httpSecurityCorsConfigurer -> CORSConfigurer());
        http.authorizeHttpRequests(request ->{
            request.dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/accommodations/image/{imageId}","/api/v1/accommodations/search", "/api/v1/accommodations/details/{accommodationId}",
                            "/api/v1/accommodations/top-accommodations", "/api/v1/accommodations/top-locations", "/api/v1/users/image/{imageId}", "/api/v1/users/forgot-password/{email}",
                            "/api/v1/accommodations/images/{accommodationId}", "/api/v1/reviews/accommodation/{accommodationId}", "/api/v1/reviews/owner/{ownerId}", "/api/v1/users/user/{userId}",
                            "/api/v1/reviews/owner/{ownerId}", "/api/v1/reviews/owner/{ownerId}/rating", "/api/v1/reviews/accommodation/{accommodationId}/rating", "/api/test-unescape", "/api/test").permitAll()
                    .requestMatchers(HttpMethod.POST,"/api/v1/users/login", "/api/v1/users", "/api/v1/accommodations/filter", "/api/v1/users/mobile", "/sendMessageRest").permitAll()
                    .requestMatchers(HttpMethod.PUT, "/api/v1/users/activate-account").permitAll()
                    .anyRequest().authenticated()
            ;
        })
                .headers(headers -> headers
                .contentSecurityPolicy(csp -> csp.policyDirectives("default-src 'self'; script-src 'self'"))
                .frameOptions(frameOptions -> frameOptions.sameOrigin())
                .httpStrictTransportSecurity(hsts -> hsts.includeSubDomains(true).maxAgeInSeconds(31536000))
        );
//                .headers(header -> header.xssProtection().and().contentSecurityPolicy(cs -> cs.policyDirectives("script-src 'self'")));
//                .oauth2Login(withDefaults())
//                .headers(headers -> headers.contentSecurityPolicy(csp -> csp.policyDirectives("default-src 'self'; script-src 'self' 'unsafe-inline'; style-src 'self' 'unsafe-inline'; font-src 'self' https://fonts.gstatic.com"))
//                        .frameOptions(frameOptions -> frameOptions.deny())
//                        .httpStrictTransportSecurity(hsts -> hsts.includeSubDomains(true).maxAgeInSeconds(31536000))
//                        .contentTypeOptions(contentTypeOptions -> contentTypeOptions.disable())
//                );
        http.oauth2ResourceServer(auth -> auth.jwt(token -> token.jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter())));
        return http.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .ldapAuthentication()
                .userDnPatterns("uid={0},ou=users,ou=system")
                .contextSource()
                .url("ldap://localhost:10389/dc=example,dc=com")
                .and()
                .passwordCompare()
                .passwordEncoder(new BCryptPasswordEncoder())
                .passwordAttribute("userpassword");
    }

    @Bean
    public FilterRegistrationBean<XSSFilter> filterRegistrationBean() {
        FilterRegistrationBean<XSSFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new XSSFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(HttpMethod.POST,"/socket/**", "/ws/**")
                .requestMatchers(HttpMethod.GET, "/", "/webjars/*", "/*.html", "favicon.ico",
                        "/*/*.html", "/*/*.css", "/*/*.js", "/socket/**", "/ws/**");

    }
}
