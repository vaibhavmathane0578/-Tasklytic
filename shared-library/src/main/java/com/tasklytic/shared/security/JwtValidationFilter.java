package com.tasklytic.shared.security;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class JwtValidationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    private static final Logger logger = LoggerFactory.getLogger(JwtValidationFilter.class);

    // Define public endpoints that don't require authentication
    private static final List<String> PUBLIC_ENDPOINTS = List.of(
        "/api/users/register",
        "/api/users/login",
        "/api/users/sendOtp"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        logger.info("Incoming request URI: {}", requestURI);

        // Skip JWT validation for public endpoints
        if (PUBLIC_ENDPOINTS.contains(requestURI)) {
            logger.info("Public endpoint accessed: {}, skipping JWT validation.", requestURI);
            filterChain.doFilter(request, response);
            return;
        }

        // Extract the JWT token from the request
        String token = getTokenFromRequest(request);
        if (token != null) {
            logger.info("Token found in request: {}", token);

            // Validate the token
            try {
                if (validateTokenAndSetAuthentication(token)) {
                    logger.info("Token is valid. Proceeding to the next filter.");
                    filterChain.doFilter(request, response);
                    return;
                }
            } catch (Exception e) {
                logger.error("JWT validation failed: {}", e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Invalid or expired JWT token");
                return;
            }
        }

        logger.warn("No token found or token is invalid. Blocking request.");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("Authentication required");
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            logger.info("Authorization header found. Extracting token.");
            return bearerToken.substring(7);
        }
        return null;
    }

    
    @SuppressWarnings("unchecked")
	private boolean validateTokenAndSetAuthentication(String token) {
        try {
            // Extract subject (e.g., user identifier)
            String subject = jwtUtil.getSubjectFromToken(token);
            logger.info("Extracted subject: {}", subject);

            // Safely extract roles claim from the token
            List<String> roles = jwtUtil.getClaimFromToken(token, "roles", List.class);

            if (roles == null) {
                logger.warn("Roles claim is missing or null in the token.");
                roles = List.of(); // Default to an empty list
            }

            logger.info("Extracted roles: {}", roles);

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
                logger.info("Authentication set for user: {}", subject);
                return true;
            } else {
                logger.warn("Token validation failed for subject: {}", subject);
            }
        } catch (Exception e) {
            logger.error("JWT validation failed: {}", e.getMessage(), e);
        }
        return false; // Validation failed
    }
}
