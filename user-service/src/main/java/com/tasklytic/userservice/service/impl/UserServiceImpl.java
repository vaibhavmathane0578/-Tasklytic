package com.tasklytic.userservice.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tasklytic.userservice.repository.UserRepository;
import com.tasklytic.userservice.dto.*;
import com.tasklytic.userservice.model.UserEntity;
import com.tasklytic.userservice.model.UserStatus;
import com.tasklytic.userservice.service.OtpService;
import com.tasklytic.userservice.service.UserService;
import com.tasklytic.userservice.utils.JwtUtil;
import com.tasklytic.userservice.constants.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private OtpService otpService;

	// Create an instance of BCryptPasswordEncoder for password encoding
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	// Method to register a new user
	@Override
	public UserRegistrationResponseDTO registerUser(UserRegistrationDTO registerDTO) {

		// Check if email already exists
		Optional<UserEntity> existingUser = userRepository.findByEmail(registerDTO.getEmail());
		if (existingUser.isPresent()) {
			throw new Constants.EmailAlreadyExistsException(
					String.format(Constants.EMAIL_ALREADY_EXISTS, registerDTO.getEmail()));
		}
		
		//verifyEmail my otp 
		boolean isOtpVerified = otpService.verifyEmailOtp(registerDTO.getEmail(), registerDTO.getOtp());
		if (!isOtpVerified) {
			throw new Constants.EmailNotVerifiedException(Constants.EMAIL_NOT_VERIFIED); // If OTP is not verified,
																							// throw exception
		}

		// Check if mobile number already exists
		Optional<UserEntity> existingMobile = userRepository.findByMobileNumber(registerDTO.getMobileNumber());
		if (existingMobile.isPresent()) {
			throw new Constants.MobileAlreadyExistsException(
					String.format(Constants.MOBILE_ALREADY_EXISTS, registerDTO.getMobileNumber()));
		}

		// Create and save the user
		UserEntity user = new UserEntity();
		user.setFirstName(registerDTO.getFirstName());
		user.setLastName(registerDTO.getLastName());
		user.setEmail(registerDTO.getEmail());
		user.setMobileNumber(registerDTO.getMobileNumber());
		user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
		user.setRole(registerDTO.getRole());
		user.setDepartment(registerDTO.getDepartment());
		user.setStatus(UserStatus.ACTIVE);
		user.setEmailVerified(true); // Since email is verified
		userRepository.save(user);

		// Generate token
		String token = jwtUtil.generateToken(user);

		// Return response
		return new UserRegistrationResponseDTO(user.getId(), user.getFirstName(), user.getLastName(), token);
	}

	// Method for user login
	@Override
	public UserLoginResponseDTO loginUser(UserLoginDTO loginDTO) {
		Optional<UserEntity> existingUser = userRepository.findByEmail(loginDTO.getEmail());
		if (!existingUser.isPresent()) {
			throw new Constants.InvalidCredentialsException(Constants.INVALID_CREDENTIALS);
		}

		UserEntity user = existingUser.get();

		// Validate password using BCryptPasswordEncoder
		if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
			throw new Constants.InvalidCredentialsException(Constants.INVALID_CREDENTIALS);
		}

		// Generate JWT Token
		String token = jwtUtil.generateToken(user);

		// Set the user status to ACTIVE on login
		user.setStatus(UserStatus.ACTIVE);
		userRepository.save(user);

		return new UserLoginResponseDTO(user.getId(), user.getFirstName(), user.getLastName(), token);

	}

	// Method to log out user and update status to INACTIVE
	@Override
	public void logoutUser(UUID userId) {
		Optional<UserEntity> existingUser = userRepository.findById(userId);
		if (!existingUser.isPresent()) {
			throw new Constants.UserNotFoundException(String.format(Constants.USER_NOT_FOUND, userId));
		}

		UserEntity user = existingUser.get();
		user.setStatus(UserStatus.INACTIVE); // Set user status to INACTIVE
		userRepository.save(user);
	}

}
