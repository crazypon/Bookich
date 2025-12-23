package com.ilnur.bookich.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


/*
CORS - stands for Cross Origin Resource Sharing
Nowadays, application are built in two different parts backend and front end.
With Front end we invoke API of our back end to get the data. And CORS does not allow us
to share resources for origins that are different from us (like they have different hostname and different port). So,
CORS is not a cyber-attack it is just constraint

CSRF - stands for Cross Site Request Forgery
When malicious website pretends that you are the user
 */

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationProvider provider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .csrf(csrf -> csrf.disable()) // we usually disable csrf for RestAPIs
                .authorizeHttpRequests(
                auth -> auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults()) // this parameter is mandatory for basic authentication
                .authenticationProvider(provider);

        return http.build();
    }
}
