package org.springframework.security.crypto.bcrypt;

public class BCryptPasswordEncoder {

	public String encode(String password) {
		// Generate a salt
		String salt = BCrypt.gensalt(12); // The "12" is the log rounds (cost factor)

		// Return the encoded password
		return BCrypt.hashpw(password, salt);
	}

	// Method to check if the password matches the encoded one
	public boolean matches(String rawPassword, String encodedPassword) {
		// Check if raw password matches the encoded password
		return BCrypt.checkpw(rawPassword, encodedPassword);
	}

	public static void main(String[] args) {
		// Create an instance of BCryptPasswordEncoder
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		// Sample password to encode
		String password = "mysecretpassword";

		// Encode the password
		String encodedPassword = passwordEncoder.encode(password);
		System.out.println("Encoded Password: " + encodedPassword);

		// Verifying password (matches the encoded password)
		boolean isPasswordMatch = passwordEncoder.matches(password, encodedPassword);
		System.out.println("Password matches: " + isPasswordMatch);
	}
}
