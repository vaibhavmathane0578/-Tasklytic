package com.tasklytic.userservice.config;

import com.tasklytic.shared.security.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class WebSecurityConfig {

    @Bean
    public OncePerRequestFilter jwtValidationFilter() {
        return new JwtValidationFilter();
    }

    // Register the filter in the Spring Security filter chain
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(jwtValidationFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
