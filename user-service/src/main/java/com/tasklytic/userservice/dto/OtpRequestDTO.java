package com.tasklytic.userservice.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OtpRequestDTO {

    @NotNull(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;
}
