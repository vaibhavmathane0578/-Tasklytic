package com.tasklytic.userservice.config;

import com.tasklytic.shared.security.JwtValidationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig {

    private static final String[] PUBLIC_ENDPOINTS = {
        "/api/users/register",
        "/api/users/login",
        "/api/users/sendOtp"
    };

    @Bean
    public JwtValidationFilter jwtValidationFilter() {
        return new JwtValidationFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity (customizable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(PUBLIC_ENDPOINTS).permitAll() // Public endpoints
                .requestMatchers("/api/users/**").authenticated()    // Protect API endpoints
                .anyRequest().authenticated()                  // Protect any remaining endpoints
            )
            .addFilterBefore(jwtValidationFilter(), UsernamePasswordAuthenticationFilter.class); // Add JWT filter

        return http.build();
    }
}
