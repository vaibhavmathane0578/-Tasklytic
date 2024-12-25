package com.tasklytic.userservice.config;

import com.tasklytic.shared.security.JwtValidationFilter;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig {

	@Bean
	public List<String> publicEndpoints() {
		return List.of("/api/users/register", "/api/users/login", "/api/users/sendOtp");
	}

	@Bean
	public JwtValidationFilter jwtValidationFilter(List<String> publicEndpoints) {
		return new JwtValidationFilter(publicEndpoints);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtValidationFilter jwtValidationFilter)
			throws Exception {
		http.csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity (customizable)
				.authorizeHttpRequests(
						auth -> auth.requestMatchers(publicEndpoints().toArray(new String[0])).permitAll()
								.requestMatchers("/api/users/**").authenticated() 
								.anyRequest().authenticated()
				).addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
}
