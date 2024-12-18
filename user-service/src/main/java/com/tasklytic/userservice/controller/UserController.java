package com.tasklytic.userservice.controller;

import com.tasklytic.userservice.constants.Constants;
import com.tasklytic.userservice.dto.*;
import com.tasklytic.userservice.model.UserEntity;
import com.tasklytic.userservice.service.UserService;

import jakarta.validation.Valid;

import com.tasklytic.userservice.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private OtpService otpService;

	// User Registration (including Reactivation)
	@PostMapping("/register")
	public ResponseEntity<UserRegistrationResponseDTO> registerUser(@RequestBody UserRegistrationDTO registerDTO) {
		UserRegistrationResponseDTO response = userService.registerUser(registerDTO);
		return ResponseEntity.ok(response);
	}

	// User Login
	@PostMapping("/login")
	public ResponseEntity<UserLoginResponseDTO> loginUser(@RequestBody UserLoginDTO loginDTO) {
		UserLoginResponseDTO response = userService.loginUser(loginDTO);
		return ResponseEntity.ok(response);
	}

	// User Logout
	@PostMapping("/logout/{userId}")
	public ResponseEntity<String> logoutUser(@PathVariable UUID userId) {
		userService.logoutUser(userId);
		return ResponseEntity.ok("User logged out successfully");
	}

	// Send OTP for updating user details
	@PostMapping("/sendOtp")
	public ResponseEntity<String> sendOtpForUpdate(@RequestBody OtpRequestDTO otpRequestDTO) {
		otpService.sendOtp(otpRequestDTO.getEmail());
		return ResponseEntity.ok("OTP sent successfully");
	}

	// Update user details (email or password)
	@PutMapping("/update")
	public ResponseEntity<String> updateUserDetails(@RequestBody UpdateUserDTO updateUserDTO) {
		userService.updateUserDetails(updateUserDTO);
		return ResponseEntity.ok("User details updated successfully");
	}

	// Soft Delete User
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable UUID userId) {
		userService.softDeleteUser(userId);
		return ResponseEntity.ok("User deleted successfully");
	}

	// Get All Users with optional filter
	@GetMapping("/all")
	public ResponseEntity<List<UserResponseDTO>> getAllUsers(@RequestBody UserFilterDTO filterDTO) {
		List<UserResponseDTO> users = userService.getAllUsers(filterDTO);
		return ResponseEntity.ok(users);
	}

	// Fetch All Users without filter
	@GetMapping("/all-users")
	public ResponseEntity<List<UserEntity>> getAllUsersWithoutFilter() {
		List<UserEntity> users = userService.getAllUsers();
		return ResponseEntity.ok(users);
	}

	// Verify OTP for email (used for registration or any email-related updates)
	@PostMapping("/verify-otp")
	public ResponseEntity<String> verifyEmailOtp(@RequestBody @Valid OtpVerifyRequestDTO otpVerifyRequestDTO) {
	    otpService.verifyEmailOtp(otpVerifyRequestDTO.getEmail(), otpVerifyRequestDTO.getOtp());
	    return ResponseEntity.ok(Constants.OTP_VERIFIED);  // Return success message
	}


}
