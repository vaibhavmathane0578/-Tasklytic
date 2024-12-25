package com.tasklytic.userservice.security;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class BCryptPasswordEncoder {

	public String encode(String password) {
		// Generate a salt
		String salt = BCrypt.gensalt(12); // The "12" is the log rounds (cost factor)
		return BCrypt.hashpw(password, salt);
	}

	public boolean matches(String rawPassword, String encodedPassword) {
		return BCrypt.checkpw(rawPassword, encodedPassword);
	}

	public static void main(String[] args) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = "mysecretpassword";
		String encodedPassword = passwordEncoder.encode(password);
		System.out.println("Encoded Password: " + encodedPassword);
		boolean isPasswordMatch = passwordEncoder.matches(password, encodedPassword);
		System.out.println("Password matches: " + isPasswordMatch);
	}
}
