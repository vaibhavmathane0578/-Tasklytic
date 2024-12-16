package com.tasklytic.userservice.service;

import com.tasklytic.userservice.dto.UserLoginDTO;
import com.tasklytic.userservice.dto.UserLoginResponseDTO;
import com.tasklytic.userservice.dto.UserRegistrationDTO;
import com.tasklytic.userservice.dto.UserRegistrationResponseDTO;
import java.util.UUID;

public interface UserService {

    // Method for user registration
    UserRegistrationResponseDTO registerUser(UserRegistrationDTO registerDTO);

    // Method for user login
    UserLoginResponseDTO loginUser(UserLoginDTO loginDTO);

    // Method for user logout
    void logoutUser(UUID userId);

}
