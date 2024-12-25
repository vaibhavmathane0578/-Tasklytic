package com.tasklytic.shared.security;

import com.tasklytic.shared.constants.Constants;
import com.tasklytic.shared.utils.JwtUtil;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.List;

public class JwtValidationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    private final List<String> publicEndpoints;

    public JwtValidationFilter(List<String> publicEndpoints) {
        this.publicEndpoints = publicEndpoints;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();

        // Skip JWT validation for public endpoints
        if (publicEndpoints.contains(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT token from the request
        String token = getTokenFromRequest(request);
        if (token != null) {
            try {
                // Validate the token
                if (validateTokenAndSetAuthentication(token)) {
                    filterChain.doFilter(request, response);
                    return;
                }
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(Constants.JWT_TOKEN_INVALID);
                return;
            }
        }

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(Constants.JWT_TOKEN_INVALID);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private boolean validateTokenAndSetAuthentication(String token) {
        // Extract subject (e.g., user identifier)
        String subject = jwtUtil.getSubjectFromToken(token);

        // Safely extract roles claim from the token
        List<String> roles = jwtUtil.getClaimFromToken(token, "roles", List.class);

        if (roles == null) {
            roles = List.of(); // Default to an empty list
        }

        // Validate the token
        if (subject != null && jwtUtil.validateToken(token, subject)) {
            // Convert roles to granted authorities
            List<SimpleGrantedAuthority> authorities = roles.stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            // Create an authentication token
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(subject, null, authorities);

            // Set the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        }

        return false; // Validation failed
    }
}
