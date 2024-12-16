package com.tasklytic.userservice.dto;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDTO {

    @NotNull(message = "First name is required")
    private String firstName;

    @NotNull(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Mobile number is required")
    @Size(min = 10, max = 10, message = "Mobile number must be 10 digits")
    private String mobileNumber;

    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull(message = "Password is required")
    @Pattern(regexp = "(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,20}", 
             message = "Password must be between 8-20 characters and contain at least 1 capital letter, 1 small letter, 1 digit, and 1 special character")
    private String password;

    @NotNull(message = "Confirm password is required")
    @Size(min = 8, max = 20, message = "Password confirmation must match the original password")
    private String confirmPassword;  // To confirm that the passwords match

    @NotNull(message = "Role is required")
    private String role;

    @NotNull(message = "Department is required")
    private String department;

    @NotNull(message = "OTP is required for email verification")
    private String otp;  // To verify email
}
