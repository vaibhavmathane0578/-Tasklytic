package com.tasklytic.userservice.utils;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.SecretKey;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.tasklytic.userservice.model.UserEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private long jwtExpirationInMs;

	private SecretKey getSigningKey() {
		byte[] keyBytes = secretKey.getBytes();
		return new SecretKeySpec(keyBytes, SignatureAlgorithm.HS512.getJcaName());
	}

	// Generate a JWT Token
	public String generateToken(UserEntity user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", user.getRole());
		claims.put("email", user.getEmail());

		return Jwts.builder().setClaims(claims) // Add claims
				.setSubject(user.getId().toString()) // Set user ID as subject
				.setIssuedAt(new Date()) // Token issued time
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs)) // Expiry time
				.signWith(getSigningKey()) // Sign with secret key object
				.compact();
	}

	// Extract user ID (subject) from the token
	public String getUserIdFromToken(String token) {
		Claims claims = extractAllClaims(token);
		return claims.getSubject();
	}

	// Validate the token
	public boolean validateToken(String token, UserEntity user) {
		String userId = getUserIdFromToken(token);
		return (userId.equals(user.getId().toString()) && !isTokenExpired(token));
	}

	// Check if the token is expired
	private boolean isTokenExpired(String token) {
		Claims claims = extractAllClaims(token);
		return claims.getExpiration().before(new Date());
	}

	private JwtParser getJwtParser() {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()) // Use SecretKey for parsing
				.build();
	}

	// Extract all claims from the token
	// Extract all claims from the token
	private Claims extractAllClaims(String token) {
		return getJwtParser().parseClaimsJws(token).getBody();
	}
}
