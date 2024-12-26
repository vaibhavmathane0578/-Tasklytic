package com.tasklytic.collaborationservice.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessageDTO {

    @NotNull(message = "Collaboration session ID is mandatory")
    private String collaborationSessionId; 

    @NotNull(message = "Sender ID is mandatory")
    private String senderId; 

    @NotNull(message = "Message content is mandatory")
    @Size(min = 1, max = 500, message = "Message should be between 1 and 500 characters")
    private String message; 
    
    @NotNull(message = "Read status is mandatory")
    private Boolean read; 

    @NotNull(message = "Delivered status is mandatory")
    private Boolean delivered; 
}
