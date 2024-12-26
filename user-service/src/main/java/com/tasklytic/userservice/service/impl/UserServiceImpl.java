package com.tasklytic.userservice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tasklytic.shared.utils.JwtUtil;
import com.tasklytic.shared.constants.Constants;
import com.tasklytic.shared.constants.Constants.Exceptions;
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
	
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

	//Register
	@Override
	public UserRegistrationResponseDTO registerUser(UserRegistrationDTO registerDTO) {
		Optional<UserEntity> existingUser = userRepository.findByEmail(registerDTO.getEmail());
		if (existingUser.isPresent()) {
			UserEntity user = existingUser.get();
			if (user.getStatus() == UserStatus.DELETED) {
				boolean isOtpVerified = otpService.verifyEmailOtp(registerDTO.getEmail(), registerDTO.getOtp());
				if (!isOtpVerified) {
					throw new Exceptions.EmailNotVerifiedException(Constants.EMAIL_NOT_VERIFIED);
				}
		        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
		            throw new Exceptions.PasswordMismatchException(Constants.PASSWORD_MISMATCH);
		        }
				user.setFirstName(registerDTO.getFirstName());
				user.setLastName(registerDTO.getLastName());
				user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
				user.setMobileNumber(registerDTO.getMobileNumber());
				user.setRole(registerDTO.getRole());
				user.setDepartment(registerDTO.getDepartment());
				user.setStatus(UserStatus.ACTIVE);
				user.setEmailVerified(true);

				userRepository.save(user);

				String token = generateJwtTokenForUser(user);

				return new UserRegistrationResponseDTO(user.getId(), user.getFirstName(), user.getLastName(),user.getEmail(), token);
			} else {
				throw new Exceptions.EmailAlreadyExistsException(
						String.format(Constants.EMAIL_ALREADY_EXISTS, registerDTO.getEmail()));
			}
		}
		boolean isOtpVerified = otpService.verifyEmailOtp(registerDTO.getEmail(), registerDTO.getOtp());
		if (!isOtpVerified) {
			throw new Exceptions.EmailNotVerifiedException(Constants.EMAIL_NOT_VERIFIED); // If OTP is not verified,
																							// throw exception
		}
		Optional<UserEntity> existingMobile = userRepository.findByMobileNumber(registerDTO.getMobileNumber());
		if (existingMobile.isPresent()) {
			throw new Exceptions.MobileAlreadyExistsException(
					String.format(Constants.MOBILE_ALREADY_EXISTS, registerDTO.getMobileNumber()));
		}
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            throw new Exceptions.PasswordMismatchException(Constants.PASSWORD_MISMATCH);
        }
		UserEntity user = new UserEntity();
		user.setFirstName(registerDTO.getFirstName());
		user.setLastName(registerDTO.getLastName());
		user.setEmail(registerDTO.getEmail());
		user.setMobileNumber(registerDTO.getMobileNumber());
		user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
		user.setRole(registerDTO.getRole());
		user.setDepartment(registerDTO.getDepartment());
		user.setStatus(UserStatus.ACTIVE);
		user.setEmailVerified(true); 
		userRepository.save(user);

		String token = generateJwtTokenForUser(user);

		return new UserRegistrationResponseDTO(user.getId(), user.getFirstName(), user.getLastName(),user.getEmail(), token);
	}

	//Login
	@Override
	public UserLoginResponseDTO loginUser(UserLoginDTO loginDTO) {
		Optional<UserEntity> existingUser = userRepository.findByEmail(loginDTO.getEmail());
		if (!existingUser.isPresent()) {
			throw new Exceptions.InvalidCredentialsException(Constants.INVALID_CREDENTIALS);
		}
		UserEntity user = existingUser.get();
		if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
			throw new Exceptions.InvalidCredentialsException(Constants.INVALID_CREDENTIALS);
		}
		String token = generateJwtTokenForUser(user);
		user.setStatus(UserStatus.ACTIVE);
		userRepository.save(user);

		return new UserLoginResponseDTO(user.getId(), user.getFirstName(), user.getLastName(), token);

	}
	
	//Log-out
	@Override
	public void logoutUser(UUID userId) {
		Optional<UserEntity> existingUser = userRepository.findById(userId);
		if (!existingUser.isPresent()) {
			throw new Exceptions.UserNotFoundException(String.format(Constants.USER_NOT_FOUND, userId));
		}

		UserEntity user = existingUser.get();
		user.setStatus(UserStatus.INACTIVE); // Set user status to INACTIVE
		userRepository.save(user);
	}

	//OTP
	@Override
	public void sendOtp(OtpRequestDTO otpRequestDTO) {
		// Check if user exists
		Optional<UserEntity> userOpt = userRepository.findByEmail(otpRequestDTO.getEmail());
		if (!userOpt.isPresent()) {
			throw new Exceptions.UserNotFoundException(
					String.format(Constants.USER_NOT_FOUND, otpRequestDTO.getEmail()));
		}
		otpService.sendOtp(otpRequestDTO.getEmail());
	}

	@Override
	public void updateUserDetails(UpdateUserDTO updateUserDTO) {
	    Optional<UserEntity> userOpt = userRepository.findById(updateUserDTO.getUserId());
	    if (!userOpt.isPresent()) {
	        throw new Exceptions.UserNotFoundException(
	                String.format(Constants.USER_NOT_FOUND, updateUserDTO.getUserId()));
	    }

	    UserEntity user = userOpt.get();
	    boolean isOtpVerified = false;
	    if (updateUserDTO.getFirstName() != null || updateUserDTO.getLastName() != null ||
	        updateUserDTO.getMobileNumber() != null || updateUserDTO.getEmail() != null ||
	        updateUserDTO.getPassword() != null||updateUserDTO.getConfirmPassword() != null || updateUserDTO.getRole() != null || 
	        updateUserDTO.getDepartment() != null) {
	        
	        isOtpVerified = otpService.verifyEmailOtp(user.getEmail(), updateUserDTO.getOtp());
	        if (!isOtpVerified) {
	            throw new Exceptions.EmailNotVerifiedException(Constants.EMAIL_NOT_VERIFIED);
	        }
	    }
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
	            throw new Exceptions.PasswordMismatchException(Constants.PASSWORD_MISMATCH);
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
			throw new Exceptions.UserNotFoundException(String.format(Constants.USER_NOT_FOUND, userId));
		}

		UserEntity user = userOpt.get();
		user.setStatus(UserStatus.DELETED);
		userRepository.save(user);
	}

	// Get all users by filter
	@Override
	public List<UserResponseDTO> getAllUsers(UserFilterDTO filterDTO) {
		 log.info("Fetching all users");
	    List<UserEntity> users;
	    if (filterDTO.getStatus() != null) {
	        users = userRepository.findByStatus(filterDTO.getStatus());
	        log.info("Number of users retrieved: {}", users.size());
	    } else {
	        users = userRepository.findAll();
	        log.info("Number of users retrieved: {}", users.size());
	    }
	    return users.stream().map(user -> new UserResponseDTO(
	            user.getId(), user.getFirstName(), user.getLastName(),
	            user.getEmail(), user.getMobileNumber(), user.getStatus())
	    ).toList();
	}

	@Override
	public List<UserEntity> getAllUsers() {
		return userRepository.findAll();
	}
	
	//generate token
	private String generateJwtTokenForUser(UserEntity user) {
	    // Define claims for the JWT
	    Map<String, Object> claims = new HashMap<>();
	    claims.put("email", user.getEmail());
	    claims.put("role", user.getRole().toString());
	    return jwtUtil.generateToken(claims, user.getEmail());
	}

	public boolean validateJwtToken(String token, UserEntity user) {
	    return jwtUtil.validateToken(token, user.getEmail().toString());
	}

	public String extractUserIdFromToken(String token) {
	    return jwtUtil.getSubjectFromToken(token); 
	}
}
