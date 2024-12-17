package com.tasklytic.userservice.service;

import com.tasklytic.userservice.dto.*;
import com.tasklytic.userservice.model.UserEntity;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserRegistrationResponseDTO registerUser(UserRegistrationDTO registerDTO);

    UserLoginResponseDTO loginUser(UserLoginDTO loginDTO);

    void logoutUser(UUID userId);

    void sendOtp(OtpRequestDTO otpRequestDTO);

    void updateUserDetails(UpdateUserDTO updateUserDTO);

    void softDeleteUser(UUID userId);

    List<UserResponseDTO> getAllUsers(UserFilterDTO filterDTO);

	List<UserEntity> getAllUsers();
}
