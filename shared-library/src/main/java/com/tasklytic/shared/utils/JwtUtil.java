package com.tasklytic.shared.utils;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

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
                .signWith(getSigningKey()) // Sign with secret key object
                .compact();
    }

    // Extract subject (e.g., user ID) from the token
    public String getSubjectFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    // Validate the token
    public boolean validateToken(String token, String subject) {
        String tokenSubject = getSubjectFromToken(token);
        return (tokenSubject.equals(subject) && !isTokenExpired(token));
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getExpiration().before(new Date());
    }

    private JwtParser getJwtParser() {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey()) // Use SecretKey for parsing
                .build();
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        return getJwtParser().parseClaimsJws(token).getBody();
    }
}
