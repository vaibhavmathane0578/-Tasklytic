package com.tasklytic.userservice.dto;

import java.util.UUID;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponseDTO{
	private UUID userId;
    private String firstName;
    private String lastName;
    private String token;
}
