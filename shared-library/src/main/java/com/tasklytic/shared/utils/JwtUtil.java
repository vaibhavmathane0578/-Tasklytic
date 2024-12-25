package com.tasklytic.shared.utils;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
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
        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
        return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
    }

    // Generate a JWT Token with custom claims
    public String generateToken(Map<String, Object> claims, String subject) {
        Date issuedAt = new Date();
        Date expiration = new Date(issuedAt.getTime() + jwtExpirationInMs);
        
        System.out.println("Token Issued At: " + issuedAt);
        System.out.println("Token Expiration: " + expiration);
        
        return Jwts.builder()
                .setClaims(claims) // Add claims
                .setSubject(subject) // Set subject (email)
                .setIssuedAt(issuedAt) 
                .setExpiration(expiration) 
                .signWith(getSigningKey(), SignatureAlgorithm.HS512) 
                .compact();
    }

    // Extract subject (emailId) from the token
    public String getSubjectFromToken(String token) {
        Claims claims = extractAllClaims(token);
        return claims.getSubject();
    }

    // Validate the token
    public boolean validateToken(String token, String expectedSubject) {
        try {
            String tokenSubject = getSubjectFromToken(token);
            boolean isValid = tokenSubject.equals(expectedSubject) && !isTokenExpired(token);
            System.out.println("Token Subject: " + tokenSubject);
            System.out.println("Expected Subject: " + expectedSubject);
            return isValid;
        } catch (JwtException | IllegalArgumentException e) {
            return false; 
        }
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        Claims claims = extractAllClaims(token);
        boolean isExpired = claims.getExpiration().before(new Date());
        System.out.println("Token Expiration: " + claims.getExpiration());
        return isExpired;
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
