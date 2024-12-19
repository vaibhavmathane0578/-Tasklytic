package com.tasklytic.userservice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tasklytic.shared.utils.JwtUtil;
import com.tasklytic.shared.constants.Constants;
import com.tasklytic.userservice.repository.UserRepository;
import com.tasklytic.userservice.dto.*;
import com.tasklytic.userservice.model.UserEntity;
import com.tasklytic.userservice.model.UserStatus;
import com.tasklytic.userservice.service.OtpService;
import com.tasklytic.userservice.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OtpService otpService;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	// Create an instance of BCryptPasswordEncoder for password encoding
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	// Method to register a new user
	@Override
	public UserRegistrationResponseDTO registerUser(UserRegistrationDTO registerDTO) {

		// Check if email already exists
		Optional<UserEntity> existingUser = userRepository.findByEmail(registerDTO.getEmail());
		if (existingUser.isPresent()) {
			UserEntity user = existingUser.get();

			// Check if user is DELETED
			if (user.getStatus() == UserStatus.DELETED) {
				// Verify email OTP
				boolean isOtpVerified = otpService.verifyEmailOtp(registerDTO.getEmail(), registerDTO.getOtp());
				if (!isOtpVerified) {
					throw new Constants.EmailNotVerifiedException(Constants.EMAIL_NOT_VERIFIED);
				}
				// Reactivate the existing user
				user.setFirstName(registerDTO.getFirstName());
				user.setLastName(registerDTO.getLastName());
				user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
				user.setMobileNumber(registerDTO.getMobileNumber());
				user.setRole(registerDTO.getRole());
				user.setDepartment(registerDTO.getDepartment());
				user.setStatus(UserStatus.ACTIVE);
				user.setEmailVerified(true);

				userRepository.save(user);

				// Generate token
				String token = generateJwtTokenForUser(user);

				return new UserRegistrationResponseDTO(user.getId(), user.getFirstName(), user.getLastName(),user.getEmail(), token);
			} else {
				throw new Constants.EmailAlreadyExistsException(
						String.format(Constants.EMAIL_ALREADY_EXISTS, registerDTO.getEmail()));
			}
		}

		// verifyEmail my otp
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
		String token = generateJwtTokenForUser(user);

		// Return response
		return new UserRegistrationResponseDTO(user.getId(), user.getFirstName(), user.getLastName(),user.getEmail(), token);
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
		String token = generateJwtTokenForUser(user);

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

	// OTP for Update
	@Override
	public void sendOtp(OtpRequestDTO otpRequestDTO) {
		// Check if user exists
		Optional<UserEntity> userOpt = userRepository.findByEmail(otpRequestDTO.getEmail());
		if (!userOpt.isPresent()) {
			throw new Constants.UserNotFoundException(
					String.format(Constants.USER_NOT_FOUND, otpRequestDTO.getEmail()));
		}

		// Send OTP using OTP service
		otpService.sendOtp(otpRequestDTO.getEmail());
	}

	@Override
	public void updateUserDetails(UpdateUserDTO updateUserDTO) {
	    Optional<UserEntity> userOpt = userRepository.findById(updateUserDTO.getUserId());
	    if (!userOpt.isPresent()) {
	        throw new Constants.UserNotFoundException(
	                String.format(Constants.USER_NOT_FOUND, updateUserDTO.getUserId()));
	    }

	    UserEntity user = userOpt.get();

	    // Verify OTP if any field other than profilePictureUrl is being updated
	    boolean isOtpVerified = false;
	    if (updateUserDTO.getFirstName() != null || updateUserDTO.getLastName() != null ||
	        updateUserDTO.getMobileNumber() != null || updateUserDTO.getEmail() != null ||
	        updateUserDTO.getPassword() != null || updateUserDTO.getRole() != null || 
	        updateUserDTO.getDepartment() != null) {
	        
	        isOtpVerified = otpService.verifyEmailOtp(user.getEmail(), updateUserDTO.getOtp());
	        if (!isOtpVerified) {
	            throw new Constants.EmailNotVerifiedException(Constants.EMAIL_NOT_VERIFIED);
	        }
	    }

	    // Update fields if provided, else retain old data
	    if (updateUserDTO.getFirstName() != null) {
	        user.setFirstName(updateUserDTO.getFirstName());
	    }
	    if (updateUserDTO.getLastName() != null) {
	        user.setLastName(updateUserDTO.getLastName());
	    }
	    if (updateUserDTO.getMobileNumber() != null) {
	        user.setMobileNumber(updateUserDTO.getMobileNumber());
	    }
	    if (updateUserDTO.getEmail() != null) {
	        user.setEmail(updateUserDTO.getEmail());
	    }
	    if (updateUserDTO.getPassword() != null) {
	        if (updateUserDTO.getPassword().equals(updateUserDTO.getConfirmPassword())) {
	            user.setPassword(passwordEncoder.encode(updateUserDTO.getPassword()));
	        } else {
	            throw new Constants.PasswordMismatchException(Constants.PASSWORD_MISMATCH);
	        }
	    }
	    if (updateUserDTO.getRole() != null) {
	        user.setRole(updateUserDTO.getRole());
	    }
	    if (updateUserDTO.getDepartment() != null) {
	        user.setDepartment(updateUserDTO.getDepartment());
	    }
	    if (updateUserDTO.getProfilePictureUrl() != null) {
	        user.setProfilePictureUrl(updateUserDTO.getProfilePictureUrl());
	    }

	    userRepository.save(user);
	}

	// Soft-Delete
	@Override
	public void softDeleteUser(UUID userId) {
		Optional<UserEntity> userOpt = userRepository.findById(userId);
		if (!userOpt.isPresent()) {
			throw new Constants.UserNotFoundException(String.format(Constants.USER_NOT_FOUND, userId));
		}

		UserEntity user = userOpt.get();
		user.setStatus(UserStatus.DELETED);
		userRepository.save(user);
	}

	// Get all users by filter
	@Override
	public List<UserResponseDTO> getAllUsers(UserFilterDTO filterDTO) {
		List<UserEntity> users;

		// Filter users based on status
		if (filterDTO.getStatus() != null) {
			users = userRepository.findByStatus(filterDTO.getStatus());
		} else {
			users = userRepository.findAll();
		}

		// Convert UserEntity to UserResponseDTO
		return users.stream().map(user -> new UserResponseDTO(user.getId(), user.getFirstName(), user.getLastName(),
				user.getEmail(), user.getMobileNumber(), user.getStatus())).toList();
	}

	// Fetch all users
	@Override
	public List<UserEntity> getAllUsers() {
		return userRepository.findAll();
	}
	
	//Token logics
	public String generateJwtTokenForUser(UserEntity user) {
	    Map<String, Object> claims = new HashMap<>();
	    claims.put("role", user.getRole());
	    claims.put("email", user.getEmail());
	    return jwtUtil.generateToken(claims, user.getId().toString());
	}
	
	public boolean validateJwtToken(String token, UserEntity user) {
	    return jwtUtil.validateToken(token, user.getId().toString());
	}

	public String extractUserIdFromToken(String token) {
	    return jwtUtil.getSubjectFromToken(token); // This will return the user ID as a string
	}


	
}
