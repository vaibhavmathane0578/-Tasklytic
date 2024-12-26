package com.tasklytic.collaborationservice.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollaborationSessionDTO {

    @NotNull(message = "Collaboration session ID is mandatory")
    private String id; // Unique ID for the collaboration session
    
    @NotNull(message = "Creator ID is mandatory")
    private String creatorId; // ID of the user who created the session
    
    @NotNull(message = "Participants list cannot be empty")
    private List<String> participants; // List of participant IDs
    
    @NotNull(message = "Task ID is mandatory")
    private String taskId; // ID of the related task
    
    @NotNull(message = "Creation timestamp is mandatory")
    private LocalDateTime createdAt; // Timestamp when the session was created
    
    private LocalDateTime lastUpdatedAt; // Timestamp when the session was last updated
}
