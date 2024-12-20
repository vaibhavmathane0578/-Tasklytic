package com.tasklytic.shared.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Component
public class JwtUtil {

    private final String secretKey;
    private final long jwtExpirationInMs;

    public JwtUtil(@Value("${jwt.secret}") String secretKey,
                   @Value("${jwt.expiration}") long jwtExpirationInMs) {
        this.secretKey = secretKey;
        this.jwtExpirationInMs = jwtExpirationInMs;
    }

    private SecretKey getSigningKey() {
        byte[] keyBytes = secretKey.getBytes();
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    // Generate a JWT Token with custom claims
    public String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) // Add claims
                .setSubject(subject) // Set subject (e.g., user ID)
                .setIssuedAt(new Date()) // Token issued time
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs)) // Expiry time
                .signWith(getSigningKey(), SignatureAlgorithm.HS512) // Sign with secret key
                .compact();
    }

    // Extract subject (e.g., user ID) from the token
    public String getSubjectFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    // Validate the token
    public boolean validateToken(String token, String expectedSubject) {
        try {
            String tokenSubject = getSubjectFromToken(token);
            return tokenSubject.equals(expectedSubject) && !isTokenExpired(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false; // Token is invalid or expired
        }
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration().before(new Date());
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token has expired", e);
        } catch (JwtException e) {
            throw new RuntimeException("Error parsing token", e);
        }
    }

    // Extract a specific claim from the token
    public <T> T getClaimFromToken(String token, String claimKey, Class<T> claimType) {
        Claims claims = extractAllClaims(token);
        return claims.get(claimKey, claimType);
    }
}
