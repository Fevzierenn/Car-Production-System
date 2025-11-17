package com.carproduction.demo.demo.configs;


import com.carproduction.demo.demo.filter.JwtAuthFilter;
import com.carproduction.demo.demo.services.UserInfoDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
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

                        // Role-based endpoints
                        .requestMatchers("/auth/user/**").hasRole("USER")
                        .requestMatchers("/auth/admin/**").hasRole("ADMIN")

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




    /*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
         httpSecurity.csrf(AbstractHttpConfigurer::disable)
                 .authorizeHttpRequests(auth -> auth
                         .requestMatchers("/user/auth").hasRole("ADMIN")
                         .requestMatchers("/public/**", "/cars/**", "/user").permitAll().anyRequest().authenticated()
                 ).formLogin(Customizer.withDefaults());

         return httpSecurity.build();

     }


    .requestMatchers("/public/**").permitAll()
    Bu şu demek: /public/hello, /public/info, vs. → herkes erişebilir.


    1. Tüm endpoint'leri açık yapmak istersen:

    .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())

    2. Sadece bazılarını açık, diğerlerini korumalı yapmak istersen:
    .requestMatchers("/public/**", "/cars/**").permitAll()
    .anyRequest().authenticated()
    3. Role bazlı erişim:

    .requestMatchers("/admin/**").hasRole("ADMIN")
    .requestMatchers("/cars/**").hasAnyRole("USER", "ADMIN")





      */

}
