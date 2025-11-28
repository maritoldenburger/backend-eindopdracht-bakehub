package nl.maritoldenburger.bakehub.config;

import nl.maritoldenburger.bakehub.filter.JwtRequestFilter;
import nl.maritoldenburger.bakehub.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfiguration(JwtRequestFilter jwtRequestFilter, CustomUserDetailsService customUserDetailsService) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public AuthenticationManager authenticationManager(PasswordEncoder passwordEncoder) throws Exception {
        var auth = new DaoAuthenticationProvider();
        auth.setPasswordEncoder(passwordEncoder);
        auth.setUserDetailsService(customUserDetailsService);
        return new ProviderManager(auth);
    }

    @Bean
    protected SecurityFilterChain filter(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .httpBasic(basic -> basic.disable())
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth ->
                        auth
                                .requestMatchers("/auth/user").authenticated()
                                .requestMatchers("/auth/**").permitAll()

                                // Users
                                .requestMatchers(HttpMethod.POST, "/users").permitAll()
                                .requestMatchers(HttpMethod.GET, "/users").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/users/**").authenticated()
                                .requestMatchers(HttpMethod.PUT, "/users/**").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")

                                // Categories
                                .requestMatchers(HttpMethod.GET, "/categories/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/categories").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/categories/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/categories/**").hasRole("ADMIN")

                                // Recipes
                                .requestMatchers(HttpMethod.GET, "/recipes/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/recipes/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/recipes/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/recipes/**").hasRole("ADMIN")

                                // Ingredients
                                .requestMatchers(HttpMethod.GET, "/ingredients/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/ingredients/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/ingredients/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/ingredients/**").hasRole("ADMIN")

                                // Reviews
                                .requestMatchers(HttpMethod.GET, "/recipes/*/reviews/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/recipes/*/reviews").authenticated()
                                .requestMatchers(HttpMethod.DELETE, "/recipes/*/reviews/*").authenticated()

                                // Favorites
                                .requestMatchers("/users/*/favorites/**").authenticated()

                                .anyRequest().denyAll()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}