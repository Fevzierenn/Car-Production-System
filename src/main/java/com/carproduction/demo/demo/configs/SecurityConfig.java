package com.carproduction.demo.demo.configs;


import com.carproduction.demo.demo.filter.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    public final JwtAuthFilter jwtAuthFilter;
    protected UserDetailsService userDetailsService;


    public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserDetailsService userDetailsService){
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }


    /*
     * Main security configuration
     * Defines endpoint access rules and JWT filter setup
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, DaoAuthenticationProvider daoAuthenticationProvider) throws Exception {
        http
                // Disable CSRF (not needed for stateless JWT)
                .csrf(csrf -> csrf.disable())

                // Configure endpoint authorization
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers( "/auth/addNewUser", "/auth/generateToken","/auth/welcome", "/auth", "/cars/**", "/h2-console/**").permitAll()

                        // Role-based endpoints (good for defense-in-depth, but @PreAuthorize is primary)
                        .requestMatchers("/auth/user/**").hasAuthority("ROLE_USER")
                        .requestMatchers("/auth/admin/**").hasAuthority("ROLE_ADMIN")

                        // All other endpoints require authentication
                        .anyRequest().authenticated()
                )

                // Stateless session (required for JWT)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Set custom authentication provider
                .authenticationProvider(daoAuthenticationProvider)

                // Add JWT filter before Spring Security's default filter
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                // Allow H2 console to be embedded in a frame
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));

        return http.build();
    }
}
