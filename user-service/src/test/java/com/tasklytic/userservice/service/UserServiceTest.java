package com.tasklytic.userservice.service;

import com.tasklytic.shared.constants.Constants;
import com.tasklytic.userservice.dto.UserRegistrationDTO;
import com.tasklytic.userservice.dto.UserRegistrationResponseDTO;
import com.tasklytic.userservice.model.UserEntity;
import com.tasklytic.userservice.model.UserStatus;
import com.tasklytic.userservice.repository.UserRepository;
import com.tasklytic.shared.utils.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    private UserRegistrationDTO testUserDTO;
    private UserEntity testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // UserRegistrationDTO setup
        testUserDTO = new UserRegistrationDTO();
        testUserDTO.setFirstName("John");
        testUserDTO.setLastName("Doe");
        testUserDTO.setEmail("john.doe@example.com");
        testUserDTO.setMobileNumber("9876543210");
        testUserDTO.setPassword("password123");
        testUserDTO.setConfirmPassword("password123");
        testUserDTO.setRole("USER");
        testUserDTO.setDepartment("IT");

        // UserEntity setup (used to simulate repository responses)
        testUser = new UserEntity();
        testUser.setId(java.util.UUID.randomUUID());
        testUser.setFirstName(testUserDTO.getFirstName());
        testUser.setLastName(testUserDTO.getLastName());
        testUser.setEmail(testUserDTO.getEmail());
        testUser.setMobileNumber(testUserDTO.getMobileNumber());
        testUser.setPassword(testUserDTO.getPassword());
        testUser.setRole(testUserDTO.getRole());
        testUser.setDepartment(testUserDTO.getDepartment());
        testUser.setStatus(UserStatus.ACTIVE);
    }

    @Test
    void testRegisterUser_Success() {
        // Mock repository behavior
        when(userRepository.findByEmail(testUserDTO.getEmail())).thenReturn(Optional.empty());
        when(userRepository.findByMobileNumber(testUserDTO.getMobileNumber())).thenReturn(Optional.empty());
        when(userRepository.save(any(UserEntity.class))).thenReturn(testUser);

        // Call the service method
        UserRegistrationResponseDTO response = userService.registerUser(testUserDTO);

        // Verify the response
        assertNotNull(response);
        assertEquals(testUser.getId(), response.getUserId());
        assertEquals(testUser.getEmail(), response.getEmail());

        // Verify repository interactions
        verify(userRepository, times(1)).findByEmail(testUserDTO.getEmail());
        verify(userRepository, times(1)).findByMobileNumber(testUserDTO.getMobileNumber());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void testRegisterUser_EmailAlreadyExists() {
        // Mock repository behavior
        when(userRepository.findByEmail(testUserDTO.getEmail())).thenReturn(Optional.of(testUser));

        // Assert exception is thrown
        Exception exception = assertThrows(Constants.EmailAlreadyExistsException.class, () -> {
            userService.registerUser(testUserDTO);
        });

        assertEquals(Constants.EMAIL_ALREADY_EXISTS, exception.getMessage());

        // Verify repository interactions
        verify(userRepository, times(1)).findByEmail(testUserDTO.getEmail());
        verify(userRepository, never()).save(any(UserEntity.class));
    }

    @Test
    void testRegisterUser_MobileAlreadyExists() {
        // Mock repository behavior
        when(userRepository.findByMobileNumber(testUserDTO.getMobileNumber())).thenReturn(Optional.of(testUser));

        // Assert exception is thrown
        Exception exception = assertThrows(Constants.MobileAlreadyExistsException.class, () -> {
            userService.registerUser(testUserDTO);
        });

        assertEquals(Constants.MOBILE_ALREADY_EXISTS, exception.getMessage());

        // Verify repository interactions
        verify(userRepository, times(1)).findByMobileNumber(testUserDTO.getMobileNumber());
        verify(userRepository, never()).save(any(UserEntity.class));
    }
}
