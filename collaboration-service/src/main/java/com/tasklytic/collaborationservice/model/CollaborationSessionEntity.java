package com.tasklytic.collaborationservice.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Table("collaboration_sessions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CollaborationSessionEntity {

    @PrimaryKey
    private String id; // Unique ID for the collaboration session
    private String creatorId; // ID of the user who created the session
    private List<String> participants; // List of participant IDs
    private String taskId; // ID of the related task (from TaskService)
    private LocalDateTime createdAt; // Timestamp when the session was created
    private LocalDateTime lastUpdatedAt; // Timestamp when the session was last updated

}
