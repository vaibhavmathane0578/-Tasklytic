package com.tasklytic.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import com.tasklytic.shared.constants.Constants;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {

	@Autowired
	private StringRedisTemplate redisTemplate;

	@Autowired
	private EmailService emailService;

	private final Random random = new Random();

	private static final int OTP_EXPIRY_MINUTES = 5;

	// Send OTP
	public void sendOtp(String email) {
		String otp = String.format("%06d", random.nextInt(999999));

		// Store OTP in Redis with TTL
		redisTemplate.opsForValue().set(getOtpKey(email), otp, OTP_EXPIRY_MINUTES, TimeUnit.MINUTES);

		// Send OTP email
		emailService.sendOtpEmail(email, otp);
	}

	// Verify OTP and return success status
	public boolean verifyEmailOtp(String email, String otp) {
		// Retrieve OTP from Redis
		String storedOtp = redisTemplate.opsForValue().get(getOtpKey(email));

		if (storedOtp == null) {
			throw new Constants.OtpNotFoundException(String.format(Constants.OTP_NOT_FOUND_OR_EXPIRED, email));
		}

		if (!storedOtp.equals(otp)) {
			throw new Constants.InvalidOtpException(Constants.OTP_INVALID);
		}

		// OTP is valid, delete OTP from Redis after successful verification
		redisTemplate.delete(getOtpKey(email)); // OTP is used, delete it from Redis
		return true; // OTP verified successfully
	}

	// Helper method to generate the Redis key for OTP storage
	private String getOtpKey(String email) {
		return "OTP_" + email;
	}
}
