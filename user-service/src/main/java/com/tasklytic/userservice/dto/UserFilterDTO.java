package com.tasklytic.userservice.dto;

import com.tasklytic.userservice.model.UserStatus;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFilterDTO {
    private UserStatus status;
}
