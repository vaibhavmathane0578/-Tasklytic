package com.tasklytic.collaborationservice.dto;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CollaborationSessionDTO {

    private String id; // Unique session ID
    private Long taskId; // Associated task
    private List<Long> participantIds; // List of participant IDs
    private Instant startTime; // Start time of the session
    private Instant endTime; // End time of the session
    private String status; // Active or Ended
}
