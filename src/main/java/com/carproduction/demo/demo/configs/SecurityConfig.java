package com.carproduction.demo.demo.configs;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
         httpSecurity.csrf(AbstractHttpConfigurer::disable)
                 .authorizeHttpRequests(auth -> auth
                         .requestMatchers("/user/auth").hasRole("ADMIN")
                         .requestMatchers("/public/**", "/cars/**", "/user").permitAll().anyRequest().authenticated()
                 ).formLogin(Customizer.withDefaults());

         return httpSecurity.build();

     }

     /*
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
