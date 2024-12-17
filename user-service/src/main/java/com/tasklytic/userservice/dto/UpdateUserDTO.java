package com.tasklytic.userservice.dto;

import java.util.UUID;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDTO {
    private UUID userId;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
    private String password;
    private String confirmPassword;
    private String role;
    private String department;
    private String profilePictureUrl;
    private String otp; // OTP for verification
}
