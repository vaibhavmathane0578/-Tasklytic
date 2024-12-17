package com.tasklytic.userservice.dto;

import java.util.UUID;

import com.tasklytic.userservice.model.UserStatus;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private UserStatus status;
}
